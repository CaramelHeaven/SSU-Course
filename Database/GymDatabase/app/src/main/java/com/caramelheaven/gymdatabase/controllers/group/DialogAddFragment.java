package com.caramelheaven.gymdatabase.controllers.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogAddFragment extends DialogFragment {

    private DatabaseReference firebaseUpdate;

    private AppCompatEditText inputTrainer;
    private AppCompatEditText inputTimeStart;
    private AppCompatEditText inputTimeEnd;
    private AppCompatEditText inputGroup;
    private AppCompatEditText inputDayOfWeek;
    private AppCompatEditText inputTypeOfSport;
    private AppCompatEditText inputPlace;

    private DatabaseReference firebaseGroup;

    private Button button;

    private int COUNT = 0;

    public static DialogAddFragment newInstance() {
        Bundle args = new Bundle();
        DialogAddFragment fragment = new DialogAddFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_group_add, container, false);
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
        inputDayOfWeek = view.findViewById(R.id.etDayWeek);
        inputGroup = view.findViewById(R.id.etGroup);
        inputTimeStart = view.findViewById(R.id.etDayStart);
        inputTimeEnd = view.findViewById(R.id.etDayEnd);
        inputTypeOfSport = view.findViewById(R.id.etTypeOfSport);
        inputTrainer = view.findViewById(R.id.etTrainer);
        inputPlace = view.findViewById(R.id.etPlace);

        button = view.findViewById(R.id.buttonOk);

        //JUST we need to know size clients
        DatabaseReference temp = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GroupSchedule");
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> groupSchedule = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .getValue();
                COUNT = groupSchedule.size();
                //потому что у нас почему-то id нумерация начинается с 1, а не с 0. fuck. nice костыль
                COUNT++;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseGroup = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GroupSchedule");

        button.setOnClickListener(v -> {
            String dayOfWeek = inputDayOfWeek.getText().toString();
            String group = inputGroup.getText().toString();
            String timeStart = inputTimeStart.getText().toString();
            String timeEnd = inputTimeEnd.getText().toString();
            String typeOfSport = inputTypeOfSport.getText().toString();
            String trainer = inputTrainer.getText().toString();
            String place = inputPlace.getText().toString();

            if ((dayOfWeek.equals("") || (group.equals("") || (timeStart.equals("") || (timeEnd.equals("") || (typeOfSport.equals("") || (trainer.equals("") || (place.equals(""))))))))) {
                Toast.makeText(getContext(), "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
            } else {
                GroupSchedule groupSchedule = new GroupSchedule(dayOfWeek, group, String.valueOf(COUNT),
                        place, timeEnd, timeStart, trainer, typeOfSport);

                DatabaseReference groupRef = firebaseGroup.child(String.valueOf(COUNT));
                groupRef.setValue(groupSchedule);
                Toast.makeText(getContext(), "Групповое занятие добавлено", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
