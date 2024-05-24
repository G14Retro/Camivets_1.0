package com.example.camivets.ui.sedes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.camivets.R;
import com.example.camivets.databinding.FragmentPlanesBinding;
import com.example.camivets.databinding.FragmentSedeDatailBinding;
import com.example.camivets.ui.planes.PlanesViewModel;

import Interfaces.AdapterListener;
import models.SedesModel;

public class SedeDatailFragment extends Fragment implements AdapterListener {

    private FragmentSedeDatailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SedesViewModel sedeViewModel =
                new ViewModelProvider(this).get(SedesViewModel.class);

        binding = FragmentSedeDatailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(SedesModel sede) {
        TextView name = binding.textSedeDetail;
        name.setText("Veterinaria");
    }
}