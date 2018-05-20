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

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.datasourse.model.ClientDirectory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DialogUpdateFragment extends DialogFragment {

    private DatabaseReference firebaseUpdate;
    private AppCompatEditText inputId;
    private AppCompatEditText inputName;
    private AppCompatEditText inputNameL;
    private Button button;

    public static DialogUpdateFragment newInstance() {
        Bundle args = new Bundle();
        DialogUpdateFragment fragment = new DialogUpdateFragment();
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

        firebaseUpdate = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/ClientDirectory");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String lastName = inputNameL.getText().toString();
                String key = inputId.getText().toString();

                //take our child from array [0...120] and changed his fields
                DatabaseReference child = firebaseUpdate.child(key);

                Map<String, Object> map = new HashMap<>();
                map.put("first_name", name);
                map.put("last_name", lastName);

                child.updateChildren(map);
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
        firebaseUpdate.onDisconnect();
    }


}
