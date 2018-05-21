package com.caramelheaven.gymdatabase.controllers.individuals;

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

import com.caramelheaven.gymdatabase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.caramelheaven.gymdatabase.controllers.clients.DialogDeleteFragment.TAG_WEIGHT_SELECTED;

public class DDeleteFragment extends DialogFragment {

    private DatabaseReference firebaseDelete;
    private AppCompatEditText inputId;
    private Button button;

    public static DDeleteFragment newInstance() {
        Bundle args = new Bundle();
        DDeleteFragment fragment = new DDeleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        return inflater.inflate(R.layout.fragment_individual_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inputId = view.findViewById(R.id.etId);
        button = view.findViewById(R.id.buttonOk);

        firebaseDelete = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gymdatabase-63161.firebaseio.com/IndividualSchedule");

        button.setOnClickListener(v -> {
            final String curId = inputId.getText().toString();
            int temp = Integer.parseInt(curId);
            Intent intent = new Intent();
            intent.putExtra(TAG_WEIGHT_SELECTED, temp);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firebaseDelete.onDisconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(500, 500);
        window.setGravity(Gravity.CENTER);
    }
}
