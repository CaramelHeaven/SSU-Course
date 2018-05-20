package com.caramelheaven.gymdatabase.controllers.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.caramelheaven.gymdatabase.R;
import com.caramelheaven.gymdatabase.controllers.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AutoCompleteTextView login;
    private EditText password;
    private Button confirmBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        confirmBtn = findViewById(R.id.confirm_button);

        confirmBtn.setOnClickListener(v -> {
            String loginString = login.getText().toString();
            String passwordString = password.getText().toString();
            if (loginString.equals("") && passwordString.equals("")) {
                Toast.makeText(this, "Fields is empty!", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = getSharedPreferences("GYM", MODE_PRIVATE).edit();
                editor.putString("login", loginString);
                editor.putString("password", passwordString);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("login", loginString);
                intent.putExtra("password", passwordString);

                startActivity(intent);
            }
        });
    }
}

