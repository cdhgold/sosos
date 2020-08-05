package com.cdhgold.topmeet.util;

import android.app.AlertDialog;
import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    public static String md5(String str){
        String MD5 = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++) sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            MD5 = sb.toString();
        }
        catch(NoSuchAlgorithmException e) { e.printStackTrace(); MD5 = null; }
        catch (UnsupportedEncodingException e) { e.printStackTrace(); MD5 = null; }
        return MD5;
    }
    public static void showAlim(String nm, Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Top1%");
        builder.setMessage(nm );

        builder.show();
    }
}
