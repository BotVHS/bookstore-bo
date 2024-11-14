package cat.uvic.teknos.bookstore.services;

import cat.uvic.teknos.bookstore.services.controllers.Controller;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;
import java.io.IOException;
import java.util.Map;

public class RequestRouterImpl implements RequestRouter {
    private static final RawHttp rawHttp = new RawHttp();
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
                return createErrorResponse(404, "Not Found");
            }

            var controllerName = pathParts[1];

            if (!controllers.containsKey(controllerName)) {
                return createErrorResponse(404, "Not Found");
            }

            String responseBody = "";

            switch (controllerName) {
                case "authors":
                    responseBody = manageResource(request, method, pathParts, "authors");
                    break;
                case "books":
                    responseBody = manageResource(request, method, pathParts, "books");
                    break;
                case "orders":
                    responseBody = manageResource(request, method, pathParts, "orders");
                    break;
                case "reviews":
                    responseBody = manageResource(request, method, pathParts, "reviews");
                    break;
                default:
                    return createErrorResponse(404, "Resource not found");
            }

            return createSuccessResponse(responseBody);

        } catch (ResourceNotFoundException e) {
            return createErrorResponse(404, "Resource not found: " + e.getMessage());
        } catch (ServerErrorException e) {
            return createErrorResponse(500, "Internal server error: " + e.getMessage());
        } catch (NumberFormatException e) {
            return createErrorResponse(400, "Invalid ID format");
        } catch (Exception e) {
            return createErrorResponse(500, "Unexpected error: " + e.getMessage());
        }
    }

    private String manageResource(RawHttpRequest request, String method, String[] pathParts, String resourceType) {
        var controller = controllers.get(resourceType);
        String responseBody = "";

        switch (method) {
            case "GET":
                if (pathParts.length == 3) {
                    responseBody = controller.get(Integer.parseInt(pathParts[2]));
                } else if (pathParts.length == 2) {
                    responseBody = controller.get();
                } else {
                    throw new ResourceNotFoundException("Invalid path");
                }
                break;

            case "POST":
                if (pathParts.length != 2) {
                    throw new ResourceNotFoundException("Invalid path for POST");
                }
                String createJson = extractRequestBody(request);
                controller.post(createJson);
                responseBody = "{\"status\":\"created\"}";
                break;

            case "PUT":
                if (pathParts.length != 3) {
                    throw new ResourceNotFoundException("Invalid path for PUT");
                }
                var updateId = Integer.parseInt(pathParts[2]);
                String updateJson = extractRequestBody(request);
                controller.put(updateId, updateJson);
                responseBody = "{\"status\":\"updated\"}";
                break;

            case "DELETE":
                if (pathParts.length != 3) {
                    throw new ResourceNotFoundException("Invalid path for DELETE");
                }
                var deleteId = Integer.parseInt(pathParts[2]);
                controller.delete(deleteId);
                responseBody = "{\"status\":\"deleted\"}";
                break;

            default:
                throw new ServerErrorException("Method not allowed: " + method);
        }

        return responseBody;
    }

    private String extractRequestBody(RawHttpRequest request) {
        return request.getBody()
                .map(body -> {
                    try {
                        return new String(body.asRawBytes());
                    } catch (IOException e) {
                        throw new ServerErrorException("Error reading request body", e);
                    }
                })
                .orElseThrow(() -> new ServerErrorException("Request body is required"));
    }

    private RawHttpResponse<?> createSuccessResponse(String body) {
        return rawHttp.parseResponse("""
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d

            %s""".formatted(body.length(), body));
    }

    private RawHttpResponse<?> createErrorResponse(int statusCode, String message) {
        String statusLine = switch (statusCode) {
            case 400 -> "400 Bad Request";
            case 404 -> "404 Not Found";
            case 405 -> "405 Method Not Allowed";
            case 500 -> "500 Internal Server Error";
            default -> "500 Internal Server Error";
        };

        String jsonMessage = "{\"error\": \"" + message.replace("\"", "\\\"") + "\"}";

        return rawHttp.parseResponse("""
            HTTP/1.1 %s
            Content-Type: application/json
            Content-Length: %d

            %s""".formatted(statusLine, jsonMessage.length(), jsonMessage));
    }
}