package cat.teknos.bookstore.domain.jdbc.models;

import com.albertdiaz.bookstore.models.Book;
import com.albertdiaz.bookstore.models.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public class Review implements com.albertdiaz.bookstore.models.Review, Serializable {
    private int id;
    private Book book;
    private User user;
    private int rating;
    private String comment;
    private LocalDate reviewDate;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int reviewID) {
        this.id = reviewID;
    }

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public LocalDate getReviewDate() {
        return reviewDate;
    }

    @Override
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}