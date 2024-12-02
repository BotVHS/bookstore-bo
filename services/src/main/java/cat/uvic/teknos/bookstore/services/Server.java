package cat.uvic.teknos.bookstore.services;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;


import java.io.IOException;
import java.net.ServerSocket;
import cat.uvic.teknos.bookstore.services.exception.ServerException;

public class Server {
    public final int PORT = 3000;
    private final RequestRouter requestRouter;
    private boolean SHUTDOWN_SERVER;

    public Server(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    public  void start() {
        try (var serverSocket = new ServerSocket(PORT)) {
            while (!SHUTDOWN_SERVER) {
                try (var clientSocket = serverSocket.accept()) {
                    var rawHttp = new RawHttp(RawHttpOptions.newBuilder().doNotInsertHostHeaderIfMissing().build());
                    var request = rawHttp.parseRequest(clientSocket.getInputStream());

                    var response = requestRouter.execRequest(request);

                    response.writeTo(clientSocket.getOutputStream());
                }
            }
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
    }
}