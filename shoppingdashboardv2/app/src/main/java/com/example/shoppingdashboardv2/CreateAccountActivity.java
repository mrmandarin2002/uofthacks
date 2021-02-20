package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity {

    private Button createActBTN;
    private TextView retLoginTV;
    private EditText emailET;
    private EditText passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createActBTN = findViewById(R.id.createActBTN);
        retLoginTV = findViewById(R.id.retLoginTV);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
    }
}