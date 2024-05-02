package com.example.camivets.ui.veterinarias;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.camivets.R;
import com.example.camivets.databinding.FragmentVeterinariasBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VeterinariasFragment extends Fragment implements OnMapReadyCallback {

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

        // Configurar el mapa según tus necesidades
        LatLng ubicacionInicial = new LatLng(-34, 151); // Latitud y longitud inicial (por ejemplo)
        mMap.addMarker(new MarkerOptions().position(ubicacionInicial).title("Marcador de ubicación inicial"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInicial, 10)); // Zoom en el mapa
    }
}
