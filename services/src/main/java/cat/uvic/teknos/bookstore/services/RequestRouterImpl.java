package cat.uvic.teknos.bookstore.services;

import cat.uvic.teknos.bookstore.services.controllers.Controller;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import java.util.Map;

public class RequestRouterImpl implements RequestRouter {
    private final RawHttp rawHttp = new RawHttp();
    private final Map<String, Controller> controllers;

    public RequestRouterImpl(Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    @Override
    public RawHttpResponse<?> execRequest(RawHttpRequest request) {
        try {
            var path = request.getUri().getPath();
            var method = request.getMethod();
            var pathParts = path.split("/");

            if (pathParts.length < 2) {
                return createResponse(404, "Not Found");
            }

            var controllerName = pathParts[1];

            if (!controllers.containsKey(controllerName)) {
                return createResponse(404, "Not Found");
            }

            var controller = controllers.get(controllerName);
            String responseBody;

            switch (method) {
                case "GET":
                    responseBody = handleGet(pathParts, controller);
                    break;

                case "POST":
                    handlePost(request, controller);
                    responseBody = "{\"status\":\"created\"}";
                    break;

                case "PUT":
                    handlePut(pathParts, request, controller);
                    responseBody = "{\"status\":\"updated\"}";
                    break;

                case "DELETE":
                    handleDelete(pathParts, controller);
                    responseBody = "{\"status\":\"deleted\"}";
                    break;

                default:
                    return createResponse(405, "Method Not Allowed");
            }

            return createResponse(200, responseBody);

        } catch (ResourceNotFoundException e) {
            return createResponse(404, "Resource Not Found");
        } catch (NumberFormatException e) {
            return createResponse(400, "Invalid ID Format");
        } catch (Exception e) {
            return createResponse(500, "Internal Server Error");
        }
    }

    private String handleGet(String[] pathParts, Controller controller) {
        if (pathParts.length == 2) {
            return controller.get();
        } else if (pathParts.length == 3) {
            int id = Integer.parseInt(pathParts[2]);
            return controller.get(id);
        }
        throw new ResourceNotFoundException();
    }

    private void handlePost(RawHttpRequest request, Controller controller) {
        String body = request.getBody()
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Request body is required"));
        controller.post(body);
    }

    private void handlePut(String[] pathParts, RawHttpRequest request, Controller controller) {
        if (pathParts.length != 3) {
            throw new ResourceNotFoundException();
        }
        int id = Integer.parseInt(pathParts[2]);
        String body = request.getBody()
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Request body is required"));
        controller.put(id, body);
    }

    private void handleDelete(String[] pathParts, Controller controller) {
        if (pathParts.length != 3) {
            throw new ResourceNotFoundException();
        }
        int id = Integer.parseInt(pathParts[2]);
        controller.delete(id);
    }

    private RawHttpResponse<?> createResponse(int statusCode, String body) {
        String statusLine = switch (statusCode) {
            case 200 -> "200 OK";
            case 201 -> "201 Created";
            case 400 -> "400 Bad Request";
            case 404 -> "404 Not Found";
            case 405 -> "405 Method Not Allowed";
            case 500 -> "500 Internal Server Error";
            default -> "500 Internal Server Error";
        };

        String response = String.format("""
                HTTP/1.1 %s
                Content-Type: application/json
                Content-Length: %d

                %s""", statusLine, body.length(), body);

        return rawHttp.parseResponse(response);
    }
}