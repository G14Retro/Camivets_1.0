package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SedesModel {
    public String id;
    public String name;
    public boolean everytime;
    public List<ServicesModel> services;
    public LocationModel location;

    // Constructor vacío requerido por Firestore
    public SedesModel(){

    }

    public SedesModel(String idSede, String name, boolean everytime, ArrayList<ServicesModel> services,LocationModel location) {
        this.id = idSede;
        this.name = name;
        this.everytime = everytime;
        this.services = services;
        this.location = location;

    }

    // Método para convertir SedesModel a Map
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("location", location);
        map.put("services", services);
        map.put("everytime", everytime);
        return map;
    }

    public void setIdSede(String idSede) {
        this.id = idSede;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServices(List<ServicesModel> services) {
        this.services = services;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    /*public String getIdSede() {
        return id;
    }*/

    public String getName() {
        return name;
    }

   public List<ServicesModel> getServices() {
        return services;
    }

    public LocationModel getLocation() {
        return location;
    }

    public boolean isEverytime() {
        return everytime;
    }

    public void setEverytime(boolean everytime) {
        everytime = everytime;
    }
}


