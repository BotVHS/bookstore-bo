package cat.uvic.teknos.bookstore.services;

import cat.teknos.bookstore.domain.jpa.models.Author;
import cat.uvic.teknos.bookstore.services.controllers.AuthorController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

public interface RequestRouter {
    RawHttpResponse<?> execRequest(RawHttpRequest request);
}