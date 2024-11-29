package cat.uvic.teknos.bookstore.services;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;
import cat.uvic.teknos.bookstore.services.exception.ServerException;
import cat.uvic.teknos.bookstore.services.controllers.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.BindException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
    public final int PORT = 3000;
    private final RequestRouter requestRouter;
    private volatile boolean SHUTDOWN_SERVER;
    private final ExecutorService executor;
    private final ScheduledExecutorService scheduledExecutor;
    private ServerSocket serverSocket;
    private final Properties properties;
    private final RawHttp rawHttp;

    public Server(Map<String, Controller> controllers, Properties appProperties) {
        this.requestRouter = new RequestRouterImpl(
                controllers,
                appProperties.getProperty("server.keystore.path"),
                appProperties.getProperty("server.keystore.password")
        );
        this.executor = Executors.newFixedThreadPool(10);
        this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.properties = new Properties();
        this.rawHttp = new RawHttp(RawHttpOptions.newBuilder()
                .doNotInsertHostHeaderIfMissing()
                .build());
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream("services/src/main/resources/server.properties")) {
            properties.load(input);
            boolean shouldShutdown = Boolean.parseBoolean(properties.getProperty("shutdown", "false"));
            if (shouldShutdown && !SHUTDOWN_SERVER) {
                System.out.println("Shutdown signal received from properties file");
                SHUTDOWN_SERVER = true;
                stopServer();
            }
        } catch (IOException e) {
            System.err.println("Error loading server.properties: " + e.getMessage());
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            scheduledExecutor.scheduleAtFixedRate(this::loadProperties, 0, 5, TimeUnit.SECONDS);

            while (!SHUTDOWN_SERVER) {
                try {
                    var clientSocket = serverSocket.accept();
                    if (!SHUTDOWN_SERVER) {
                        executor.execute(() -> {
                            try {
                                var request = rawHttp.parseRequest(clientSocket.getInputStream());
                                var response = requestRouter.execRequest(request);
                                response.writeTo(clientSocket.getOutputStream());
                                clientSocket.close();
                            } catch (IOException e) {
                                System.err.println("Error handling request: " + e.getMessage());
                            }
                        });
                    }
                } catch (IOException e) {
                    if (!SHUTDOWN_SERVER) {
                        System.err.println("Accept failed: " + e.getMessage());
                    }
                }
            }
        } catch (BindException e) {
            throw new ServerException("Port " + PORT + " already in use");
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        } finally {
            stopServer();
        }
    }

    private void stopServer() {
        SHUTDOWN_SERVER = true;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            executor.shutdownNow();
            scheduledExecutor.shutdownNow();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Server stopped");
    }

    public void shutdown() {
        SHUTDOWN_SERVER = true;
        stopServer();
    }
}