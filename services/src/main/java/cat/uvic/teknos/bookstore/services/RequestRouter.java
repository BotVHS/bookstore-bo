package cat.uvic.teknos.bookstore.services;

import cat.teknos.bookstore.domain.jpa.models.Author;
import cat.uvic.teknos.bookstore.services.controllers.AuthorController;

public class RequestRouter {

    private static RawHttp rawHttp = new RawHttp();
    public RawHttpResponse<?> route(RawHttpRequest request) {
        String path = request.getUri().getPath();
        var method = request.getMethod();
        var pathParts = path.split("/");
        var responseJsonBody = "";


        var controllerName = path.split("/")[1];

        switch (controllerName) {
            case "author": {
                var controller = new AuthorController();

                if (method == "POST") {
                    var authorJson = request.getBody().get().toString();
                    var mapper = new ObjectMapper();
                    try {
                        var author = mapper.readValue(authorJson, Author.class);
                        controller.post(author);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (method == "GET" && pathParts.length == 2) {
                    responseJsonBody = controller.get();
                }
                if (method == "DELETE") {
                    if (pathParts.length == 2) {controller.delete();}
                    else {controller.delete(Integer.parseInt(pathParts[2]));}
                }
                if (method == "PUT") {
                    var authorJson = request.getBody().get().toString();
                    var mapper = new ObjectMapper();
                    try {
                        var author = mapper.readValue(authorJson, Author.class);
                        controller.put(author.getId(), author);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        RawHttpResponse = null;
        try {
            var json = "";
            /*response = rawHttp.parseResponse("HTTP/1.1 200 OK\r\n") +
                    "Content-Type: text/json\r\n" +
                    "Content-Length: " + json.length() + "\r\n" +
                    "\r\n" +
                    json);*/
        } finally {}
    }
}
