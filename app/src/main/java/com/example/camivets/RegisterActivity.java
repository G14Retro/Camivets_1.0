package com.example.camivets;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    TextView name,secondName,lastName,secondLastName,email,password,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.txtName);
        secondName = findViewById(R.id.txtSecondName);
        lastName = findViewById(R.id.txtLastName);
        secondLastName = findViewById(R.id.txtSecondLastName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtConfirmPassword);
    }

    public void Save(View view){
        if (!password.getText().toString().equals(confirmPassword.toString())){
            Toast.makeText(RegisterActivity.this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            return;
        }
    }
}