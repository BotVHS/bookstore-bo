package cat.uvic.teknos.bookstore.domain.fake.models;

import java.time.LocalDate;

public class Author implements com.albertdiaz.bookstore.models.Author {
    private int id;
    private String FirstName;
    private String LastName;
    private String Biography;
    private LocalDate BirthDate;
    private String Nationality;

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
        return FirstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    @Override
    public String getLastName() {
        return LastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    @Override
    public String getBiography() {
        return Biography;
    }

    @Override
    public void setBiography(String biography) {
        this.Biography = biography;
    }

    @Override
    public LocalDate getBirthDate() {
        return BirthDate;
    }

    @Override
    public void setBirthDate(LocalDate birthDate) {
        this.BirthDate = birthDate;
    }

    @Override
    public String getNationality() {
        return Nationality;
    }

    @Override
    public void setNationality(String country) {
        this.Nationality = country;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", biography=" + getBiography() +
                ", birthdate=" + getBirthDate() +
                ", nationality=" + getNationality() +
                '}';
    }
}
