package com.example.shoppingdashboardv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";

    private ArrayList<Task> mTasks = new ArrayList<>(); // want to populate this from the database

    private FloatingActionButton AddTask_flabt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.d(TAG, "onCreate: started");

        // populate ArrayList here
        Date date_cur = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));

        Task task0 = new Task("Bob", "Loblaws", date_cur, date_cur, 5);
        mTasks.add(task0);

        Task task1 = new Task("John Cena", "Longos", date_cur, date_cur, 21);
        mTasks.add(task1);
        createRecyclerView();

        AddTask_flabt = findViewById(R.id.AddTask_flabt);
        AddTask_flabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes you to Add Task Fragment
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