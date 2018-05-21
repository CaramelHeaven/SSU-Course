package com.caramelheaven.gymdatabase.controllers.group.more;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.datasourse.model.GroupWork;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GroupInformationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String currentGroupId;
    InformationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_information);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

        List<HashMap<String, String>> example = new ArrayList<>();
        List<HashMap<String, String>> example2 = new ArrayList<>();
        List<HashMap<String, String>> example3 = new ArrayList<>();
        adapter = new InformationAdapter(example, example2, example3);

        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        currentGroupId = intent.getStringExtra("KEY");

        DatabaseReference firebase = FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GroupSchedule");

        DatabaseReference firebaseGroupWork = FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GroupWork");



        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> grouplist = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .getValue();

                grouplist.removeAll(Collections.singleton(null));

                //это выполняется один раз, нет смысла беспокоится о большой занимаемой памяти
                //которая и так уже большая.
                for (HashMap<String, String> temp : grouplist) {
                    if (temp.get("id_group_schedule").equals(currentGroupId)) {

                        String currentIdWork = temp.get("group_id");

                        firebaseGroupWork.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayList<HashMap<String, String>> groupWorkList = (ArrayList<HashMap<String, String>>) dataSnapshot
                                        .getValue();

                                groupWorkList.removeAll(Collections.singleton(null));
                                final List<HashMap<String, String>> futureGroupWork = new ArrayList<>();
                                final List<HashMap<String, String>> futureClient = new ArrayList<>();
                                List<String> idClients = new ArrayList<>();

                                //мы получили текущий айди группы, из GroupSchedule и теперь
                                //в GroupWork пробегаемся по всем и чекаем, есть ли у них этот
                                // айди, если да - то заносим в список
                                for (HashMap<String, String> temp2 : groupWorkList) {
                                    if (temp2.get("id_group").equals(currentIdWork)) {
                                        futureGroupWork.add(temp2);
                                        idClients.add("get_people");
                                    }
                                }
                                //тупо берем весь список юзеров и кидаем в адаптер, там отберем нормальных
                                DatabaseReference firebaseClient = FirebaseDatabase
                                        .getInstance()
                                        .getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientDirectory");
                                //тупо еще раз берем и нужно чекнуть эту штуку, т.к. в ней занесены
                                //старт занятий года и конец года занятий.
                                DatabaseReference firebaseClientTrace = FirebaseDatabase
                                        .getInstance()
                                        .getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientTrace");

                                firebaseClient.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final ArrayList<HashMap<String, String>> temp = (ArrayList<HashMap<String, String>>) dataSnapshot
                                                .getValue();
                                        temp.removeAll(Collections.singleton(null));
                                        firebaseClientTrace.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                final ArrayList<HashMap<String, String>> tempClientTrace = (ArrayList<HashMap<String, String>>) dataSnapshot
                                                        .getValue();

                                                tempClientTrace.removeAll(Collections.singleton(null));

                                                //and updated it :)
                                                adapter.updateList(futureGroupWork, temp, tempClientTrace);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        Toast.makeText(GroupInformationActivity.this, "cur: " + currentIdWork, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
