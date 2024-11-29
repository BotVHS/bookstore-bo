package cat.uvic.teknos.bookstore.services;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;
import cat.uvic.teknos.bookstore.services.exception.ServerException;
import cat.uvic.teknos.bookstore.services.controllers.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Properties;

public class Server {
    public final int PORT = 3000;
    private final RequestRouter requestRouter;
    private boolean SHUTDOWN_SERVER;

    public Server(Map<String, Controller> controllers, Properties properties) {
        this.requestRouter = new RequestRouterImpl(
                controllers,
                properties.getProperty("server.keystore.path"),
                properties.getProperty("server.keystore.password")
        );
    }

    public void start() {
        try (var serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (!SHUTDOWN_SERVER) {
                try (var clientSocket = serverSocket.accept()) {
                    var rawHttp = new RawHttp(RawHttpOptions.newBuilder()
                            .doNotInsertHostHeaderIfMissing()
                            .build());
                    var request = rawHttp.parseRequest(clientSocket.getInputStream());

                    var response = requestRouter.execRequest(request);

                    response.writeTo(clientSocket.getOutputStream());
                }
            }
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
    }

    public void shutdown() {
        SHUTDOWN_SERVER = true;
    }
}