package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.Book;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BookController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public BookController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        var book = repositoryFactory.getBookRepository().get(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        try {
            return Mappers.get().writeValueAsString(book);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing book", e);
        }
    }

    @Override
    public String get() {
        var books = repositoryFactory.getBookRepository().getAll();
        try {
            return Mappers.get().writeValueAsString(books);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing books", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            var book = modelFactory.createBook();
            Mappers.get().readerForUpdating(book).readValue(json);

            // Verificar que el autor existe
            if (book.getAuthor() != null && book.getAuthor().getId() > 0) {
                var author = repositoryFactory.getAuthorRepository().get(book.getAuthor().getId());
                if (author == null) {
                    throw new ResourceNotFoundException("Author not found with id: " + book.getAuthor().getId());
                }
                book.setAuthor(author);
            } else {
                throw new ServerErrorException("Author is required for creating a book");
            }

            repositoryFactory.getBookRepository().save(book);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error deserializing book: " + e.getMessage(), e);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerErrorException("Error creating book: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        var existingBook = repositoryFactory.getBookRepository().get(id);
        if (existingBook == null) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

        try {
            Mappers.get().readerForUpdating(existingBook).readValue(json);

            // Verificar que el autor existe si se está actualizando
            if (existingBook.getAuthor() != null && existingBook.getAuthor().getId() > 0) {
                var author = repositoryFactory.getAuthorRepository().get(existingBook.getAuthor().getId());
                if (author == null) {
                    throw new ResourceNotFoundException("Author not found with id: " + existingBook.getAuthor().getId());
                }
                existingBook.setAuthor(author);
            }

            repositoryFactory.getBookRepository().save(existingBook);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error updating book", e);
        }
    }

    @Override
    public void delete(int id) {
        var book = repositoryFactory.getBookRepository().get(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

        repositoryFactory.getBookRepository().delete(book);
    }
}