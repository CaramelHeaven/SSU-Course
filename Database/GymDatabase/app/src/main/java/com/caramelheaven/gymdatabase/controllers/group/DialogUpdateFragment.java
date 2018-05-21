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
import com.caramelheaven.gymdatabase.controllers.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DialogUpdateFragment extends DialogFragment {

    private DatabaseReference firebaseUpdate;
    private AppCompatEditText inputId;
    private AppCompatEditText inputTimeStart;
    private AppCompatEditText inputTimeEnd;
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
        return inflater.inflate(R.layout.fragment_group_update, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputId = view.findViewById(R.id.etId);
        inputTimeEnd = view.findViewById(R.id.etTimeEnd);
        inputTimeStart = view.findViewById(R.id.etTimeStart);

        button = view.findViewById(R.id.buttonOk);

        firebaseUpdate = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/GroupSchedule");

        button.setOnClickListener(v -> {
            String id = inputId.getText().toString();
            String timeEnd = inputTimeEnd.getText().toString();
            String timeStart = inputTimeStart.getText().toString();

            DatabaseReference child = firebaseUpdate.child(id);

            Map<String, Object> map = new HashMap<>();
            map.put("time_end", timeEnd);
            map.put("time_start", timeStart);

            child.updateChildren(map);

            Toast.makeText(getContext(), "Групповое расписание обновлено", Toast.LENGTH_SHORT).show();
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
