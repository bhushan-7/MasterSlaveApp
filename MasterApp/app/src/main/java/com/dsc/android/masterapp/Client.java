package com.dsc.android.masterapp;

import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AsyncTask<Void, Void, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    LinearLayout layout;
    MainActivity mainActivity;

    Client(String addr, int port, TextView textResponse, LinearLayout linearLayout, MainActivity mainActivity) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        layout=linearLayout;
        this.mainActivity=mainActivity;
    }

    @Override
    protected String doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            /*
             * notice: inputStream.read() will block if no data return
             */
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "Reopen app and enter correct ip address and port number : UnknownHostException: " + e.toString();
            //  mainActivity.buttonConnect.setClickable(true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "Reopen app and enter correct ip address and port number : IOException: " + e.toString();
            // mainActivity.buttonConnect.setClickable(true);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        if (response=="Connected")
        {
            mainActivity.buttonConnect.setClickable(false);
        }
        layout.setAlpha(1);
        super.onPostExecute(result);
    }

}