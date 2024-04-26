package com.example.camivets;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

import models.UserModel;

public class RegisterActivity extends AppCompatActivity {
    TextView name, secondName, lastName, secondLastName, email, password, confirmPassword;

    // Firebase Firestore
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        //Inicializar FireStore Auth
        mAuth = FirebaseAuth.getInstance();

        // Asignar vistas
        name = findViewById(R.id.txtName);
        secondName = findViewById(R.id.txtSecondName);
        lastName = findViewById(R.id.txtLastName);
        secondLastName = findViewById(R.id.txtSecondLastName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtConfirmPassword);
    }

    public void Save(View view) {
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto UserModel
        UserModel userRegister = new UserModel();
        userRegister.Name = name.getText().toString();
        userRegister.SecondName = secondName.getText().toString();
        userRegister.LastName = lastName.getText().toString();
        userRegister.SecondLastname = secondLastName.getText().toString();
        userRegister.Email = email.getText().toString();
        userRegister.Password = password.getText().toString();

        // Crear un mapa de datos para el usuario
        Map<String, Object> userData = new HashMap<>();
        userData.put("Name", userRegister.Name);
        userData.put("SecondName", userRegister.SecondName);
        userData.put("LastName", userRegister.LastName);
        userData.put("SecondLastName", userRegister.SecondLastname);
        userData.put("Email", userRegister.Email);
        userData.put("Password", userRegister.Password);

        //Consumo API FireBase registro
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Éxito al agregar el usuario
                            Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            // Agregar el usuario a Firestore
                            db.collection("Clientes")
                                    .add(userData)
                                    .addOnSuccessListener(documentReference -> {
                                        // Obtener la referencia al documento agregado
                                        Log.d("DocumentID", "Document ID: " + documentReference.getId());
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error al agregar el usuario
                                        Toast.makeText(RegisterActivity.this, "Error al registrar el usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                            Intent actHome = new Intent(RegisterActivity.this, NavCamivets.class);
                            startActivity(actHome);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}