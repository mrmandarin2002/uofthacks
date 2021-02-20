package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewTaskActivity extends AppCompatActivity {

    private TextView task_nameTV2;
    private TextView task_locationTV2;
    private TextView task_starttimeTV2;
    private TextView task_endtimeTV2;
    private TextView iconTV2;
    private TextView task_numpeopleTV2;
    private TextView task_statusTV2;
    private TextView message_TV2;

    private EditText nameET2;
    private EditText addressET2;
    private EditText costET2;
    private EditText groceryListET2;

    private Button submitReqBTN2;
    private Button cancelBTN2;

    Task curTask;
    Date startDate;
    Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);



        task_nameTV2 = findViewById(R.id.task_nameTV2);
        task_locationTV2 = findViewById(R.id.task_locationTV2);
        task_starttimeTV2 = findViewById(R.id.task_starttime_TV2);
        task_endtimeTV2 = findViewById(R.id.task_endtime_TV2);
        iconTV2 = findViewById(R.id.iconTV2);
        task_numpeopleTV2 = findViewById(R.id.task_numpeople_TV2);
        task_statusTV2 = findViewById(R.id.statusTV);
        message_TV2 = findViewById(R.id.messageTV2);

        nameET2 = findViewById(R.id.nameET2);
        addressET2 = findViewById(R.id.addressET2);
        costET2 = findViewById(R.id.costET2);
        groceryListET2 = findViewById(R.id.groceryListET2);

        submitReqBTN2 = findViewById(R.id.submitReqBTN2);
        cancelBTN2 = findViewById(R.id.cancelBTN2);

        getData();
        setData();



        // set all TVs to what is passed in

        submitReqBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save fields in a single string and update task
            }
        });

        cancelBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to last activity
            }
        });
    }

    private void getData() {
        if (getIntent().hasExtra("startDate")) {
            Intent intent = getIntent();
            curTask = intent.getParcelableExtra("object");
            // if it crashes, comment lines below
            startDate = new Date();
            startDate.setTime(intent.getLongExtra("startDate", -1));
            endDate = new Date();
            endDate.setTime(intent.getLongExtra("endDate", -1));

        } else {
            Toast.makeText(this, "No data passed", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {

        ArrayList<String> messages = new ArrayList<>();
        String messagesText = "";
        messages = curTask.getRequests();
        if (messages.size() == 0) {
            message_TV2.setVisibility(View.GONE);
            task_statusTV2.setText("Available");
        } else if (messages.size() == curTask.getMax_orders()) {
            for (int i = 0; i < messages.size(); i++) {
                messagesText += messages.get(i) + "\n";
            }
            message_TV2.setText(messagesText);
            task_statusTV2.setText("Full");
        } else {
            for (int i = 0; i < messages.size(); i++) {
                messagesText += messages.get(i) + "\n";
            }
            message_TV2.setText(messagesText);
            task_statusTV2.setText("Available");
        }



        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String nameText = "Name: " + curTask.getName();
        String locationText = "Destination: " + curTask.getDestination();

        String startText = "Start time: " + dateFormat.format(startDate);
        String endText = "End time: " + dateFormat.format(endDate);
        String numpeopleText = "Max Count: " + curTask.getMax_orders();
        String iconText = Character.toString(curTask.getDestination().charAt(0));

        task_nameTV2.setText(nameText);
        task_locationTV2.setText(locationText); // this could throw an error...
        task_starttimeTV2.setText(startText);
        task_endtimeTV2.setText(endText);
        task_numpeopleTV2.setText(numpeopleText);
        iconTV2.setText(iconText);
        iconTV2.setAllCaps(true);
    }

}