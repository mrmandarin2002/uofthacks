package com.example.shoppingdashboardv2;

import android.os.StrictMode;

import com.example.shoppingdashboardv2.Task;

import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



public class interactions{

    int PORT = 5055;
    String FORMAT = "utf-8";
    String DISCONNECT_MESSAGE = "!DISCONNECT";
    int HEADER = 16;
    String server_ip = "192.168.100.110";
    PrintWriter out;
    BufferedReader in;
    Socket tcp_socket;


    public interactions() throws IOException {

        /////////////////////////////////
        //CODE FROM GOD HIMSELF
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////

        System.out.println("YEEEE");
        this.tcp_socket = new Socket(server_ip, PORT);
        this.out = new PrintWriter(tcp_socket.getOutputStream(), true);
        this.in = new BufferedReader( new InputStreamReader( tcp_socket.getInputStream() ) );
        send("fucker", Arrays.asList("Derek", "Fucker"));
    }

    //sending a message to the server
    //and getting a response back
    public String send(String function, List<String> arguments) throws IOException {
        String message_str = function + ';';
        for(String argument : arguments){
            message_str += argument + '|';
        }

        //this is disgusting code don't mind it
        String length_msg = "";
        int length_sz = String.valueOf(message_str.length()).length();
        for(int x = 0; x < HEADER - length_sz; x++){
            length_msg += '0';
        }
        length_msg += String.valueOf(message_str.length());
        //System.out.println("LENGTH MSG: " + length_msg);
        //System.out.println("MSG: " + message_str);
        this.out.print(length_msg);
        this.out.flush();
        this.out.print(message_str);
        this.out.flush();

        //System.out.println("OK");

        String returned_input = this.in.readLine();
        //System.out.println("RETURNED SHIT: " + returned_input);
        return returned_input;
    }

    //returns 1 if user is valid
    //returns 0 otherwise
    public int check_user(String username, String password) throws IOException {
        return Integer.parseInt(send("check_user", Arrays.asList(username, password)));
    }

    //returns 1 if sign_up was successful
    //returns 0 if username was already taken
    public int sign_up(String username, String password) throws IOException{
        return Integer.parseInt(send("sign_up", Arrays.asList(username, password)));
    }

    public ArrayList<String> split_that_works(String thing, String splitter){
        ArrayList <String> myList = new ArrayList<String>();
        String temp_s = "";
        for(int x = 0; x < thing.length(); x++){
            if(thing.charAt(x) == splitter.charAt(0)){
                myList.add(temp_s);
                temp_s = "";
            } else{
                temp_s += thing.charAt(x);
            }
        }
        myList.add(temp_s);
        return myList;
    }

    //returns list of communities user is in
    public ArrayList<String> get_community(String username) throws IOException{
        ArrayList<String> myList = new ArrayList<String>();
        String test_shit = send("get_community", Arrays.asList(username));
        System.out.println(test_shit);
        for(String community : split_that_works(test_shit, "|")){
            myList.add(community);
        }
        return myList;
    }

    public int join_community(String username, String community_code) throws IOException{
        return Integer.parseInt(send("join_community", Arrays.asList(username, community_code)));
    }

    //returns community code
    public String create_community(String username, String community_name) throws IOException{
        String test =  send("create_community", Arrays.asList(username, community_name));
        System.out.println(test);
        return test;
    }


    //return 1 if push was successful
    /*
    For server use

    first argument is community_code
    each task is separated by colon (|)
    each task works as follows (separated by : within task)
    [0] = username
    [1] = destination
    [2] = start
    [3] = finish
    [4] = max orders
    [everything after] = requests
     */
    //pushes event changes to the server
    public int push_events(String community_code, ArrayList<Task> tasks) throws IOException{

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //this is fucking disgusting
        List<String> str_tasks = new ArrayList<>();
        str_tasks.add(community_code);
        for(Task task : tasks){
            String temp_string = "";
            temp_string += task.getName() + '~';
            temp_string += task.getDestination() + '~';
            temp_string += sdf.format(task.getStart()) + '~';
            temp_string += sdf.format(task.getFinish())+ '~';
            temp_string += String.valueOf(task.getMax_orders());
            for(String request : task.getRequests()){
                temp_string += '~' + request;
            }
            str_tasks.add(temp_string);
        }

        System.out.println(str_tasks);
        return Integer.parseInt(send("push_events", str_tasks));
    }

    //it's legit not possible to make more disgusting code than this
    //returns a list of events of a certain community
    public ArrayList<Task> pull_events(String community_code) throws IOException, ParseException {

        String data = send("pull_events", Arrays.asList(community_code));
        if(data == "0"){
            System.out.println("FUCK SOMETHING IS OFF IN PULL EVENTS");
        }
        ArrayList<Task> tasks = new ArrayList<>();
        if(data.length() > 0) {
            System.out.println("DATA: " + data);
            for (String task : split_that_works(data, "|")) {

                List<String> task_list = split_that_works(task, "~");

                ArrayList<String> requests = new ArrayList<>();

                for (int x = 5; x < task_list.size(); x++) {
                    requests.add(task_list.get(x));
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = sdf.parse(task_list.get(2));
                Date endDate = sdf.parse(task_list.get(3));
                Task temp_task = new Task(task_list.get(0), task_list.get(1), startDate, endDate, Integer.parseInt(task_list.get(4)), requests);
                tasks.add(temp_task);
            }
        } else{
            System.out.println("NO TASKS AVAILABLE");
        }
        return tasks;
    }
}