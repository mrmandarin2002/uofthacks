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

        interactions cur_interactions = null;
        try {
            cur_interactions = new interactions();
        } catch (IOException e) {
            e.printStackTrace();
        }

        usernameLogIn_et = findViewById(R.id.usernameLogIn_et);
        LogInPassword_et = findViewById(R.id.LogInPassword_et);
        Signin_bt = findViewById(R.id.Signin_bt);
        SignUp_BT = findViewById(R.id.SignUp_BT);

        interactions finalCur_interactions = cur_interactions;

        Signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    finalCur_interactions.check_user(usernameLogIn_et.getText().toString(),
//                            LogInPassword_et.getText().toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

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