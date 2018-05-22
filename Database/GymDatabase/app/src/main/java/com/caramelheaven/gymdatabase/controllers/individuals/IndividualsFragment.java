package com.caramelheaven.gymdatabase.controllers.individuals;

import android.app.Activity;
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
import com.caramelheaven.gymdatabase.controllers.clients.DialogAddFragment;
import com.caramelheaven.gymdatabase.controllers.clients.DialogDeleteFragment;
import com.caramelheaven.gymdatabase.controllers.clients.DialogUpdateFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividualsFragment extends Fragment {

    private Toolbar toolbar;
    private DatabaseReference firebase;
    private RecyclerView recyclerView;
    private IndividualAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;
    private Menu menu;

    //buttons for admin
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabUpdate;
    private FloatingActionButton fabDelete;

    public static IndividualsFragment newInstance() {
        Bundle args = new Bundle();
        IndividualsFragment fragment = new IndividualsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_individuals, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        toolbar = view.findViewById(R.id.toolbar);
        fabAdd = view.findViewById(R.id.fab);
        fabUpdate = view.findViewById(R.id.fabInsert);
        fabDelete = view.findViewById(R.id.fabDelete);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);

        setActionBar();

        //base lists for reflecting inside UI
        List<HashMap<String, String>> listIndividuals;
        List<HashMap<String, String>> listClients;
        List<HashMap<String, String>> listTrainers;

        listIndividuals = new ArrayList<>();
        listClients = new ArrayList<>();
        listTrainers = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new IndividualAdapter(listIndividuals, listClients, listTrainers);

        swipeRefresh.setColorSchemeResources(R.color.fabInsert);
        swipeRefresh.setOnRefreshListener(() -> {
            adapter.clear();
            setIndividualsFirebase();
            adapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(false);
                }
            }, 2500);
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
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void querySearch(String inputText) {
        inputText = inputText.toLowerCase();
        System.out.println("Зашел в querySearch: " + inputText);
        List<HashMap<String, String>> updatedList = new ArrayList<>();
        List<HashMap<String, String>> hashClients = adapter.getIndividualsList();
        if (hashClients.size() == 0) {
            Toast.makeText(getContext(), "Size is 0", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < hashClients.size(); i++) {
                HashMap<String, String> tempHash = hashClients.get(i);
                if ((tempHash.get("day_of_week") != null && tempHash.get("day_of_week").toLowerCase().contains(inputText))) {
                    updatedList.add(tempHash);
                }
            }

            setIndividualsFirebase();
            adapter.updateFromSearch(updatedList);
        }
    }


    private void setActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void setIndividualsFirebase() {
        firebase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> individualsList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/IndividualSchedule")
                        .getValue();

                ArrayList<HashMap<String, String>> clientsList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/ClientDirectory")
                        .getValue();

                ArrayList<HashMap<String, String>> trainerList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/TrainerDirectory")
                        .getValue();

                individualsList.removeAll(Collections.singleton(null));
                clientsList.removeAll(Collections.singleton(null));
                trainerList.removeAll(Collections.singleton(null));

                for (HashMap<String, String> place : individualsList) {
                    for (Map.Entry<String, String> entry : place.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        System.out.println("check key individual: " + key);
                        System.out.println("check value individual: " + value);
                    }
                }
                adapter.updateList(individualsList, clientsList, trainerList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private static final int REQUEST_WEIGHT = 1;

    private void setFABs() {
        fabAdd.setOnClickListener(v -> {
            DAddFragment dialog = DAddFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabUpdate.setOnClickListener(v -> {
            DUpdateFragment dialog = DUpdateFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabDelete.setOnClickListener(v -> {
            DDeleteFragment dialog = DDeleteFragment.newInstance();
            dialog.setTargetFragment(this, REQUEST_WEIGHT);
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WEIGHT:
                    int id = data.getIntExtra(DialogDeleteFragment.TAG_WEIGHT_SELECTED, -1);
                    ArrayList<HashMap<String, String>> updatedRemovingList = (ArrayList<HashMap<String, String>>) adapter.getIndividualsList();

                    int deletedId = 0;
                    for (int i = 0; i < updatedRemovingList.size(); i++) {
                        if (updatedRemovingList.get(i).get("id_individual_work").equals(String.valueOf(id))) {
                            deletedId = i;
                            Toast.makeText(getContext(), "delete: " + deletedId + " i: " + i, Toast.LENGTH_SHORT).show();
                        }
                    }
                    updatedRemovingList.remove(deletedId);
                    updatedRemovingList.trimToSize();
                    adapter.updateAdapterFromDeleted(updatedRemovingList);

                    DatabaseReference firebaseTemp = FirebaseDatabase
                            .getInstance()
                            .getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/IndividualSchedule");

                    final DatabaseReference child = firebaseTemp.child(String.valueOf(deletedId));
                    child.removeValue();
                    break;
            }
            //updateUI();
        }
    }
}
