package com.example.camivets.ui.veterinarias;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.camivets.R;
import com.example.camivets.databinding.FragmentVeterinariasBinding;
import com.example.camivets.ui.sedes.SedesAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.SedesModel;

public class VeterinariasFragment extends Fragment implements OnMapReadyCallback {

    private FirebaseFirestore mFireStore;
    private FirebaseAuth mAuth;
    private List<SedesModel> sedes;

    private FragmentVeterinariasBinding binding;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVeterinariasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        GetSedes();
    }

    private void GetSedes(){
        sedes = new ArrayList<>();
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        SedesModel sedeModel = new SedesModel();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mFireStore.collection("Sedes")
                    .whereEqualTo("everytime", true)
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
                                LatLng ubicacionInicial = new LatLng(0, 0);
                                for (SedesModel sede : sedes) {
                                    ubicacionInicial = new LatLng(Double.valueOf(sede.location.Coordinates.altitude),
                                            Double.valueOf(sede.location.Coordinates.latitude));
                                    mMap.addMarker(new MarkerOptions()
                                            .position(ubicacionInicial)
                                            .title(sede.name));
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInicial, 15));
                            } else {
                                // No se encontraron documentos con el correo electrónico del usuario actual
                                Toast.makeText(getContext(), "No se encontró información", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: No se encontró información en la base de datos");
                            }
                        }
                    });
        }
    }

}
