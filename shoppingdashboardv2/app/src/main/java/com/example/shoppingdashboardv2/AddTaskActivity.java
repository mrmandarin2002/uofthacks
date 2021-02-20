package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddTaskActivity extends AppCompatActivity {

    Button submitButton;
    Button cancel_bt;
    EditText nameField;
    EditText destField;
    EditText dateTimeStartField;
    EditText dateTimeReturnField;
    EditText maxOrdersField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        nameField = (EditText)findViewById(R.id.name_et);
        destField = (EditText)findViewById(R.id.destination_et);
        dateTimeReturnField = (EditText)findViewById(R.id.startDateTime_et);//this part gets the start date and time from the text field
        dateTimeReturnField = (EditText)findViewById(R.id.returnDateTime_et); // this part gets the return date and time from the text field

        maxOrdersField   = (EditText)findViewById(R.id.maxOrders_et);

        submitButton = (Button) findViewById(R.id.createTask_bt);
        cancel_bt = findViewById(R.id.cancel_bt);

        submitButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                        String name = nameField.getText().toString();
                        String dest = destField.getText().toString();
                        String sDate = dateTimeStartField.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date startDate = new Date();
                        try {
                            startDate = sdf.parse(sDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String rDate = dateTimeReturnField.getText().toString();
                        Date returnDate = new Date();
                        try {
                            returnDate = sdf.parse(rDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String max_orders = maxOrdersField.getText().toString();
                        int maximum_orders = Integer.parseInt(max_orders);
                        ArrayList<String> requests = new ArrayList<String>();;

                        Task addedTask = new Task(name, dest, startDate, returnDate, maximum_orders, requests);

                        Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                        startActivity(intent);

                        //Log.v("EditText", mEdit.getText().toString());

                    }
                });

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}