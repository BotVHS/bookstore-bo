package cat.uvic.teknos.bookstore.services;

import java.io.IOException;
import java.net.Socket;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final RequestRouter requestRouter;
    private final RawHttp rawHttp;

    public ClientHandler(Socket clientSocket, RequestRouter requestRouter) {
        this.clientSocket = clientSocket;
        this.requestRouter = requestRouter;
        this.rawHttp = new RawHttp(RawHttpOptions.newBuilder()
                .doNotInsertHostHeaderIfMissing()
                .build());
    }

    @Override
    public void run() {
        try {
            var request = rawHttp.parseRequest(clientSocket.getInputStream());
            var response = requestRouter.execRequest(request);
            response.writeTo(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}