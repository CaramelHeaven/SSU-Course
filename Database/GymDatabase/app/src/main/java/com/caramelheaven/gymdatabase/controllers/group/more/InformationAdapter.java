package com.caramelheaven.gymdatabase.controllers.group.more;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.datasourse.model.GroupWork;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<HashMap<String, String>> groupWorkList;
    List<HashMap<String, String>> clientsList;
    List<HashMap<String, String>> clientTraceList;

    public InformationAdapter(List<HashMap<String, String>> groupWorkList, List<HashMap<String, String>> clientsList,
                              List<HashMap<String, String>> clientTraceList) {
        this.groupWorkList = groupWorkList;
        this.clientsList = clientsList;
        this.clientTraceList = clientTraceList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information, parent, false);
        return new InformationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InformationVH informationVH = (InformationVH) holder;
        HashMap<String, String> hashGroupWork = groupWorkList.get(position);
        HashMap<String, String> hashClient = new HashMap<>();
        HashMap<String, String> hashClientTrace = clientTraceList.get(Integer.parseInt(hashGroupWork.get("get_people")));

        if (Integer.parseInt(hashClientTrace.get("client_id")) >= clientsList.size()) {
            System.out.println("Ну, бывает: " + Integer.parseInt(hashClientTrace.get("client_id")) + " size: " + clientsList.size());
        } else {
            hashClient = clientsList.get(Integer.parseInt(hashClientTrace.get("client_id")));
            informationVH.namePerson.setText(hashClient.get("first_name") + " " + hashClient.get("last_name"));

            informationVH.start_end.setText("time start: " + hashClientTrace.get("client_date_start") + " time end: " + hashClientTrace.get("client_date_end"));
            informationVH.nameGroup.setText(hashGroupWork.get("name_group"));
        }
    }

    @Override
    public int getItemCount() {
        return groupWorkList.size();
    }

    private class InformationVH extends RecyclerView.ViewHolder {
        TextView nameGroup;
        TextView namePerson;
        TextView start_end;

        public InformationVH(View itemView) {
            super(itemView);
            nameGroup = itemView.findViewById(R.id.name_group);
            namePerson = itemView.findViewById(R.id.get_people);
            start_end = itemView.findViewById(R.id.client_start_end);
        }
    }

    public void updateList(List<HashMap<String, String>> temp, List<HashMap<String, String>> tempClient,
                           List<HashMap<String, String>> tempClientTrace) {
        for (int i = 0; i < temp.size(); i++) {
            if (groupWorkList.contains(temp.get(i))) {
                //hothing
            } else {
                groupWorkList.add(temp.get(i));
                notifyItemInserted(groupWorkList.size() - 1);
            }
        }
        for (int i = 0; i < tempClient.size(); i++) {
            if (clientsList.contains(tempClient.get(i))) {
                //nothing
            } else {
                clientsList.add(tempClient.get(i));
                notifyItemInserted(clientsList.size() - 1);
            }
        }
        for (int i = 0; i < tempClientTrace.size(); i++) {
            if (clientTraceList.contains(tempClientTrace.get(i))) {
                //nothing
            } else {
                clientTraceList.add(tempClientTrace.get(i));
                notifyItemInserted(clientTraceList.size() - 1);
            }
        }
        notifyDataSetChanged();
    }
}
