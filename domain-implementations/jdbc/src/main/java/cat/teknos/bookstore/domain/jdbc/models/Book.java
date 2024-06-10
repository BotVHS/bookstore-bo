package cat.teknos.bookstore.domain.jdbc.models;

import com.albertdiaz.bookstore.models.Author;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements com.albertdiaz.bookstore.models.Book, Serializable {


    private int id;
    private String title;
    private Author author;
    private String isbn;
    private float price;
    private String genre;
    private LocalDate publishDate;
    private String publisher;
    private int pageCount;


    @Override
    public int getId() { return id; }

    @Override
    public void setId(int BookID) { this.id = BookID; }

    @Override
    public String getTitle() { return title; }

    @Override
    public void setTitle(String Title) { this.title = Title; }

    @Override
    public Author getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String getIsbn() { return isbn; }

    @Override
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public Float getPrice() {return price;}
    @Override
    public void setPrice(Float Price) { this.price = Price; }

    @Override
    public String getGenre() { return genre; }
    @Override
    public void setGenre(String Genre) { this.genre = Genre; }

    @Override
    public LocalDate getPublishDate() { return publishDate; }
    @Override
    public void setPublishDate(LocalDate PublishDate) { this.publishDate = PublishDate; }

    @Override
    public String getPublisher() { return publisher; }

    @Override
    public void setPublisher(String Publisher) { this.publisher = Publisher; }

    @Override
    public int getPageCount() { return pageCount; }

    @Override
    public void setPageCount(int PageCount) { this.pageCount = PageCount; }
}
