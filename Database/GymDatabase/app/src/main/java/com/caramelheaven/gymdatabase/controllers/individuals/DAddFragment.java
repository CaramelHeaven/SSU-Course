package com.caramelheaven.gymdatabase.controllers.individuals;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.datasourse.model.GroupSchedule;
import com.caramelheaven.gymdatabase.datasourse.model.IndividualSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DAddFragment extends DialogFragment {

    private AppCompatEditText inputTrainer;
    private AppCompatEditText inputTimeStart;
    private AppCompatEditText inputTimeEnd;
    private AppCompatEditText inputDayOfWeek;
    private AppCompatEditText inputClient;

    private Button button;

    private DatabaseReference firebaseAdd;

    private int COUNT = 0;

    public static DAddFragment newInstance() {
        Bundle args = new Bundle();
        DAddFragment fragment = new DAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_individuals_add, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1000, 800);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputClient = view.findViewById(R.id.etIdClient);
        inputTimeStart = view.findViewById(R.id.etTimeStart);
        inputTimeEnd = view.findViewById(R.id.etTimeEnd);
        inputTrainer = view.findViewById(R.id.etTrainer);
        inputDayOfWeek = view.findViewById(R.id.etDayWeek);

        button = view.findViewById(R.id.buttonOk);

        //JUST we need to know size clients
        DatabaseReference temp = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/IndividualSchedule");

        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .getValue();
                COUNT = list.size();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        firebaseAdd = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/IndividualSchedule");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String client = inputClient.getText().toString();
                String timestar = inputTimeStart.getText().toString();
                String timeEnd = inputTimeEnd.getText().toString();
                String trainer = inputTrainer.getText().toString();
                String day = inputDayOfWeek.getText().toString();

                if ((client.equals("") || (timestar.equals("") || (trainer.equals("") || (timeEnd.equals("") || (day.equals(""))))))) {
                    Toast.makeText(getContext(), "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
                } else {
                    IndividualSchedule individualSchedule = new IndividualSchedule(client, String.valueOf(COUNT), timeEnd, timestar, trainer, day);

                    DatabaseReference groupRef = firebaseAdd.child(String.valueOf(COUNT));
                    groupRef.setValue(individualSchedule);
                    Toast.makeText(getContext(), "Индивидуальное занятие добавлено", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
