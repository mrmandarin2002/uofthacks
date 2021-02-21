package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // should be pulling data from server as mTasks, need to thread this
        interactions cur_interaction = ServerSingleton.get().getMinteracations();

        try {
            ServerSingleton.get().setmTasks( (ArrayList<Task>) cur_interaction.
                    pull_events(ServerSingleton.get().getmCommunityCode()));
            mTasks = ServerSingleton.get().getmTasks();// need to change return type in interactions
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onCreate: started");

        // populate ArrayList here
        Date date_cur = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
        ArrayList<String> nonEmptyArray = new ArrayList<>();
        nonEmptyArray.add("LOOOK AT ME");
        nonEmptyArray.add("THERE'S TWO OF US");

        // shouldn't need this section after proper server integration
        Task task0 = new Task("Bob", "Loblaws", date_cur, date_cur, 3, nonEmptyArray);
        ServerSingleton.get().addmTasks(task0);

        Task task1 = new Task("John Cena", "Longos", date_cur, date_cur, 21, nonEmptyArray);
        ServerSingleton.get().addmTasks(task1);
        mTasks = ServerSingleton.get().getmTasks();
        // do not normally make changes here

        if (getIntent().hasExtra("newMessage")) {
            String newMessage = getIntent().getStringExtra("newMessage");
            String name = getIntent().getStringExtra("name");
            for (int i = 0; i < mTasks.size(); i++) {
                if (mTasks.get(i).getName().equals(name)) {
                    ArrayList<String> updatedRequests = mTasks.get(i).getRequests();
                    updatedRequests.add(newMessage);
                    mTasks.get(i).setRequests(updatedRequests);
                    ServerSingleton.get().setmTasks(mTasks);
                    try {
                        cur_interaction.push_events(ServerSingleton.get().getmCommunityCode(),
                                ServerSingleton.get().getmTasks());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // then need to update to server
                }
            }
        }

        createRecyclerView();

        AddTask_flabt = findViewById(R.id.AddTask_flabt);
        AddTask_flabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createRecyclerView(){
        Log.d(TAG, "CreateRecyclerView");
        RecyclerView taskRV = findViewById(R.id.taskRV);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mTasks, this);
        taskRV.setAdapter(adapter);
        taskRV.setLayoutManager(new LinearLayoutManager(this));
    }

}