package com.example.camivets.ui.sedes;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camivets.R;

import java.util.List;

import Interfaces.AdapterListener;
import models.SedesModel;

public class SedesAdapter extends RecyclerView.Adapter<SedesAdapter.SedesViewHolder> {
    private List<SedesModel> sedesList;
    private Context context;

    private NavController navController;
    private AdapterListener listener;

    public SedesAdapter(List<SedesModel> sedesList, NavController  navController) {
        this.sedesList = sedesList;
        this.navController = navController;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public SedesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_sedes,parent,false);
        return new SedesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SedesAdapter.SedesViewHolder holder, int position) {
        SedesModel sedesModel = sedesList.get(position);
        holder.name.setText(sedesModel.getName());
        holder.address.setText(sedesModel.getLocation().Address);
        holder.btnSede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onItemSelected(sedesModel);
                navController.navigate(R.id.nav_sedeDatail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sedesList.size();
    }

    public static class SedesViewHolder extends RecyclerView.ViewHolder{
        TextView name,address;
        Button btnSede;
        public SedesViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nameSede);
            address = itemView.findViewById(R.id.address);
            btnSede = itemView.findViewById(R.id.btnSede);
        }
    }
}
