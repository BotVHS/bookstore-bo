package cat.teknos.bookstore.domain.jpa.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "BOOK")
public class Book implements com.albertdiaz.bookstore.models.Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "AUTHORID")
    private Author author;

    @Column(name = "ISBN", unique = true)
    private String isbn;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "PUBLISHDATE")
    private LocalDate publishDate;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "PAGECOUNT")
    private int pageCount;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public com.albertdiaz.bookstore.models.Author getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(com.albertdiaz.bookstore.models.Author author) {
        this.author = (Author) author;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public Float getPrice() {
        return price;
    }

    @Override
    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public LocalDate getPublishDate() {
        return publishDate;
    }

    @Override
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}