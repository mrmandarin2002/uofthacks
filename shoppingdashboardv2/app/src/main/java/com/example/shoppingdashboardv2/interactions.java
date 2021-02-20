package com.example.shoppingdashboardv2;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class interactions {

    int PORT = 5055;
    String FORMAT = "utf-8";
    String DISCONNECT_MESSAGE = "!DISCONNECT";
    int HEADER = 16;
    String server_ip = "192.168.100.110";

    public interactions() throws IOException {
        Socket tcp_socket = new Socket(server_ip, PORT);
        PrintWriter out = new PrintWriter(tcp_socket.getOutputStream(), true);
        out.println("TESTING_BITCHES");
    }

    public void send(String message){

    }
}
