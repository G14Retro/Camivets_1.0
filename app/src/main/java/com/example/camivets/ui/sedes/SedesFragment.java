package com.example.camivets.ui.sedes;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camivets.NavCamivets;
import com.example.camivets.R;
import com.example.camivets.databinding.FragmentSedesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import models.LocationModel;
import models.SedesModel;
import models.ServicesModel;

public class  SedesFragment extends Fragment {

    private FragmentSedesBinding binding;
    private List<SedesModel> sedes;
    private RecyclerView sedeRecyclerView;
    private FirebaseFirestore mFireStore;

    private FirebaseAuth mAuth;

    public SedesFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SedesViewModel sedesViewModel =
                new ViewModelProvider(this).get(SedesViewModel.class);

        binding = FragmentSedesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View rootView = inflater.inflate(R.layout.fragment_sedes,container,false);

        return init(rootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private View init(View rootView){
        sedes = new ArrayList<>();
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        SedesModel sedeModel = new SedesModel();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mFireStore.collection("Sedes")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // Verificar si se encontró algún documento
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(i);
                                    SedesModel sede = documentSnapshot.toObject(SedesModel.class);
                                    sede.id = documentSnapshot.getId();
                                    sedes.add(sede);
                                }
                                sedeRecyclerView = rootView.findViewById(R.id.list_sedes);
                                sedeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                SedesAdapter sedesAdapter = new SedesAdapter(sedes);
                                sedeRecyclerView.setAdapter(sedesAdapter);
                            } else {
                                // No se encontraron documentos con el correo electrónico del usuario actual
                                Toast.makeText(getContext(), "No se encontró información", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: No se encontró información en la base de datos");
                            }
                        }
                    });
        } else {
            // No se encontraron documentos con el correo electrónico del usuario actual
            Toast.makeText(getContext(), "No se encontró el perfil del usuario", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onSuccess: No se encontró el perfil del usuario");
        }
        return  rootView;
    }

    public void goToSedeById(View view){
        view.getId();
    }

}