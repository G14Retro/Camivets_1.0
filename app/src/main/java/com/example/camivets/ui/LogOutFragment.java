package com.example.camivets.ui;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.camivets.NavCamivets;
import com.example.camivets.R;
import com.example.camivets.RegisterActivity;
import com.example.camivets.databinding.FragmentLogOutBinding;
import com.example.camivets.databinding.FragmentVeterinariasBinding;
import com.example.camivets.ui.veterinarias.VeterinariasViewModel;

public class LogOutFragment extends Fragment {

    private FragmentLogOutBinding binding;
    private static final String KEY = "stored_login";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogOutViewModel logOutViewModel =
                new ViewModelProvider(this).get(LogOutViewModel.class);

        binding = FragmentLogOutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LogOut(MenuItem menuItem){

    }

}