package com.albertdiaz.bookstore.models;

import java.time.LocalDate;

public interface Author {
    int getId();
    void setId(int id);

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getBiography();
    void setBiography(String biography);
    LocalDate getBirthDate();
    void setBirthDate(LocalDate birthDate);

    String getNationality();
    void setNationality(String country);

}
