package cat.teknos.bookstore.domain.jdbc.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Author implements com.albertdiaz.bookstore.models.Author, Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate birthDate;
    private String nationality;

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
        return nationality;
    }
    @Override
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
