package org.ac.bibliotheque.library.domain.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private String number;
    @Column(name = "zip")
    private String zip;
    @Column(name = "phone")
    private String phone;
    @Column(name = "librarian_id")
    private Long librarian_id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public Long getLibrarian_id() {
        return librarian_id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setLibrarian_id(Long librarian_id) {
        this.librarian_id = librarian_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(id, library.id) && Objects.equals(name, library.name) && Objects.equals(country, library.country) && Objects.equals(city, library.city) && Objects.equals(street, library.street) && Objects.equals(number, library.number) && Objects.equals(zip, library.zip) && Objects.equals(phone, library.phone) && Objects.equals(librarian_id, library.librarian_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city, street, number, zip, phone, librarian_id);
    }

    @Override
    public String toString() {
        return String.format("Library: id - %d, name - %s, country - %s, city - %s, street - %s, number - %s, zip - %s, phone - %s, librarian_id - %d",
                id, name, country, city, street, number, zip, phone, librarian_id);
    }
}
