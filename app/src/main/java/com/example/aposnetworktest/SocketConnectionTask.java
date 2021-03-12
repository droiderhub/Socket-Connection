package com.example.aposnetworktest;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

public class SocketConnectionTask extends AsyncTask<String, Void,String > {

  //  SSLSocket requestSocket = null;
   // private SSLSocketFactoryExtended sslsocketfactory;
    private  InputStream inputStream = null;
    int port = 0;
    String ip = "";
    public static int failureCount = -1;
    String output = null;
    Socket socket = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // requestSocket = null;
        socket = null;
        inputStream = AppManager.getContext().getResources().openRawResource(R.raw.cert);
        port = AppManager.getInstance().getInt("port");
        ip = AppManager.getInstance().getString("ip");
       // port = 1000;
//        ip = "121.244.157.168";

        failureCount = -1;
        output = null;

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Logger.v("failureCount --" + failureCount);
            Logger.v("Socket 5");
            SecurityManager manager = new SecurityManager();
            Logger.v("Socket 4");
            manager.checkConnect(ip, port);
            Logger.v("Socket 3");
            Logger.v("Soc --"+manager.getInCheck());
           // sslsocketfactory = new SSLSocketFactoryExtended(inputStream, "1.2",false);
            socket =new Socket("88.213.84.66", 2008);
            Logger.v("Socket 1");
            //requestSocket = (SSLSocket) sslsocketfactory.createSocket(ip, port);
            Logger.v("Socket 2");
           // requestSocket.setSoTimeout(10000);
            socket.setSoTimeout(10000);

           // Logger.v("sendISORequest Initiated" + requestSocket.isConnected());
            Logger.v("sendISORequest Initiated" + socket.isConnected());



//            requestSocket.setKeepAlive(true);
            Logger.v("echoTestConnection IF block");
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            byte[] finalEchoResponse =ByteConversionUtils.HexStringToByteArray(Constant.INPUT);

            //byte[] finalEchoResponse = (CreatePacket.createISORequest());

            // 1. get Input and Output streams
          //  bos = new BufferedOutputStream(requestSocket.getOutputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
         //   bis = new BufferedInputStream(requestSocket.getInputStream());
            bis = new BufferedInputStream(socket.getInputStream());
            // 2: Communicating with the server
            Logger.v("TRY block send");
            // 3: Post the request data
            Logger.v(finalEchoResponse);
            bos.write(finalEchoResponse);
            bos.flush();

            // 4: Receive the response data
            byte[] buffer = new byte[1024];
            int nBytes = -1;
            Logger.v("TRY block Receive");
            while ((nBytes = bis.read(buffer)) >= 0) {
                Logger.v("buffer --" + buffer.length);
                Logger.v(buffer);
                output = ByteConversionUtils.byteArrayToHexString(buffer,buffer.length,false);
                bos.flush();
                Logger.v("TRY block End Flush");
                failureCount = -1;

                /*    }
                    else {
                        break;
                    }*/
            }
            Logger.v("TRY block End" + nBytes);
           // Logger.v("requestSocket.isClosed() --" + requestSocket.isClosed());
            Logger.v("requestSocket.isClosed() --" + socket.isClosed());
            Logger.v("echoTestConnection else");
        } catch (Exception e) {
//            if (e.getMessage().contains("failed to connect"))
//                ((BaseActivity) context).showToast(context.getString(R.string.failed_to_connect));
            Logger.v("E1 --" + e.getMessage());
            output = output+" + " + e.getMessage();
            e.printStackTrace();
        } finally {
            Logger.v("Socker finally");
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
