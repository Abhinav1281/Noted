package com.example.noted;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("I9fUDTKFWaP3qfbiDGcOBP6ZRN6t7BsgauvUwT9n")
                .clientKey("YjScY8HH30QrAqz3CmGTLWB0LnpMxfr6lSNsD4g7").server("https://parseapi.back4app.com/")
                .build());
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId","705344074314");
        installation.saveInBackground();
    }
}
