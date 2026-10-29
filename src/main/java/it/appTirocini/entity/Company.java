package it.appTirocini.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "companies", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String city;
    private String contactName;
    private String contactEmail;


    public Company() {
    }

    public Company(UUID id, String name, String city, String contactName, String contactEmail) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
