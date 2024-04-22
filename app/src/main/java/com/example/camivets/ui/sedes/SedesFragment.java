package com.example.camivets.ui.sedes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.camivets.databinding.FragmentSedesBinding;

public class SedesFragment extends Fragment {

    private FragmentSedesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SedesViewModel sedesViewModel =
                new ViewModelProvider(this).get(SedesViewModel.class);

        binding = FragmentSedesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSedes;
        sedesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}