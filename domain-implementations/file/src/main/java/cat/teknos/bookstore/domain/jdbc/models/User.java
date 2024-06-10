package cat.teknos.bookstore.domain.jdbc.models;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements com.albertdiaz.bookstore.models.User, Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private LocalDate joinDate;




    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getFirstName() { return firstName; }

    @Override
    public void setFirstName(String FirstName) { this.firstName = FirstName; }

    @Override
    public String getLastName() { return lastName; }

    @Override
    public void setLastName(String LastName) { this.lastName = LastName; }

    @Override
    public String getEmail() { return email; }

    @Override
    public void setEmail(String string) { this.email = string; }

    @Override
    public String getPasswordHash() { return passwordHash; }

    @Override
    public void setPasswordHash(String PasswordHash) { this.passwordHash = PasswordHash; }

    @Override
    public String getAddress() { return address; }

    @Override
    public void setAddress(String address) { this.address = address; }

    @Override
    public String getCity() { return city; }

    @Override
    public void setCity(String City) { this.city = City; }

    @Override
    public String getCountry() { return country; }

    @Override
    public void setCountry(String Country) { this.country = Country; }

    @Override
    public String getPostalCode() { return postalCode; }

    @Override
    public void setPostalCode(String PostalCode) { this.postalCode = PostalCode; }

    @Override
    public LocalDate getJoinDate() { return joinDate; }

    @Override
    public void setJoinDate(LocalDate JoinDate) { this.joinDate = JoinDate; }
}
