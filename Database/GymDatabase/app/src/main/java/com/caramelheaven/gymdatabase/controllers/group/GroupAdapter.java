package com.caramelheaven.gymdatabase.controllers.group;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.utils.OnItemClickListener;

import java.util.HashMap;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    List<HashMap<String, String>> groupList;
    List<HashMap<String, String>> placeList;
    List<HashMap<String, String>> kindOfSportList;
    List<HashMap<String, String>> trainersList;

    public GroupAdapter(List<HashMap<String, String>> groupList, List<HashMap<String, String>> placeList,
                        List<HashMap<String, String>> kindOfSportList, List<HashMap<String, String>> trainersList) {
        this.groupList = groupList;
        this.placeList = placeList;
        this.kindOfSportList = kindOfSportList;
        this.trainersList = trainersList;
    }

    public List<HashMap<String, String>> getGroupList() {
        return groupList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupVH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupVH groupVH = (GroupVH) holder;

        HashMap<String, String> hashGroup = groupList.get(position);
        HashMap<String, String> hashPlace = placeList.get(Integer.parseInt(hashGroup.get("place_id")));
        HashMap<String, String> hashKind = kindOfSportList.get(Integer.parseInt(hashGroup.get("type_of_sport_id")));
        HashMap<String, String> hashTrainer = trainersList.get(Integer.parseInt(hashGroup.get("trainer_id")));

        groupVH.day_of_week.setText(hashGroup.get("day_of_week"));
        groupVH.time_start.setText("time start: " + hashGroup.get("time_start"));
        groupVH.time_end.setText("time end: " + hashGroup.get("time_end"));

        //small bug, i wont fixing it because i'm tired
        if (hashGroup.get("group_id") == null) {
            groupVH.group_id.setText("Group № 1");
        } else {
            groupVH.group_id.setText("Group № " + hashGroup.get("group_id"));
        }

        groupVH.place_id.setText("place: " + hashPlace.get("name_place"));

        groupVH.type_of_sport_id.setText("kind of sport: " + hashKind.get("name_kind"));

        groupVH.trainer_id.setText("trainer: " + hashTrainer.get("first_name") + " " + hashTrainer.get("last_name") + ",");
    }

    public void clear() {
        groupList.clear();
    }

    public void updateList(List<HashMap<String, String>> group, List<HashMap<String, String>> place,
                           List<HashMap<String, String>> kind, List<HashMap<String, String>> trainers) {
        for (int i = 0; i < group.size(); i++) {
            if (groupList.contains(group.get(i))) {
                System.out.println("Не добавляем эту паршивку");
            } else {
                groupList.add(group.get(i));
                notifyItemInserted(groupList.size() - 1);
            }
        }
        for (int i = 0; i < place.size(); i++) {
            if (placeList.contains(place.get(i))) {
                //nothing
            } else {
                placeList.add(place.get(i));
                notifyItemInserted(placeList.size() - 1);
            }
        }
        for (int i = 0; i < kind.size(); i++) {
            kindOfSportList.add(kind.get(i));
            notifyItemInserted(kindOfSportList.size() - 1);
        }
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

    public void updateFromSearch(List<HashMap<String, String>> temp) {
        groupList = temp;
        notifyDataSetChanged();
    }

    public void updateAdapterFromDeleted(List<HashMap<String, String>> updated) {
        groupList = updated;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    private class GroupVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView day_of_week;
        TextView time_start;
        TextView time_end;
        TextView trainer_id;
        TextView type_of_sport_id;
        TextView group_id;
        TextView place_id;
        CardView cardView;

        GroupVH(View itemView) {
            super(itemView);
            day_of_week = itemView.findViewById(R.id.day_of_week);
            time_start = itemView.findViewById(R.id.time_start);
            time_end = itemView.findViewById(R.id.time_end);
            trainer_id = itemView.findViewById(R.id.trainer_id);
            type_of_sport_id = itemView.findViewById(R.id.type_of_sport_id);
            group_id = itemView.findViewById(R.id.group_id);
            place_id = itemView.findViewById(R.id.place_id);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
