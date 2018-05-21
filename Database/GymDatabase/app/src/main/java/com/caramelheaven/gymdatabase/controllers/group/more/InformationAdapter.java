package com.caramelheaven.gymdatabase.controllers.group.more;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.datasourse.model.GroupWork;

import java.util.HashMap;
import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<HashMap<String, String>> groupWorkList;

    public InformationAdapter(List<HashMap<String, String>> groupWorkList) {
        this.groupWorkList = groupWorkList;
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
        HashMap<String, String> hashPerson = groupWorkList.get(position);

        informationVH.namePerson.setText(hashPerson.get("get_people"));
        informationVH.nameGroup.setText(hashPerson.get("name_group"));
    }

    @Override
    public int getItemCount() {
        return groupWorkList.size();
    }

    private class InformationVH extends RecyclerView.ViewHolder {
        TextView nameGroup;
        TextView namePerson;

        public InformationVH(View itemView) {
            super(itemView);
            nameGroup = itemView.findViewById(R.id.name_group);
            namePerson = itemView.findViewById(R.id.get_people);
        }
    }

    public void updateList(List<HashMap<String, String>> temp) {
        for (int i = 0; i < temp.size(); i++) {
            if (groupWorkList.contains(temp.get(i))) {
                //hothing
            } else {
                groupWorkList.add(temp.get(i));
                notifyItemInserted(groupWorkList.size() - 1);
            }
        }
        notifyDataSetChanged();
    }
}
