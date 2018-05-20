package com.caramelheaven.gymdatabase.controllers.individuals;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividualsFragment extends Fragment {

    private Toolbar toolbar;
    private DatabaseReference firebase;
    private RecyclerView recyclerView;
    private IndividualAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

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

        if (login.equals("admin")){
            setFABs();
        } else {
            fabAdd.setVisibility(View.GONE);
            fabDelete.setVisibility(View.GONE);
            fabUpdate.setVisibility(View.GONE);
        }

        recyclerView.setAdapter(adapter);
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
                List<HashMap<String, String>> individualsList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/IndividualSchedule")
                        .getValue();

                List<HashMap<String, String>> clientsList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/ClientDirectory")
                        .getValue();

                List<HashMap<String, String>> trainerList = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/TrainerDirectory")
                        .getValue();

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
