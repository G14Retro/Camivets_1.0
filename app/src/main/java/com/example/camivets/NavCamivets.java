package com.example.camivets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.camivets.databinding.ActivityNavCamivetsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class NavCamivets extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavCamivetsBinding binding;
    private  NavController navController;
    private static final String KEY = "stored_login", KEY_REGISTER = "stored_register";

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static final String TAG = "ProfileFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavCamivetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        validateRegister();

        setSupportActionBar(binding.appBarNavCamivets.toolbar);
        binding.appBarNavCamivets.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_servicios, R.id.nav_sedes, R.id.nav_veterinarias, R.id.nav_planes, R.id.nav_profile, R.id.nav_exit)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_camivets);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
                if (id==R.id.nav_exit){
                    logOutDialog(NavCamivets.this);
                }
                validateRegister();
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem,navController);
                //This is for closing the drawer after acting on it
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav__camivets, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_camivets);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logOut(){
        SharedPreferences sharedPreferences = getSharedPreferences("isAuth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("stored_login",false);
        editor.apply();
        Intent actMain = new Intent(NavCamivets.this, MainActivity.class);
        startActivity(actMain);
    }

    private void logOutDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Estas seguro de cerrar sesión");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOut();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void validateRegister(){

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Realizar una consulta para buscar el documento del cliente por correo electrónico
            db.collection("Clientes")
                    .whereEqualTo("Email", userEmail) // Asegúrate de que coincida con el nombre del campo en tu base de datos
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // Verificar si se encontró algún documento
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Suponiendo que solo hay un documento con el correo electrónico único,
                                // puedes acceder al primer documento en la lista de resultados
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                                // Agregar un log para verificar si la consulta trae algo
                                Log.d(TAG, "onSuccess: Se encontraron documentos en la colección 'Clientes'");


                                // Obtener los datos del documento y mostrarlos en los EditText
                                String name = Objects.requireNonNull(documentSnapshot.getString("Name"));
                                String lastName = Objects.requireNonNull(documentSnapshot.getString("LastName"));
                                String secondName = documentSnapshot.getString("SecondName");
                                String secondLastName = documentSnapshot.getString("SecondLastName");
                                String email = Objects.requireNonNull(documentSnapshot.getString("Email"));

                                // Implementar la lógica para actualizar la información del perfil cuando se hace clic en el botón "Guardar cambios"



                            } else {
                                // No se encontraron documentos con el correo electrónico del usuario actual
                                navController.navigate(R.id.nav_profile);
                                Log.d(TAG, "onSuccess: No se encontró el perfil del usuario");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar cualquier error de consulta
                            Toast.makeText(getParent(), "Error al buscar el perfil del usuario", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onFailure: Error al buscar el perfil del usuario", e);
                        }
                    });
        } else {
            // Manejar el caso en que el usuario no esté autenticado
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreateView: Usuario no autenticado");
        }
    }

}