package cat.uvic.teknos.bookstore.services;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.BindException;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import cat.uvic.teknos.bookstore.services.exception.ServerException;

public class Server {
    public final int PORT = 3000;
    private final RequestRouter requestRouter;
    private volatile boolean SHUTDOWN_SERVER;
    private final ExecutorService executor;
    private final ScheduledExecutorService scheduledExecutor;
    private final RawHttp rawHttp;
    private ServerSocket serverSocket;
    private final Properties properties;

    public Server(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        this.SHUTDOWN_SERVER = false;
        this.executor = Executors.newFixedThreadPool(10);
        this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.rawHttp = new RawHttp(RawHttpOptions.newBuilder()
                .doNotInsertHostHeaderIfMissing()
                .build());
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream("services/src/main/resources/server.properties")) {
            if (input == null) {
                System.out.println("Warning: server.properties not found, using default values");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading server.properties: " + e.getMessage());
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            startShutdownChecker();

            while (!SHUTDOWN_SERVER) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    executor.execute(() -> handleClient(clientSocket));
                } catch (IOException e) {
                    if (!SHUTDOWN_SERVER) {
                        System.err.println("Error accepting client: " + e.getMessage());
                    }
                }
            }
        } catch (BindException e) {
            throw new ServerException("Port " + PORT + " is already in use. Please choose a different port or stop the other service.");
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            var request = rawHttp.parseRequest(clientSocket.getInputStream());
            var response = requestRouter.execRequest(request);
            response.writeTo(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void startShutdownChecker() {
        scheduledExecutor.scheduleAtFixedRate(() -> {
            loadProperties();
            if (Boolean.parseBoolean(properties.getProperty("shutdown", "false"))) {
                System.out.println("Shutdown signal received");
                SHUTDOWN_SERVER = true;
                try {
                    if (serverSocket != null && !serverSocket.isClosed()) {
                        serverSocket.close();
                    }
                } catch (IOException e) {
                    System.err.println("Error closing server socket: " + e.getMessage());
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void shutdown() {
        SHUTDOWN_SERVER = true;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }

        executor.shutdown();
        scheduledExecutor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
            if (!scheduledExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduledExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            scheduledExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Server stopped");
    }
}