package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SedesModel {
    public String IdSede;
    public String Name;
    public boolean Everytime;
    public String Address;
    //public List<ServicesModel> Services;
    //public LocationModel Location;

    // Constructor vacío requerido por Firestore
    public SedesModel(){

    }

    public SedesModel(String idSede, String name, boolean everytime, String address) {
        this.IdSede = idSede;
        this.Name = name;
        this.Everytime = everytime;
        this.Address = address;
        //this.Services = services;
        //this.Location = location;

    }

    // Método para convertir SedesModel a Map
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", Name);
        //map.put("location", Location);
       // map.put("services", Services);
        map.put("everytime", Everytime);
        return map;
    }

    public void setIdSede(String idSede) {
        this.IdSede = idSede;
    }

    public void setName(String name) {
        this.Name = name;
    }

    /*public void setServices(List<ServicesModel> services) {
        this.Services = services;
    }*/

    /*public void setLocation(LocationModel location) {
        this.Location = location;
    }*/

    public String getIdSede() {
        return IdSede;
    }

    public String getName() {
        return Name;
    }

   /* public List<ServicesModel> getServices() {
        return Services;
    }*/

    /*public LocationModel getLocation() {
        return Location;
    }*/

    public boolean isEverytime() {
        return Everytime;
    }

    public void setEverytime(boolean everytime) {
        Everytime = everytime;
    }
}


