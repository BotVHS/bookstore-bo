package cat.teknos.bookstore.domain.jpa.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "AUTHOR")
public class Author implements com.albertdiaz.bookstore.models.Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;

    @Column(name = "COUNTRY")
    private String country;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getBiography() {
        return biography;
    }

    @Override
    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getNationality() {
        return country;
    }

    @Override
    public void setNationality(String country) {
        this.country = country;
    }
}
