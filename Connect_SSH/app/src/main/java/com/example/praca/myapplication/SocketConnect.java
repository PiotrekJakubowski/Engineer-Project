package com.example.praca.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnect {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private byte[] buffer = new byte[157*10];
    private InputStream input;
    private String host = null;
    private int port = 7000;

    public SocketConnect(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connectWithServer() {
        try {
            if (socket == null) {
                socket = new Socket(this.host, this.port);
                out = new PrintWriter(socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disConnectWithServer() {
        if (socket != null) {
            if (socket.isConnected()) {
                try {
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendDataWithString(String message) {
        if (message != null) {
            out.write(message);
            out.flush();
        }
    }
    public byte[] receive() throws IOException {
        input = socket.getInputStream();
        input.read(buffer);
        return this.buffer;
    }

}

