package com.example.camivets.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.camivets.R;
import com.example.camivets.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ViewPager2 viewPager;
    private TextView textViewInfo;
    private List<Integer> imageList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = binding.viewPager;
        textViewInfo = binding.textInfo;

        // Configurar el ViewPager2
        setupViewPager();

        // Observar cambios en el texto informativo
        homeViewModel.getText().observe(getViewLifecycleOwner(), textViewInfo::setText);

        return root;
    }

    private void setupViewPager() {
        // Crear una lista de identificadores de recursos de imágenes
        imageList = new ArrayList<>();
        imageList.add(getImageResourceId("imagen1")); // Nombre del archivo de imagen sin extensión
        imageList.add(getImageResourceId("imagen2"));
        imageList.add(getImageResourceId("imagen3"));

        // Crear una instancia de tu adaptador de ViewPager2
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageList, requireContext());

        // Configurar el adaptador para el ViewPager2
        viewPager.setAdapter(adapter);
    }

    private int getImageResourceId(String imageName) {
        return getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}