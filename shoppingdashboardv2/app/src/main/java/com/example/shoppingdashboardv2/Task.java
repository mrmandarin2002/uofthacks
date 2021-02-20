package com.example.shoppingdashboardv2;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Task object with name, location, start and end dates, and number of orders
 */
public class Task implements Parcelable {
    private String name;
    private String destination;

    private Date start;
    private Date finish;
    private int max_orders;
    private ArrayList<String> requests;


    /**
     * private final Date variable holding the current date
     */
    private final Date CURRENTDATE = new Date();

    /**
     * task object to store name, location, start time, end time, and number of orders person is willing to accomodate
     ** @param name - person's name
     * @param destination - location
     * @param start - when they posted the "task"
     * @param finish - when they are going
     * @param max_orders - how many orders they are willing to facilitate
     */
    public Task(String name, String destination, Date start, Date finish, int max_orders, ArrayList<String> requests) {
        this.name = name;
        this.destination = destination;
        this.start = start;
        this.finish = finish;
        this.max_orders = max_orders;
        this.requests = requests;

        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
    }

    protected Task(Parcel in) {
        name = in.readString();
        destination = in.readString();
        max_orders = in.readInt();
        requests = in.createStringArrayList();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    /**
     * @return String person's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return String location of task
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return Date of when task was posted
     */
    public Date getStart() {
        return start;
    }

    /**
     * @return Date of when task will be completed (end time)
     */
    public Date getFinish() {
        return finish;
    }

    /**
     * @return int number of orders they are willing to satisfy
     */
    public int getMax_orders() {
        return max_orders;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));

    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(destination);
        dest.writeInt(max_orders);
        dest.writeStringList(requests);
    }
}
