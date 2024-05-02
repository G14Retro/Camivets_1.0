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
import android.widget.TextView;

import com.example.camivets.NavCamivets;
import com.example.camivets.R;
import com.example.camivets.databinding.FragmentSedesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.SedesModel;

public class  SedesFragment extends Fragment {

    private FragmentSedesBinding binding;
    private List<SedesModel> sedes;
    private RecyclerView sedeRecyclerView;
    private FirebaseFirestore mFireStore;

    public SedesFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SedesViewModel sedesViewModel =
                new ViewModelProvider(this).get(SedesViewModel.class);

        binding = FragmentSedesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View rootView = inflater.inflate(R.layout.fragment_sedes,container,false);
        init(rootView);

        return init(rootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public View init(View rootView){
        sedes = new ArrayList<>();
        mFireStore = FirebaseFirestore.getInstance();
        mFireStore.collection("Sedes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                sedes.add(new SedesModel("2", (String) document.getData().get("name"),true,"falsa123"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        sedes.add(new SedesModel("2","Sede Engativa",true,"falsa123"));
        sedes.add(new SedesModel("2","Sede Suba",true,"falsa123"));
        sedes.add(new SedesModel("2","Sede Norte",true,"falsa123"));
        sedes.add(new SedesModel("2","Sede Sur",true,"falsa123"));
        sedes.add(new SedesModel("2","Sede Centro",true,"falsa123"));
        sedes.add(new SedesModel("2","Sede 85",true,"falsa123"));
        sedeRecyclerView = rootView.findViewById(R.id.list_sedes);
        sedeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SedesAdapter sedesAdapter = new SedesAdapter(sedes);
        sedeRecyclerView.setAdapter(sedesAdapter);
        return  rootView;
    }

}