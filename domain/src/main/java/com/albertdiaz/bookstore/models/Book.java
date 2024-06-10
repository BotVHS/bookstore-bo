package com.albertdiaz.bookstore.models;

import java.time.LocalDate;

public interface Book {
    int getId();
    void setId(int id);

    String getTitle();
    void setTitle(String title);

    Author getAuthor();
    void setAuthor(Author author);

    String getIsbn();
    void setIsbn(String isbn);


    Float getPrice();
    void setPrice(Float price);

    String getGenre();
    void setGenre(String Genre);

    LocalDate getPublishDate();
    void setPublishDate(LocalDate publishDate);

    String getPublisher();
    void setPublisher(String publisher);

    int getPageCount();
    void setPageCount(int pageCount);
}
