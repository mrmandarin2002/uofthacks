package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

EditText usernameLogIn_et;
EditText LogInPassword_et;
Button Signin_bt;
Button SignUp_BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameLogIn_et = findViewById(R.id.usernameLogIn_et);
        LogInPassword_et = findViewById(R.id.LogInPassword_et);
        Signin_bt = findViewById(R.id.Signin_bt);
        SignUp_BT = findViewById(R.id.SignUp_BT);


        Signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = usernameLogIn_et.getText().toString();
                try {
                    ServerSingleton.get().getMinteracations().check_user(newUsername,
                            LogInPassword_et.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ServerSingleton.get().setmUsername(newUsername);

                Intent intent = new Intent(v.getContext(), DashboardActivity.class);
                startActivity(intent);

            }
        });

        SignUp_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        //

    }
}