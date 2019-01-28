package com.example.praca.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        function();
    }

    void function() {
    Button stop1 = (Button) findViewById(R.id.stop);
    Button turnLeft = (Button) findViewById(R.id.left);
    Button turnRight = (Button) findViewById(R.id.right);
    Button back = (Button) findViewById(R.id.backward);

        Button forward1 = (Button) findViewById(R.id.forward);
        forward1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                        @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            steruj("192.168.0.2", "pi", "raspberry", "python Desktop/Adafruit_MotorHAT/forward.py");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        turnLeft.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            steruj("192.168.0.2", "pi", "raspberry", "python Desktop/Adafruit_MotorHAT/left.py");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        turnRight.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            steruj("192.168.0.2", "pi", "raspberry", "python Desktop/Adafruit_MotorHAT/right.py");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        back.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            steruj("192.168.0.2", "pi", "raspberry", "python Desktop/Adafruit_MotorHAT/back.py");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        stop1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            steruj("192.168.0.2", "pi", "raspberry", "python Desktop/Adafruit_MotorHAT/stop.py");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }


    public static void steruj(String host, String user, String password, String command1) throws Exception {

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



/*channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    //System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    break;
                }
            }
            */