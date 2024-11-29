package cat.uvic.teknos.bookstore.client.dto;

import java.time.LocalDate;

public class UserDto implements com.albertdiaz.bookstore.models.User {
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
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @Override
    public String getLastName() { return lastName; }
    @Override
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String getEmail() { return email; }
    @Override
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getPasswordHash() { return passwordHash; }
    @Override
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    @Override
    public String getAddress() { return address; }
    @Override
    public void setAddress(String address) { this.address = address; }

    @Override
    public String getCity() { return city; }
    @Override
    public void setCity(String city) { this.city = city; }

    @Override
    public String getCountry() { return country; }
    @Override
    public void setCountry(String country) { this.country = country; }

    @Override
    public String getPostalCode() { return postalCode; }
    @Override
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @Override
    public LocalDate getJoinDate() { return joinDate; }
    @Override
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
}
