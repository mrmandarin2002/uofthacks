package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class CreateAccountActivity extends AppCompatActivity {

    private Button createActBTN;
    private TextView retLoginTV;
    private EditText usernameET;
    private EditText passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createActBTN = findViewById(R.id.createActBTN);
        retLoginTV = findViewById(R.id.retLoginTV);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);

        createActBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = usernameET.getText().toString();
                try {
                    ServerSingleton.get().getMinteracations().sign_up(newUsername,
                            passwordET.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ServerSingleton.get().setmUsername(newUsername);

                Intent intent = new Intent(v.getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        retLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}