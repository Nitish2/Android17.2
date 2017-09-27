package com.example.hp.bound_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/*
 A bound service is the server in a client-server interface. It allows components to bind to
 the service, send requests, receive responses, and perform interprocess communication (IPC).
 */
public class MainActivity extends AppCompatActivity {

    //Declaring variables
    TextView textView;
    Button button;
    Bound_Service boundService;
    boolean serviceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inializing and creating objects by ID .
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() { // Creating onClick method
            @Override
            public void onClick(View view) {
                //Set the text view with the current time
                textView.setText(boundService.getTime());
                Toast.makeText(MainActivity.this, "Service is started", //Toast message
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {//Creating onStart method
        super.onStart();
        // Bind to LocalService
        // An Intent provides a facility for performing late runtime binding between the code
        Intent intent = new Intent(this,  Bound_Service.class); //Creating intent object
        // bindService() to obtain a persistent connection to a service
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

    }
    //ServiceConnection is a interface for monitoring the state of an application service.
    ServiceConnection serviceConnection = new ServiceConnection() { //Creating Object
        //onServiceDisconnected() is called when a connection to the Service has been lost.
        public void onServiceDisconnected(ComponentName name) {
            serviceBound=false;
            Toast.makeText(MainActivity.this, "Service is disconnected", // Toast Message
                    Toast.LENGTH_LONG).show();
        }
        @Override
        //onServiceConnected() is called when a connection to the Service has been established.
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceBound = true;
            /*
            Because we have bound to an explicit service that is running in our own process, we can
            cast its IBinder to a class and directly access it.
              */
            Bound_Service.LocalBinder localBinder = ( Bound_Service.LocalBinder) iBinder;
            boundService = localBinder.getService();
            Toast.makeText(MainActivity.this, "Service is connected", // Toast Message
                    Toast.LENGTH_LONG).show();
        }
    };


    protected void onStop(){ // Creating onStop method
        MainActivity.super.onStop();
        // Unbind from the service
        if (serviceBound)
        {
            unbindService(serviceConnection);
            serviceBound= false;

        }
    }
}