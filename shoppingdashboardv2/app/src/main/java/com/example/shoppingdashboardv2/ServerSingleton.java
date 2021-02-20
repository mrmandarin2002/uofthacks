package com.example.shoppingdashboardv2;

import java.io.IOException;
import java.util.ArrayList;

public class ServerSingleton {
    private String mUsername;
    private String mCommunityCode;
    private ArrayList<Task> mTasks;
    private interactions minteracations;
    private ServerSingleton() {
        mUsername = null;
        mCommunityCode = null;
        minteracations = null;
        try {
            minteracations = new interactions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interactions getMinteracations() {
        return minteracations;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String newUsername) {
        this.mUsername = newUsername;
    }

    public String getmCommunityCode() {
        return mCommunityCode;
    }

    public void setmCommunityCode(String newCommunityCode) {
        this.mCommunityCode = newCommunityCode;
    }

    public ArrayList<Task> getmTasks() {
        return mTasks;
    }

    public void addmTasks(Task task) {
        this.mTasks.add(task);
    }

    public void setmTasks(ArrayList<Task> cur_tasks) {
        this.mTasks = cur_tasks;
    }

    private static ServerSingleton mServerSingleton;
    public static ServerSingleton get() {
        if (mServerSingleton == null) {
            mServerSingleton = new ServerSingleton();
        }
        return mServerSingleton;
    }
}
