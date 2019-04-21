package com.dsc.android.masterapp;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect;
    LinearLayout layout;
    Button pauseplay;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        response = (TextView) findViewById(R.id.responseTextView);
        layout=findViewById(R.id.llc);
        pauseplay=findViewById(R.id.pauseplay);
        layout.setAlpha(0);

        buttonConnect.setOnClickListener(new OnClickListener() {



            @Override
            public void onClick(View arg0) {


                String s1=editTextAddress.getText()
                        .toString();
                String s2= editTextPort
                        .getText().toString();

                if (TextUtils.isEmpty(s1) ||TextUtils.isEmpty(s2))
                {
                    Toast.makeText(MainActivity.this,"Enter ip address and port number",Toast.LENGTH_LONG).show();
                }
                else {
                    buttonConnect.setClickable(false);
                    Client myClient = new Client(s1,Integer.parseInt(s2),response,layout,mainActivity);
                    myClient.execute();
                }

            }
        });


        pauseplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Client myClient = new Client(editTextAddress.getText()
                        .toString(), Integer.parseInt(editTextPort
                        .getText().toString()), response,layout,mainActivity);
                myClient.execute();
            }
        });


    }
}