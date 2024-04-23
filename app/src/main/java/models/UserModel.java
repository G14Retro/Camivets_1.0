package models;

import java.util.HashMap;
import java.util.Map;

public class UserModel {
    public String Name;
    public String SecondName;
    public String LastName;
    public String SecondLastname;
    public String Email;
    public String Password;

    // Constructor vacío requerido por Firestore
    public UserModel() {}

    // Constructor con parámetros
    public UserModel(String name, String secondName, String lastName, String secondLastname, String email, String password) {
        this.Name = name;
        this.SecondName = secondName;
        this.LastName = lastName;
        this.SecondLastname = secondLastname;
        this.Email = email;
        this.Password = password;
    }

    // Método para convertir UserModel a Map
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", Name);
        map.put("SecondName", SecondName);
        map.put("LastName", LastName);
        map.put("SecondLastname", SecondLastname);
        map.put("Email", Email);
        map.put("Password", Password);
        return map;
    }

    public void setPassword(String passwordStr) {
        this.Password = passwordStr;
    }

    public void setEmail(String emailStr) {
        this.Email = emailStr;
    }

    public void setLastName(String lastNameStr) {
        this.LastName = lastNameStr;
    }

    public void setSecondLastName(String secondLastNameStr) {
        this.SecondLastname = secondLastNameStr;
    }

    public void setSecondName(String secondNameStr) {
        this.SecondName = secondNameStr;
    }

    public void setName(String nameStr) {
        this.Name = nameStr;
    }
}
