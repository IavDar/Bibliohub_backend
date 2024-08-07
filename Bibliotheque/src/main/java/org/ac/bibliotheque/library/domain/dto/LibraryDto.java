package org.ac.bibliotheque.library.domain.dto;


import java.util.Objects;

public class LibraryDto {

    private Long id;

    private String name;

    private String country;

    private String city;

    private String street;

    private String number;

    private String zip;

    private String phone;

    private Long librarian_id;

    public Long getId() {
        return id;
    }

    public Long getLibrarian_id() {
        return librarian_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getZip() {
        return zip;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
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
        LibraryDto that = (LibraryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(number, that.number) && Objects.equals(zip, that.zip) && Objects.equals(phone, that.phone) && Objects.equals(librarian_id, that.librarian_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city, street, number, zip, phone, librarian_id);
    }

    @Override
    public String toString() {
        return String.format("Library DTO: id - %d, name - %s, country - %s, city - %s, street - %s, number - %s, zip - %s, phone - %s, librarian_id - %d",
                id, name, country, city, street, number, zip, phone, librarian_id);
    }
}
