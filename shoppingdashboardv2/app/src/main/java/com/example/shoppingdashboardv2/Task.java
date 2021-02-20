package com.example.shoppingdashboardv2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Task {
    private String name;
    private String destination;

    private Date start;
    private Date finish;
    private int max_orders;



    /**
     * private final Date variable holding the current date
     */
    private final Date CURRENTDATE = new Date();


    public Task(String name, String destination, Date start, Date finish, int max_orders) {
        this.name = name;
        this.destination = destination;
        this.start = start;
        this.finish = finish;
        this.max_orders = max_orders;

        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public Date getStart() {
        return start;
    }

    public Date getFinish() {
        return finish;
    }

    public int getMax_orders() {
        return max_orders;
    }
    public void setTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));

    }
}
