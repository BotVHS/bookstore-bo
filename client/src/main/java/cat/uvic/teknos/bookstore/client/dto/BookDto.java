package cat.uvic.teknos.bookstore.client.dto;

import java.time.LocalDate;

public class BookDto implements com.albertdiaz.bookstore.models.Book {
    private int id;
    private String title;
    private com.albertdiaz.bookstore.models.Author author;
    private String isbn;
    private Float price;
    private String genre;
    private LocalDate publishDate;
    private String publisher;
    private int pageCount;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getTitle() { return title; }
    @Override
    public void setTitle(String title) { this.title = title; }

    @Override
    public com.albertdiaz.bookstore.models.Author getAuthor() { return author; }
    @Override
    public void setAuthor(com.albertdiaz.bookstore.models.Author author) { this.author = author; }

    @Override
    public String getIsbn() { return isbn; }
    @Override
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public Float getPrice() { return price; }
    @Override
    public void setPrice(Float price) { this.price = price; }

    @Override
    public String getGenre() { return genre; }
    @Override
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public LocalDate getPublishDate() { return publishDate; }
    @Override
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }

    @Override
    public String getPublisher() { return publisher; }
    @Override
    public void setPublisher(String publisher) { this.publisher = publisher; }

    @Override
    public int getPageCount() { return pageCount; }
    @Override
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
}
