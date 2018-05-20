package com.caramelheaven.gymdatabase.controllers.clients;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.datasourse.model.ClientDirectory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<HashMap<String, String>> listClients;
    List<HashMap<String, String>> listGymMemberships;

    public ClientsAdapter(List<HashMap<String, String>> listClients, List<HashMap<String, String>> listGymMemberships) {
        this.listClients = listClients;
        this.listGymMemberships = listGymMemberships;
    }

    public List<HashMap<String, String>> getClientsFromAdapter() {
        /*List<ClientDirectory> clientsList = new ArrayList<>();
        //nice realisation ;)
        List<HashMap<String, String>> searchList = listClients;
        for (HashMap<String, String> temp : searchList) {
            String id_client = null, first_name = null, gym_membership_id = null, last_name = null;
            for (Map.Entry<String, String> entry : temp.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                switch (key) {
                    case "id_client":
                        id_client = value;
                        break;
                    case "first_name":
                        first_name = value;
                        break;
                    case "gym_membership_id":
                        gym_membership_id = value;
                        break;
                    case "last_name":
                        last_name = value;
                }
            }
            clientsList.add(new ClientDirectory(first_name, gym_membership_id, id_client, last_name));
        }*/
        return listClients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ClientsVH clientVH = (ClientsVH) holder;
        HashMap<String, String> hashClient = listClients.get(position);
        HashMap<String, String> hashGym = listGymMemberships.get(Integer.parseInt(hashClient.get("gym_membership_id")));

        System.out.println("current id gym: " + hashClient.get("gym_membership_id"));

        clientVH.firstName.setText(hashClient.get("first_name"));
        clientVH.lastName.setText(hashClient.get("last_name"));
        clientVH.id.setText("id: " + hashClient.get("id_client"));
        clientVH.gymId.setText("Абонимент №: " + hashClient.get("gym_membership_id"));

        clientVH.dayStart.setText("Day of start: " + hashGym.get("day_of_start"));
        clientVH.dayOfEnd.setText("Day of end: " + hashGym.get("day_of_end"));
    }

    public void clear() {
        listClients.clear();
        listGymMemberships.clear();
    }

    @Override
    public int getItemCount() {
        return listClients.size();
    }

    public void updateFromSearch(List<HashMap<String, String>> clientDirectories) {
        listClients = clientDirectories;
        notifyDataSetChanged();
    }

    private class ClientsVH extends RecyclerView.ViewHolder {

        TextView firstName;
        TextView lastName;
        TextView id;
        TextView gymId;
        TextView dayOfEnd;
        TextView dayStart;

        public ClientsVH(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            id = itemView.findViewById(R.id.idClient);
            gymId = itemView.findViewById(R.id.idGym);
            dayOfEnd = itemView.findViewById(R.id.dayEnd);
            dayStart = itemView.findViewById(R.id.dayStart);
        }
    }

    public void updateList(List<HashMap<String, String>> clients, List<HashMap<String, String>> gyms) {
        for (int i = 0; i < clients.size(); i++) {
            if (listClients.contains(clients.get(i))) {
                System.out.println("Не добавляем эту паршивку");
            } else {
                listClients.add(clients.get(i));
                listGymMemberships.add(gyms.get(i));
                notifyItemInserted(listClients.size() - 1);
            }
        }
        notifyDataSetChanged();
    }
}
