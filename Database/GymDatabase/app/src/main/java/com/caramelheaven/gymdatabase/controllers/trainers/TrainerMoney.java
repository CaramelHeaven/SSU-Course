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
import android.widget.TextView;

import com.caramelheaven.gymdatabase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TrainerMoney extends DialogFragment {

    private DatabaseReference firebaseMoney;
    private TextView showMoney;
    private TextView showTrainersQuantity;

    public static TrainerMoney newInstance() {
        Bundle args = new Bundle();
        TrainerMoney fragment = new TrainerMoney();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_trainer_money, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        showMoney = view.findViewById(R.id.quantityMoney);
        showTrainersQuantity = view.findViewById(R.id.quantityTrainers);

        firebaseMoney = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/TrainerDirectory");
        firebaseMoney.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> listValues = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .getValue();

                listValues.removeAll(Collections.singleton(null));

                int sum = 0;
                for (HashMap<String, String> temp : listValues) {
                    String k = temp.get("salary");
                    System.out.println("salary: " + k);
                    sum = sum + Integer.parseInt(k);
                    System.out.println("sum : " + sum);
                }

                double middleMoney = (double) sum / (double) (listValues.size());

                DecimalFormat df = new DecimalFormat("#.##");
                String dx = df.format(middleMoney);

                showTrainersQuantity.setText(String.valueOf(listValues.size()) + " человек/a");
                showMoney.setText(dx + " rub.");
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(500, 500);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firebaseMoney.onDisconnect();
    }
}
