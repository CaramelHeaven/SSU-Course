package com.caramelheaven.gymdatabase.controllers.clients;

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
import com.caramelheaven.gymdatabase.controllers.MainActivity;
import com.caramelheaven.gymdatabase.datasourse.model.ClientDirectory;
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
import java.util.Timer;
import java.util.TimerTask;

public class ClientsFragment extends Fragment {

    private Toolbar toolbar;
    private DatabaseReference firebase;
    private RecyclerView recyclerView;
    private ClientsAdapter adapter;
    private List<HashMap<String, String>> listClients;
    private List<HashMap<String, String>> listGymMemberships;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabUpdate;
    private FloatingActionButton fabDelete;
    private SwipeRefreshLayout swipeRefresh;
    private Menu menu;

    private static final int REQUEST_WEIGHT = 1;
    private static final int REQUEST_ANOTHER_ONE = 2;

    public static ClientsFragment newInstance() {
        Bundle args = new Bundle();
        ClientsFragment fragment = new ClientsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_clients, container, false);
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

        listClients = new ArrayList<>();
        listGymMemberships = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new ClientsAdapter(listClients, listGymMemberships);

        //setClientFirebase();

        swipeRefresh.setColorSchemeResources(R.color.fabInsert);
        swipeRefresh.setOnRefreshListener(() -> {
            adapter.clear();
            setClientFirebase();
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

        setClientFirebase();
        adapter.notifyDataSetChanged();

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
        List<HashMap<String, String>> hashClients = adapter.getClientsFromAdapter();
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

    private void setActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void setClientFirebase() {
        firebase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> listClients = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/ClientDirectory")
                        .getValue();

                ArrayList<HashMap<String, String>> listGyms = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .child("/GymMembership")
                        .getValue();

                listClients.removeAll(Collections.singleton(null));
                listGyms.removeAll(Collections.singleton(null));

                for (int i = 0; i < listClients.size(); i++) {
                    System.out.println("i: = " + i);
                    for (Map.Entry<String, String> temp : listClients.get(i).entrySet()) {
                        System.out.println("key: " + temp.getKey());
                        System.out.println("value: " + temp.getValue());
                    }
                }
                adapter.updateList(listClients, listGyms);
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
            dialog.show(getActivity().getSupportFragmentManager(), dialog.getClass().getName());
        });

        fabUpdate.setOnClickListener(v -> {
            DialogUpdateFragment dialog = DialogUpdateFragment.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(), null);
        });

        fabDelete.setOnClickListener(v -> {
            DialogDeleteFragment dialog = DialogDeleteFragment.newInstance();
            dialog.setTargetFragment(this, REQUEST_WEIGHT);
            dialog.show(getActivity().getSupportFragmentManager(), dialog.getClass().getName());
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WEIGHT:
                    int id = data.getIntExtra(DialogDeleteFragment.TAG_WEIGHT_SELECTED, -1);
                    Toast.makeText(getContext(), "we: " + id, Toast.LENGTH_SHORT).show();
                    System.out.println("result: " + String.valueOf(id));
                    ArrayList<HashMap<String, String>> updatedRemovingList = (ArrayList<HashMap<String, String>>) adapter.getClientsFromAdapter();

                    int deletedId = 0;
                    for (int i = 0; i < updatedRemovingList.size(); i++) {
                        if (updatedRemovingList.get(i).get("id_client").equals(String.valueOf(id))) {
                            deletedId = i;
                        }
                    }
                    updatedRemovingList.remove(deletedId);
                    updatedRemovingList.trimToSize();
                    adapter.updateAdapterFromDeleted(updatedRemovingList);

                    DatabaseReference firebaseClient = FirebaseDatabase
                            .getInstance()
                            .getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientDirectory");

                    final DatabaseReference child = firebaseClient.child(String.valueOf(id));
                    child.removeValue();
                    break;
            }
            //updateUI();
        }
    }
}
