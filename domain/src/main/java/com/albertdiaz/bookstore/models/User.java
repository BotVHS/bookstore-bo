package com.albertdiaz.bookstore.models;

import java.time.LocalDate;

public interface User {
    int getId();
    void setId(int id);

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getEmail();
    void setEmail(String email);

    String getPasswordHash();
    void setPasswordHash(String passwordHash);

    String getAddress();
    void setAddress(String address);

    String getCity();
    void setCity(String city);

    String getCountry();
    void setCountry(String country);

    String getPostalCode();
    void setPostalCode(String postalCode);

    LocalDate getJoinDate();
    void setJoinDate(LocalDate joinDate);

}
