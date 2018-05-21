package com.caramelheaven.gymdatabase.controllers.trainers;

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
import com.caramelheaven.gymdatabase.datasourse.model.IndividualSchedule;
import com.caramelheaven.gymdatabase.datasourse.model.TrainerDirectory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TAddFragment extends DialogFragment {

    private AppCompatEditText inputName;
    private AppCompatEditText inputLastName;
    private AppCompatEditText inputSalary;

    private Button button;

    private DatabaseReference firebaseAdd;

    private int COUNT = 0;

    public static TAddFragment newInstance() {
        Bundle args = new Bundle();
        TAddFragment fragment = new TAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_trainers_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputLastName = view.findViewById(R.id.etLastName);
        inputName = view.findViewById(R.id.etFirstName);
        inputSalary = view.findViewById(R.id.etSalary);

        button = view.findViewById(R.id.buttonOk);

        //JUST we need to know size clients
        DatabaseReference temp = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/TrainerDirectory");

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

        firebaseAdd = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/TrainerDirectory");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String lastName = inputLastName.getText().toString();
                String salary = inputSalary.getText().toString();

                if ((name.equals("") || (lastName.equals("") || (salary.equals(""))))) {
                    Toast.makeText(getContext(), "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
                } else {
                    TrainerDirectory trainer = new TrainerDirectory(name, String.valueOf(COUNT), lastName, salary);

                    DatabaseReference groupRef = firebaseAdd.child(String.valueOf(COUNT));
                    groupRef.setValue(trainer);
                    Toast.makeText(getContext(), "Новый тренер добавлен", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1000, 500);
        window.setGravity(Gravity.CENTER);
    }

}
