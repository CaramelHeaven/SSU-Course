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
import android.widget.LinearLayout;

import com.caramelheaven.gymdatabase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogDeleteFragment extends DialogFragment {

    private DatabaseReference firebaseClient;
    private DatabaseReference firebaseGym;
    private AppCompatEditText inputId;
    private AppCompatEditText inputName;
    private AppCompatEditText inputNameL;
    private Button button;

    public static DialogDeleteFragment newInstance() {
        Bundle args = new Bundle();

        DialogDeleteFragment fragment = new DialogDeleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_client_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputId = view.findViewById(R.id.etId);
        inputName = view.findViewById(R.id.etName);
        inputNameL = view.findViewById(R.id.etLastName);
        button = view.findViewById(R.id.buttonOk);

        firebaseClient = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientDirectory");
        firebaseGym = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GymMembership");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String lastName = inputNameL.getText().toString();
                final String keyClient = inputId.getText().toString();
                //take our child from array [0...120] and changed his fields
                final DatabaseReference child = firebaseClient.child(keyClient);

                //we need to get gym_membership_id from our child for delete his gym membership
                firebaseClient.removeEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<HashMap<String, String>> listValues = (ArrayList<HashMap<String, String>>) dataSnapshot
                                .getValue();
                        for (HashMap<String, String> temp : listValues){
                            System.out.println("id client: " + temp.get("id_client"));
                            if (temp.get("id_client").equals(keyClient)){
                                String f = temp.get("gym_membership_id");
                                System.out.println("ffff: " + f);
                                DatabaseReference childGym = firebaseGym.child(f);
                                childGym.removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

              /*  firebaseGym.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("parce: " + Integer.parseInt(gymId[0]));
                        List<HashMap<String, String>> listValues = (ArrayList<HashMap<String, String>>) dataSnapshot
                                .getValue();

                        for (HashMap<String, String> temp : listValues){
                            System.out.println("containes: " + temp.get("id_gym_membership"));
                            if (gymId[0].equals(temp.get("id_gym_membership"))){
                                System.out.println("ура, содержит!");
                                DatabaseReference ref = firebaseGym.child()
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });*/


                child.removeValue();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firebaseClient.onDisconnect();
    }


    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(500, 500);
        window.setGravity(Gravity.CENTER);
    }
}
