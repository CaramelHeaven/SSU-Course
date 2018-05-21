package com.caramelheaven.gymdatabase.controllers.trainers;

import android.content.Context;
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
import com.caramelheaven.gymdatabase.controllers.clients.DialogAddFragment;
import com.caramelheaven.gymdatabase.controllers.clients.DialogDeleteFragment;
import com.caramelheaven.gymdatabase.controllers.clients.DialogUpdateFragment;
import com.caramelheaven.gymdatabase.controllers.group.GroupAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainersFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrainersAdapter adapter;
    private Toolbar toolbar;
    private DatabaseReference firebase;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabUpdate;
    private FloatingActionButton fabDelete;
    private SwipeRefreshLayout swipeRefresh;

    private Menu menu;

    public static TrainersFragment newInstance() {
        Bundle args = new Bundle();
        TrainersFragment fragment = new TrainersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_trainers, container, false);
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        List<HashMap<String, String>> listTrainers = new ArrayList<>();

        adapter = new TrainersAdapter(listTrainers);

        swipeRefresh.setColorSchemeResources(R.color.fabInsert);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                setTrainersFirebase();
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
                ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.WHITE);
                //hint
                ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);
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
        System.out.println("Зашел в querySearch: " + inputText);
        List<HashMap<String, String>> hashClients = adapter.getTrainersList();
        List<HashMap<String, String>> updatedList = new ArrayList<>();
        if (hashClients.size() == 0) {
            Toast.makeText(getContext(), "Size is 0", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < hashClients.size(); i++) {
                HashMap<String, String> tempHash = hashClients.get(i);
                if ((tempHash.get("first_name") != null && tempHash.get("first_name").toLowerCase().contains(inputText)) ||
                        (tempHash.get("last_name") != null && tempHash.get("last_name").toLowerCase().contains(inputText))) {
                    updatedList.add(tempHash);
                }
            }
            adapter.updateFromSearch(updatedList);
        }
    }

    private void setTrainersFirebase() {
        firebase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<HashMap<String, String>> trainerList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/TrainerDirectory")
                        .getValue();

                for (HashMap<String, String> place : trainerList) {
                    for (Map.Entry<String, String> entry : place.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        System.out.println("check key: " + key);
                        System.out.println("check value: " + value);
                    }
                }

                adapter.updateList(trainerList);
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
            Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            DialogAddFragment dialog = DialogAddFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabUpdate.setOnClickListener(v -> {
            DialogUpdateFragment dialog = DialogUpdateFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabDelete.setOnClickListener(v -> {
            DialogDeleteFragment dialog = DialogDeleteFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });
    }
}
