package org.ac.bibliotheque.library.entity;

import java.util.Objects;

public class Library {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String street;
    private String number;
    private String zipCode;
    private String email;
    private String phoneNumber;

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

    public String getZipCode() {
        return zipCode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(id, library.id) && Objects.equals(name, library.name) && Objects.equals(country, library.country) && Objects.equals(city, library.city) && Objects.equals(street, library.street) && Objects.equals(number, library.number) && Objects.equals(zipCode, library.zipCode) && Objects.equals(email, library.email) && Objects.equals(phoneNumber, library.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city, street, number, zipCode, email, phoneNumber);
    }

    @Override
    public String toString() {
        return String.format("Library: id - %d, name - %s, city - %s, street - %s, number - %s, zipCode - %s, email - %s, phoneNumber - %s",
                id, name, country, city, street, number, zipCode, email, phoneNumber);
    }
}
