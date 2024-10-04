package cat.uvic.teknos.bookstore.services;

import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;
import rawhttp.core.RawHttpResponse;


import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static final int PORT = 80;

    public static void main(String[] args) throws IOException {
        try (var serverSocket = new ServerSocket(PORT)) {
            var router = new RequestRouter();

            while (true) {
                try (var clientSocket = serverSocket.accept()) {
                    var rawHttp = new RawHttp(RawHttpOptions.newBuilder().doNotInsertHostHeaderIfMissing().build());
                    var request = rawHttp.parseRequest(clientSocket.getInputStream());

                    var response = router.execRequest(request);

                    response.writeTo(clientSocket.getOutputStream());
                }
            }
        }
    }
}