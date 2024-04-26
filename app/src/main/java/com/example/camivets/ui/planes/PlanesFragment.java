package com.example.camivets.ui.planes;

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
import com.example.camivets.databinding.FragmentSedesBinding;
import com.example.camivets.ui.sedes.SedesViewModel;

public class PlanesFragment extends Fragment {

    private FragmentPlanesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlanesViewModel planesViewModel =
                new ViewModelProvider(this).get(PlanesViewModel.class);

        binding = FragmentPlanesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlanes;
        planesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}