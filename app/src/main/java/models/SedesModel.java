package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SedesModel {
    public String id;
    public String name;
    public List<Services> services;
    public Location location;
}


class Services{
    String key;
    String name;
}

class Location{
    String address;
    String country;
    String departament;
    String city;
    Coordinates coordinates;
}

class Coordinates{
    String altitude;
    String latitude;
}