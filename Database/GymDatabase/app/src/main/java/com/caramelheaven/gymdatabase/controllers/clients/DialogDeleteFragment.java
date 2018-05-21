package com.caramelheaven.gymdatabase.controllers.clients;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caramelheaven.gymdatabase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogDeleteFragment extends DialogFragment  {

    private DatabaseReference firebaseClient;
    private AppCompatEditText inputId;
    private AppCompatEditText inputName;
    private AppCompatEditText inputNameL;
    private Button button;

    public static final String TAG_WEIGHT_SELECTED = "weight";

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

        button.setOnClickListener(v -> {
            final String idClient = inputId.getText().toString();
            int temp = Integer.parseInt(idClient);
            Intent intent = new Intent();
            intent.putExtra(TAG_WEIGHT_SELECTED, temp);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
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
