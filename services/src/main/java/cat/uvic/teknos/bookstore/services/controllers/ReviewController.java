package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.Review;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ReviewController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public ReviewController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        var review = repositoryFactory.getReviewRepository().get(id);
        if (review == null) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        try {
            return Mappers.get().writeValueAsString(review);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing review", e);
        }
    }

    @Override
    public String get() {
        var reviews = repositoryFactory.getReviewRepository().getAll();
        try {
            return Mappers.get().writeValueAsString(reviews);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing reviews", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            var review = modelFactory.createReview();
            Mappers.get().readerForUpdating(review).readValue(json);

            // Verif book
            if (review.getBook() == null || review.getBook().getId() == 0) {
                throw new ServerErrorException("Book is required for a review");
            }
            var book = repositoryFactory.getBookRepository().get(review.getBook().getId());
            if (book == null) {
                throw new ResourceNotFoundException("Book not found with id: " + review.getBook().getId());
            }

            // verif user
            if (review.getUser() == null || review.getUser().getId() == 0) {
                throw new ServerErrorException("User is required for a review");
            }
            var user = repositoryFactory.getUserRepository().get(review.getUser().getId());
            if (user == null) {
                throw new ResourceNotFoundException("User not found with id: " + review.getUser().getId());
            }

            repositoryFactory.getReviewRepository().save(review);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error deserializing review: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        var existingReview = repositoryFactory.getReviewRepository().get(id);
        if (existingReview == null) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }

        try {
            Mappers.get().readerForUpdating(existingReview).readValue(json);
            repositoryFactory.getReviewRepository().save(existingReview);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error updating review", e);
        }
    }

    @Override
    public void delete(int id) {
        var review = repositoryFactory.getReviewRepository().get(id);
        if (review == null) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }

        repositoryFactory.getReviewRepository().delete(review);
    }
}