package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    ArrayList<Task> mTasks;

    // want to populate this from the database

    private FloatingActionButton AddTask_flabt;
    private FloatingActionButton refreshFAB;
    private TextView dashboardTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboardTitleTV = findViewById(R.id.dashboardTitleTV);
        String cur_text = dashboardTitleTV.getText().toString();
        cur_text += " | " + ServerSingleton.get().getmCommunityCode();
        dashboardTitleTV.setText(cur_text);

        /*
        // should be pulling data from server as mTasks, need to thread this
        interactions cur_interaction = ServerSingleton.get().getMinteracations();
        try {
            ServerSingleton.get().setmTasks( (ArrayList<Task>) cur_interaction.
                    pull_events(ServerSingleton.get().getmCommunityCode()));
            mTasks = ServerSingleton.get().getmTasks();// need to change return type in interactions
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }



         */

        content();

        Log.d(TAG, "onCreate: started");

        mTasks = ServerSingleton.get().getmTasks();
        // do not normally make changes here

        if (getIntent().hasExtra("newMessage")) {
            String newMessage = getIntent().getStringExtra("newMessage");
            String name = getIntent().getStringExtra("name");
            String destination = getIntent().getStringExtra("location");
            for (int i = 0; i < mTasks.size(); i++) {
                if (mTasks.get(i).getName().equals(name) && mTasks.get(i).getDestination().equals(destination)) {
                    ArrayList<String> updatedRequests = mTasks.get(i).getRequests();
                    updatedRequests.add(newMessage);
                    mTasks.get(i).setRequests(updatedRequests);
                    ServerSingleton.get().setmTasks(mTasks);
                    try {
                        ServerSingleton.get().getMinteracations().push_events(ServerSingleton.get().getmCommunityCode(),
                                ServerSingleton.get().getmTasks());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // then need to update to server
                }
            }
        }

        //createRecyclerView();

        AddTask_flabt = findViewById(R.id.AddTask_flabt);
        AddTask_flabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        refreshFAB = findViewById(R.id.refreshFAB);
        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content();
            }
        });

    }

    public void content(){

        interactions cur_interaction = ServerSingleton.get().getMinteracations();
        try {
            ServerSingleton.get().setmTasks( (ArrayList<Task>) cur_interaction.
                    pull_events(ServerSingleton.get().getmCommunityCode()));
            mTasks = ServerSingleton.get().getmTasks();// need to change return type in interactions
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        createRecyclerView();
        Toast.makeText(DashboardActivity.this, "REFRESHED", Toast.LENGTH_SHORT).show();
    }
    

    private void createRecyclerView(){
        Log.d(TAG, "CreateRecyclerView");
        RecyclerView taskRV = findViewById(R.id.taskRV);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mTasks, this);
        taskRV.setAdapter(adapter);
        taskRV.setLayoutManager(new LinearLayoutManager(this));
    }

}