package cat.teknos.bookstore.domain.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "REVIEW")
public class Review implements com.albertdiaz.bookstore.models.Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "BOOKID", nullable = false)
    private Book book;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "COMMENT", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "REVIEWDATE")
    private LocalDate reviewDate;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public com.albertdiaz.bookstore.models.Book getBook() {
        return book;
    }

    @Override
    public void setBook(com.albertdiaz.bookstore.models.Book book) {
        this.book = (Book) book;
    }

    @Override
    public com.albertdiaz.bookstore.models.User getUser() {
        return user;
    }

    @Override
    public void setUser(com.albertdiaz.bookstore.models.User user) {
        this.user = (User) user;
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
