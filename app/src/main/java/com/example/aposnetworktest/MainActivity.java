package com.example.aposnetworktest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button check_conn_btn;
    EditText ip_address_et,port_no_et;
    TextView output_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip_address_et = findViewById(R.id.ip_address_et);
        port_no_et = findViewById(R.id.port_no_et);
        output_tv = findViewById(R.id.output_tv);
        check_conn_btn = findViewById(R.id.check_conn_btn);
        test();

        ip_address_et.setText(AppManager.getInstance().getString("ip"));
        port_no_et.setText(String.valueOf(AppManager.getInstance().getInt("port")));

        check_conn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().setInt("port", Integer.parseInt(port_no_et.getText().toString().trim()));
                AppManager.getInstance().setString("ip", (ip_address_et.getText().toString().trim()));
                Logger.v("ip-----------"+ip_address_et.getText().toString().trim());
                Logger.v("port-----------"+Integer.parseInt(port_no_et.getText().toString().trim()));
                Logger.v("ip-----------"+AppManager.getInstance().getString("ip"));
                Logger.v("port-----------"+AppManager.getInstance().getInt("port"));
                try {
                    Logger.v("onclick socket");
                    //with ssl
                   String output= new SocketConnectionTask().execute().get();
                    Logger.v("socketoutput----------"+output);
                    output_tv.setText("IP = "+AppManager.getInstance().getString("ip")+"   Port = "+AppManager.getInstance().getInt("port")+"  Output = "+output);

                    //without ssl
                    byte[] finalEchoResponse =ByteConversionUtils.HexStringToByteArray(Constant.INPUT);
//                    sendMessage(output_tv.getText().toString());
                  //  sendMessage(Constant.INPUT);

                    //without ssl try asynk
                    new MyAsyncTask(MainActivity.this, output_tv, check_conn_btn).execute(
                            ip_address_et.getText().toString(),
                            port_no_et.getText().toString(),
                            output_tv.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                } /*catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        });
    }

    public void test(){

    }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        Activity activity;
        Button button;
        TextView textView;
        IOException ioException;
        String output = null;
        MyAsyncTask(Activity activity, TextView textView, Button button) {
            super();
            Logger.v("asynk_initialise");
            this.activity = activity;
            this.textView = textView;
            this.button = button;
            this.ioException = null;
        }
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            Logger.v("doInBackground");
            try {
               // Socket socket = new Socket(params[0], Integer.parseInt(params[1]));
                Socket socket =new Socket("88.213.84.66", 2008);

                OutputStream out = socket.getOutputStream();
                byte[] finalEchoResponse =ByteConversionUtils.HexStringToByteArray(Constant.INPUT);
                //out.write(params[2].getBytes());
                Logger.v("input_data----"+Constant.INPUT);
                out.write(finalEchoResponse);
                Logger.v("out_flush");
                InputStream in = socket.getInputStream();
                byte buf[] = new byte[1024];
                int nbytes = -1;
                while ((nbytes = in.read(buf)) >= 0) {
                    Logger.v("inside_while");
                    Logger.v("buffer --" + buf.length);
                    sb.append(new String(buf, 0, nbytes));
                    output = ByteConversionUtils.byteArrayToHexString(buf,buf.length,false);
                    Logger.v("output------"+output);
                }
                Logger.v("while_done");
                socket.close();
                Logger.v("socket_close");
            } catch(IOException e) {

                this.ioException = e;
                Logger.v("expection_saty-----"+e);
                return "error";
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            Logger.v("ompost_asynk");
            if (this.ioException != null) {
                new AlertDialog.Builder(this.activity)
                        .setTitle("An error occurrsed")
                        .setMessage(this.ioException.toString())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                this.textView.setText(result);
            }
            this.button.setEnabled(true);
        }
    }

    private void sendMessage(final String msg) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("88.213.84.66", 2008);

                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println(msg);
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st = input.readLine();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String s = output_tv.getText().toString();
                            if (st.trim().length() != 0)
                                output_tv.setText(s + "\nFrom Server : " + st);
                        }
                    });

                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
