package com.example.shoppingdashboardv2;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class interactions {

    int PORT = 5055;
    String FORMAT = "utf-8";
    String DISCONNECT_MESSAGE = "!DISCONNECT";
    int HEADER = 16;
    String server_ip = "192.168.100.110";
    PrintWriter out;
    BufferedReader in;
    Socket tcp_socket;



    public interactions() throws IOException {
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
        System.out.println("LENGTH MSG: " + length_msg);
        System.out.println("MSG: " + message_str);
        this.out.print(length_msg);
        this.out.flush();
        this.out.print(message_str);
        this.out.flush();

        System.out.println("OK");

        String returned_input = this.in.readLine();
        System.out.println("RETURNED SHIT: " + returned_input);
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

    //returns list of communities user is in
    public List<String> get_user_community(String username) throws IOException{
        return Arrays.asList(send("get_user_community", Arrays.asList(username)));
    }



}