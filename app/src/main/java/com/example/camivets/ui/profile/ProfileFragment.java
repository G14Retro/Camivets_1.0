package com.example.camivets.ui.profile;

import android.os.Bundle;
import android.util.Log; // Importamos la clase Log para los logs
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.camivets.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static final String TAG = "ProfileFragment"; // Etiqueta para los logs

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar Firebase Authentication y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Obtener referencia al documento del perfil del usuario actual
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

                                // Obtener las vistas correspondientes
                                EditText editName = binding.editName;
                                EditText editLastName = binding.lastName;
                                EditText editSecondName = binding.Secondname;
                                EditText editSecondLastName = binding.secondLastname;
                                EditText editEmail = binding.email;
                                Button btnSaveChanges = binding.btnSaveChanges;
                                Button btnVolver = binding.btnBack; // Botón para volver

                                // Obtener los datos del documento y mostrarlos en los EditText
                                String name = Objects.requireNonNull(documentSnapshot.getString("Name"));
                                String lastName = Objects.requireNonNull(documentSnapshot.getString("LastName"));
                                String secondName = documentSnapshot.getString("SecondName");
                                String secondLastName = documentSnapshot.getString("SecondLastName");
                                String email = Objects.requireNonNull(documentSnapshot.getString("Email"));

                                editName.setText(name);
                                editLastName.setText(lastName);
                                editSecondName.setText(secondName != null ? secondName : "");
                                editSecondLastName.setText(secondLastName != null ? secondLastName : "");
                                editEmail.setText(email);

                                // Implementar la lógica para actualizar la información del perfil cuando se hace clic en el botón "Guardar cambios"
                                btnSaveChanges.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Lógica para guardar cambios
                                        Log.d(TAG, "onClick: Guardar cambios");
                                        // Aquí puedes agregar alertas o cualquier otra lógica que desees
                                    }
                                });

                                // Implementar la lógica para el botón de volver
                                btnVolver.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Acción para volver, por ejemplo:
                                        requireActivity().onBackPressed();
                                    }
                                });
                            } else {
                                // No se encontraron documentos con el correo electrónico del usuario actual
                                Toast.makeText(getContext(), "Por favor completa tu registro", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: No se encontró el perfil del usuario");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar cualquier error de consulta
                            Toast.makeText(getContext(), "Error al buscar el perfil del usuario", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onFailure: Error al buscar el perfil del usuario", e);
                        }
                    });
        } else {
            // Manejar el caso en que el usuario no esté autenticado
            Toast.makeText(getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreateView: Usuario no autenticado");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
