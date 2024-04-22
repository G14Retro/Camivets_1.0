package com.example.camivets.ui.veterinarias;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.camivets.databinding.FragmentVeterinariasBinding;

public class VeterinariasFragment extends Fragment {

    private FragmentVeterinariasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VeterinariasViewModel veterinariasViewModel =
                new ViewModelProvider(this).get(VeterinariasViewModel.class);

        binding = FragmentVeterinariasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textVeterinarias;
        veterinariasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}