package com.example.praca.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

    public class automatic extends AppCompatActivity {
    Boolean runProgram = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);
        Button run = (Button) findViewById(R.id.automatic);
        Button stop = (Button) findViewById(R.id.stop);

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        runProgram = true;
                        steruj("192.168.0.2", "pi", "raspberry", "python Desktop/Auto.py");
                        SocketConnect nc = new SocketConnect("192.168.0.10", 7000);
                        nc.connectWithServer();
                        String message = "Ready";
                        byte [] receive = null;
                        String str = null;
                        nc.sendDataWithString(message);
                        while (runProgram) {
                            try {
                                receive = nc.receive();
                            } catch (IOException e) {
                                e.printStackTrace(); }
                            try {
                                str = new String(receive, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            List<String> czujnik = Arrays.asList(str.split(","));
                            System.out.print("Rozmiar listy: " + czujnik.size() + "\n");
                            Float f1 = Float.parseFloat(czujnik.get(0));
                            Float f2 = Float.parseFloat(czujnik.get(1));
                            Float f3 = Float.parseFloat(czujnik.get(2));
                            if(f1 < 3 ) {
                                nc.sendDataWithString("bacAndLeft");
                            }else if(f2 < 3 ) {
                                if(f1 > f3)
                                    nc.sendDataWithString("bacAndLeft");
                                else
                                    nc.sendDataWithString("bacAndRight");
                            }else if(f3 < 3 ) {
                                nc.sendDataWithString("bacAndRight");
                            }else {
                                nc.sendDataWithString("Forward"); }
                            String string1 = String.format("%.02f, ", f1);
                            String string2 = String.format("%.02f, ", f2);
                            String string3 = String.format("%.02f \n", f3);

                            System.out.print(string1);
                            System.out.print(string2);
                            System.out.print(string3);
                            System.out.print("\n");
                        }
                        nc.disConnectWithServer();
                        return null;
                    }
                }.execute(1);
            }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runProgram = false;
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            steruj("192.168.0.2", "pi", "raspberry", "python Desktop/stop.py");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public static void steruj(String host, String user, String password, String command1){

        try {

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command1);

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
