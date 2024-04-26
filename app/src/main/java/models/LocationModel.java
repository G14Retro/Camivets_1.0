package models;

public class LocationModel {
    public String Address;
    public String Country;
    public String Departament;
    public String City;
    public CoordinatesModel Coordinates;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDepartament() {
        return Departament;
    }

    public void setDepartament(String departament) {
        Departament = departament;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public CoordinatesModel getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(CoordinatesModel coordinates) {
        Coordinates = coordinates;
    }
}
