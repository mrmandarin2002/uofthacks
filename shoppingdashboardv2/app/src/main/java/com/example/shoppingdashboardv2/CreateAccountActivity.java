package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class CreateAccountActivity extends AppCompatActivity {

    private Button createActBTN;
    private TextView retLoginTV;
    private EditText usernameET;
    private EditText passwordET;
    private EditText commCodeET;
    private EditText commNameET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createActBTN = findViewById(R.id.createActBTN);
        retLoginTV = findViewById(R.id.retLoginTV);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        commCodeET = findViewById(R.id.codeET);
        commNameET = findViewById(R.id.commNameET);

        createActBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = usernameET.getText().toString();
                interactions cur_interactions = ServerSingleton.get().getMinteracations();
                int check = 0;
                try {
                    check = cur_interactions.sign_up(newUsername,
                            passwordET.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(check == 1) {
                    ServerSingleton.get().setmUsername(newUsername);

                    String code = commCodeET.getText().toString();
                    String commName = commNameET.getText().toString();

                    if (code.length() == 0) {
                        try {
                            String newCommCode = cur_interactions.create_community(newUsername, commName);
                            ServerSingleton.get().setmCommunityCode(newCommCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (commName.length() == 0) {
                        try {
                            cur_interactions.join_community(newUsername, code);
                            ServerSingleton.get().setmCommunityCode(cur_interactions.get_community(newUsername).get(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    Intent intent = new Intent(v.getContext(), DashboardActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(v.getContext(), "Your username is taken bro", Toast.LENGTH_SHORT).show();
                }
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