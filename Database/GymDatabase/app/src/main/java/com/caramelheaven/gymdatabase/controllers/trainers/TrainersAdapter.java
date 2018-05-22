package com.caramelheaven.gymdatabase.controllers.trainers;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caramelheaven.gymdatabase.R;

import java.util.HashMap;
import java.util.List;

public class TrainersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<HashMap<String, String>> trainersList;

    public TrainersAdapter(List<HashMap<String, String>> trainersList) {
        this.trainersList = trainersList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trainer, parent, false);
        return new TrainerVH(view);
    }


    public List<HashMap<String, String>> getTrainersList() {
        return trainersList;
    }

    public void clear() {
        trainersList.clear();
    }

    public void updateAdapterFromDeleted(List<HashMap<String, String>> updated) {
        trainersList = updated;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TrainerVH trainerVH = (TrainerVH) holder;
        HashMap<String, String> hashTrainer = trainersList.get(position);

        trainerVH.trainer_describe.setText("№: " + hashTrainer.get("id_trainer") +
                " " + hashTrainer.get("first_name") +
                " " + hashTrainer.get("last_name"));

        if (TrainersFragment.fiss == 22){
            trainerVH.trainer_price.setText(hashTrainer.get("salary") + " rub.");
        } else {
            trainerVH.trainer_price.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return trainersList.size();
    }

    private class TrainerVH extends RecyclerView.ViewHolder {

        TextView trainer_describe;
        TextView trainer_price;

        public TrainerVH(View itemView) {
            super(itemView);
            trainer_describe = itemView.findViewById(R.id.trainer_describe);
            trainer_price = itemView.findViewById(R.id.trainer_price);
        }
    }

    public void updateFromSearch(List<HashMap<String, String>> temp) {
        trainersList = temp;
        notifyDataSetChanged();
    }

    public void updateList(List<HashMap<String, String>> trainers) {
        for (int i = 0; i < trainers.size(); i++) {
            if (trainersList.contains(trainers.get(i))) {
                //nothing
            } else {
                trainersList.add(trainers.get(i));
                notifyItemInserted(trainersList.size() - 1);
            }
        }
        notifyDataSetChanged();
    }
}
