package com.example.hp.bound_service;

import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.Date;

/*
  * A Service is an application component that can perform long-running operations in the background
       and it does not provide a user interface.
  * Creating class Music_Service and extends it from Service base class .

 */
public class Bound_Service extends Service {
    IBinder iBinder = new LocalBinder(); // Creating object
    @Nullable
    @Override
    /*
    onBind()returns an IBinder object that defines the programming interface that clients
       can use to interact with the service.
     */
    public IBinder onBind(Intent intent) {
        return iBinder ;
    }

    public class LocalBinder extends Binder {// Creating inner class and extends it from Binder
        public Bound_Service getService() { // Return the application that owns this service.
            return Bound_Service.this;
        }

    }

    public String getTime() {// Creating method
        // SimpleDateFormat is a concrete class for formatting and parsing dates .
        SimpleDateFormat simpleDateFormat = null; // Creating object
        //Applying the condition for getting the date and time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Setting date format
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return simpleDateFormat.format(new Date());
        }
        return simpleDateFormat.format(new Date()); // returns the date

    }
}
