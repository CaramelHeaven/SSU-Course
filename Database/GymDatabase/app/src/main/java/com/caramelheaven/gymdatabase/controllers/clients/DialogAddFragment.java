package com.caramelheaven.gymdatabase.controllers.clients;

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
import com.caramelheaven.gymdatabase.datasourse.model.ClientDirectory;
import com.caramelheaven.gymdatabase.datasourse.model.GymMembership;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogAddFragment extends DialogFragment {

    private AppCompatEditText inputName;
    private AppCompatEditText inputNameL;
    private AppCompatEditText inputStart;
    private AppCompatEditText inputEnd;
    private AppCompatEditText inputPrice;
    private AppCompatEditText inputSale;
    private int COUNT_CLIENTS = 0;

    private DatabaseReference firebaseClient;
    private DatabaseReference firebaseGym;

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
        return inflater.inflate(R.layout.fragment_client_add, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1000, 700);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button button = view.findViewById(R.id.buttonOk);
        inputName = view.findViewById(R.id.etName);
        inputNameL = view.findViewById(R.id.etLastName);
        inputStart = view.findViewById(R.id.etDayStart);
        inputEnd = view.findViewById(R.id.etDayEnd);
        inputPrice = view.findViewById(R.id.etPrice);
        inputSale = view.findViewById(R.id.etSale);

        //JUST we need to know size clients
        DatabaseReference temp = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientDirectory");
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> listClients = (ArrayList<HashMap<String, String>>) dataSnapshot
                        .getValue();
                COUNT_CLIENTS = listClients.size();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseClient = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientDirectory");
        firebaseGym = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GymMembership");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String lastName = inputNameL.getText().toString();
                String dayStart = inputStart.getText().toString();
                String dayEnd = inputEnd.getText().toString();
                String price = inputPrice.getText().toString();
                String sale = inputSale.getText().toString();

                GymMembership gym = new GymMembership(dayEnd, dayStart, String.valueOf(COUNT_CLIENTS), price, sale);
                ClientDirectory client = new ClientDirectory(name, String.valueOf(COUNT_CLIENTS), String.valueOf(COUNT_CLIENTS), lastName);

                DatabaseReference clientRef = firebaseClient.child(String.valueOf(COUNT_CLIENTS));
                DatabaseReference gymRef = firebaseGym.child(String.valueOf(COUNT_CLIENTS));

                gymRef.setValue(gym);
                clientRef.setValue(client);

                Toast.makeText(getContext(), "Added: " + COUNT_CLIENTS, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
