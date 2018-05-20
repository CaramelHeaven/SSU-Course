package com.caramelheaven.gymdatabase.controllers.individuals;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caramelheaven.gymdatabase.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividualAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<HashMap<String, String>> individualsList;
    List<HashMap<String, String>> clientsList;
    List<HashMap<String, String>> trainersList;

    public IndividualAdapter(List<HashMap<String, String>> individualsList, List<HashMap<String, String>> clientsList,
                             List<HashMap<String, String>> trainersList) {
        this.individualsList = individualsList;
        this.clientsList = clientsList;
        this.trainersList = trainersList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_individual, parent, false);
        return new IndividualVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IndividualVH individualVH = (IndividualVH) holder;

        HashMap<String, String> hashIndividual = individualsList.get(position);
        HashMap<String, String> hashClient = clientsList.get(Integer.parseInt(hashIndividual.get("client_id")));
        HashMap<String, String> hashTrainer = trainersList.get(Integer.parseInt(hashIndividual.get("trainer_id")));

        for (Map.Entry<String, String> entry : hashClient.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("check key client: " + key);
            System.out.println("check value client: " + value);
        }
        for (Map.Entry<String, String> entry : hashTrainer.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("check key trainer: " + key);
            System.out.println("check value trainer: " + value);
        }


        individualVH.id_individual_work.setText("№ " + hashIndividual.get("id_individual_work") + ": ");
        individualVH.time_start_end.setText("time start: " + hashIndividual.get("time_start") + " , time end: " + hashIndividual.get("time_end"));

        individualVH.client_id.setText("client: " + hashClient.get("first_name") + " " + hashClient.get("last_name") + ", ");

        individualVH.trainer_id.setText("trainer: " + hashTrainer.get("first_name") + " " + hashTrainer.get("last_name"));

    }

    @Override
    public int getItemCount() {
        return individualsList.size();
    }

    public void clear() {
        individualsList.clear();
    }

    public void updateList(List<HashMap<String, String>> individual, List<HashMap<String, String>> client,
                           List<HashMap<String, String>> trainer) {
        for (int i = 0; i < individual.size(); i++) {
            if (individualsList.contains(individual.get(i))) {
                System.out.println("Не добавляем эту паршивку");
            } else {
                individualsList.add(individual.get(i));
                notifyItemInserted(individualsList.size() - 1);
            }
        }
        for (int i = 0; i < client.size(); i++) {
            if (clientsList.contains(client.get(i))) {
                //nothing
            } else {
                clientsList.add(client.get(i));
                notifyItemInserted(clientsList.size() - 1);
            }
        }
        for (int i = 0; i < trainer.size(); i++) {
            trainersList.add(trainer.get(i));
            notifyItemInserted(trainersList.size() - 1);
        }
        notifyDataSetChanged();
    }

    private class IndividualVH extends RecyclerView.ViewHolder {

        TextView time_start_end;
        TextView client_id;
        TextView id_individual_work;
        TextView day_working;
        TextView trainer_id;

        public IndividualVH(View itemView) {
            super(itemView);
            time_start_end = itemView.findViewById(R.id.time_start_end);
            client_id = itemView.findViewById(R.id.client_id);
            id_individual_work = itemView.findViewById(R.id.id_individual_work);
            day_working = itemView.findViewById(R.id.day_working);
            trainer_id = itemView.findViewById(R.id.trainer_id);
        }
    }

}
