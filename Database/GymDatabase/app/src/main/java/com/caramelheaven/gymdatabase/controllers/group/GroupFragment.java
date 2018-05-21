package com.caramelheaven.gymdatabase.controllers.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.controllers.group.more.GroupInformationActivity;
import com.caramelheaven.gymdatabase.utils.OnItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupFragment extends Fragment {

    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    private Toolbar toolbar;
    private DatabaseReference firebase;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabUpdate;
    private FloatingActionButton fabDelete;
    private SwipeRefreshLayout swipeRefresh;

    private Menu menu;

    public static GroupFragment newInstance() {
        Bundle args = new Bundle();
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        toolbar = view.findViewById(R.id.toolbar);
        fabAdd = view.findViewById(R.id.fab);
        fabUpdate = view.findViewById(R.id.fabInsert);
        fabDelete = view.findViewById(R.id.fabDelete);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);

        setActionBar(toolbar);

        //base lists for reflecting inside UI
        List<HashMap<String, String>> listGroup;
        List<HashMap<String, String>> listPlace;
        List<HashMap<String, String>> listKind;
        List<HashMap<String, String>> listTrainers;

        listGroup = new ArrayList<>();
        listPlace = new ArrayList<>();
        listKind = new ArrayList<>();
        listTrainers = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new GroupAdapter(listGroup, listPlace, listKind, listTrainers);

        swipeRefresh.setColorSchemeResources(R.color.fabInsert);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                setGroupFirebase();
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        SharedPreferences sharedPreference = getActivity().getSharedPreferences("GYM", Context.MODE_PRIVATE);
        String login = sharedPreference.getString("login", null);

        if (login.equals("admin")) {
            setFABs();
        } else {
            fabAdd.setVisibility(View.GONE);
            fabDelete.setVisibility(View.GONE);
            fabUpdate.setVisibility(View.GONE);
        }

        setGroupFirebase();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int temp = position + 1;
                Intent intent = new Intent(getContext(), GroupInformationActivity.class);
                intent.putExtra("KEY", String.valueOf(temp));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Поиск");
                //https://stackoverflow.com/a/21549541
                //text color
                ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.WHITE);
                //hint
                ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);
                searchView.setMaxWidth(Integer.MAX_VALUE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        querySearch(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                return true;
            case R.id.action_logout:
                Toast.makeText(getContext(), "hahaha!", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void querySearch(String inputText) {
        inputText = inputText.toLowerCase();
        List<HashMap<String, String>> hashClients = adapter.getGroupList();
        List<HashMap<String, String>> updatedList = new ArrayList<>();
        if (hashClients.size() == 0) {
            Toast.makeText(getContext(), "Size is 0", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < hashClients.size(); i++) {
                HashMap<String, String> tempHash = hashClients.get(i);
                if ((tempHash.get("day_of_week") != null && tempHash.get("day_of_week").toLowerCase().contains(inputText))) {
                    updatedList.add(tempHash);
                }
            }
            adapter.updateFromSearch(updatedList);
        }
    }

    private void setGroupFirebase() {
        firebase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> grouplist = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/GroupSchedule")
                        .getValue();

                ArrayList<HashMap<String, String>> listClients = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/ClientDirectory")
                        .getValue();

                ArrayList<HashMap<String, String>> placeList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/Place")
                        .getValue();

                ArrayList<HashMap<String, String>> kindofSportList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/KindOfSport")
                        .getValue();

                ArrayList<HashMap<String, String>> trainerList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/TrainerDirectory")
                        .getValue();

                grouplist.removeAll(Collections.singleton(null));
                listClients.removeAll(Collections.singleton(null));
                placeList.removeAll(Collections.singleton(null));
                kindofSportList.removeAll(Collections.singleton(null));
                trainerList.removeAll(Collections.singleton(null));

                for (HashMap<String, String> place : trainerList) {
                    for (Map.Entry<String, String> entry : place.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        System.out.println("check key: " + key);
                        System.out.println("check value: " + value);
                    }
                }

                adapter.updateList(grouplist, placeList, kindofSportList, trainerList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setActionBar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void setFABs() {
        fabAdd.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Добавляем групповое занятие", Toast.LENGTH_SHORT).show();
            DialogAddFragment dialog = DialogAddFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabUpdate.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Изменяем групповое занятие", Toast.LENGTH_SHORT).show();
            DialogUpdateFragment dialog = DialogUpdateFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabDelete.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Удаляем групповое занятие", Toast.LENGTH_SHORT).show();
            DialogDeleteFragment dialog = DialogDeleteFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });
    }
}
