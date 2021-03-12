package com.example.aposnetworktest;

import android.app.Application;
import android.content.Context;
/**
 * Created by satyajittarafdar on 04/03/18.
 */

public class AppInit extends Application {
    private static Context context;
    public static AppInit appInit;
    public AppInit() {
    }

    public static Context getContext() {

        return context;
    }
    public static AppInit getInstance() {
        return appInit;
    }

    public static String getPrefsName() {

        return context.getPackageName();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInit = this;
        context = getApplicationContext();

    }
}
