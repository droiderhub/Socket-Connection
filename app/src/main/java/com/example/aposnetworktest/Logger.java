package com.example.aposnetworktest;

import android.util.Log;


public class Logger {

    public static void v(String s){
        Log.v("responce","Tag Msg --"+s);
    }

    public static void v(String tag, String s){
        Log.v("responce" ,"Tag Msg --"+s);
    }

    public static void ve(String s){
        Log.v("responce" ,"Tag Msg --"+s);
    }
    public static void e(String tag, String s){
        Log.e(tag ,"Tag Msg --"+s);
    }

    public static void showMessage(String tag , int s){
        Log.v("responce","tag --- "+tag+" \nTag Msg --"+s);
    }

    public static void bytArray(String tag, byte[] isoBufferEchoResponse){
        String hexa1 = ByteConversionUtils.byteArrayToHexString(isoBufferEchoResponse, isoBufferEchoResponse.length, false);
        Logger.v(tag+"-"+hexa1);
    }

    public static void v(byte[] isoBufferEchoResponse){
        String hexa1 = ByteConversionUtils.byteArrayToHexString(isoBufferEchoResponse, isoBufferEchoResponse.length, false);
        Logger.v("byte ---"+hexa1);
    }

}
