package com.example.aposnetworktest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
public class AppManager {
    private static AppManager instance = null;
    private static Context mContext;
    private static String LogString = "AppManager";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AppManager(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences("payswiff_data", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager(AppInit.getContext());
        }
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public void setApplicationContext(Context context) {
        mContext = context;
    }

    public Context getApplicationContext() {
        return mContext;
    }
    //get the Device info


    //gets the unique android id...
    public static String getUniqueId(Context context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(), "android_id");
    }


    //gets the App Version
    public static String getAppVersion(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        // Misplaced declaration of an exception variable
        catch (Exception e) {
            e.printStackTrace();

        }
        if (packageInfo != null)
            return packageInfo.versionName;
        else
            return "";
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        // Misplaced declaration of an exception variable
        catch (Exception e) {
            e.printStackTrace();

        }
        if (packageInfo != null)
            return packageInfo.versionCode;
        else
            return 0;
    }

    public void setString(String key, String value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setInt(String key, int value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        return preferences.getInt(key, 1000);
    }

    public String getString(String key) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        return preferences.getString(key,"121.244.157.168");
    }

    private int getDefaultInt(String key) {
        return 0;
    }

    public boolean getBoolean(String key) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    //---------------------------------------App Specific  starts here ---------------------------------------------------


}