package cat.uvic.teknos.bookstore.client.dto;

import java.time.LocalDate;

public class ReviewDto implements com.albertdiaz.bookstore.models.Review {
    private int id;
    private com.albertdiaz.bookstore.models.Book book;
    private com.albertdiaz.bookstore.models.User user;
    private int rating;
    private String comment;
    private LocalDate reviewDate;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public com.albertdiaz.bookstore.models.Book getBook() { return book; }
    @Override
    public void setBook(com.albertdiaz.bookstore.models.Book book) { this.book = book; }

    @Override
    public com.albertdiaz.bookstore.models.User getUser() { return user; }
    @Override
    public void setUser(com.albertdiaz.bookstore.models.User user) { this.user = user; }

    @Override
    public int getRating() { return rating; }
    @Override
    public void setRating(int rating) { this.rating = rating; }

    @Override
    public String getComment() { return comment; }
    @Override
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public LocalDate getReviewDate() { return reviewDate; }
    @Override
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
}