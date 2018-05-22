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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DUpdateFragment extends DialogFragment {

    private DatabaseReference firebaseUpdate;
    private AppCompatEditText inputId;
    private AppCompatEditText inputDay;
    private Button button;


    public static DUpdateFragment newInstance() {
        Bundle args = new Bundle();
        DUpdateFragment fragment = new DUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_individuals_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputId = view.findViewById(R.id.etId);
        inputDay = view.findViewById(R.id.etDayWeek);
        button = view.findViewById(R.id.buttonOk);

        firebaseUpdate = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/IndividualSchedule");

        button.setOnClickListener(v -> {
            String id = inputId.getText().toString();
            String day = inputDay.getText().toString();

            firebaseUpdate.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<HashMap<String, String>> listValues = (ArrayList<HashMap<String, String>>) dataSnapshot
                            .getValue();
                    listValues.removeAll(Collections.singleton(null));
                    for (int i = 0; i < listValues.size(); i++) {
                        if (listValues.get(i).get("id_individual_work").equals(id)) {
                            DatabaseReference child = firebaseUpdate.child(String.valueOf(i));
                            Map<String, Object> map = new HashMap<>();
                            map.put("day_of_week", day);
                            child.updateChildren(map);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            Toast.makeText(getContext(), "Индивидуальное расписание обновлено", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(500, 300);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firebaseUpdate.onDisconnect();
    }
}
