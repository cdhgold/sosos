package com.cdhgold.goodman.util;

import android.app.AlertDialog;
import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class Util {
    // 암호화
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
        builder.setTitle("GoodMan!");
        builder.setMessage(nm );
        AlertDialog ad = builder.create();
        ad.show();
        //ad.dismiss();
        //builder.show();
    }
    public static String getComma(String tmp){
        String ret = "";
        DecimalFormat formatter = new DecimalFormat("###,###");
        ret = "$"+formatter.format(Double.parseDouble(tmp));

        return ret;
    }
    /*
    1 Do it yourself		 	60
        2 helping parents         100
        3 No cursing               100
        4 Volunteer activity      90
        5 Use of public transportation     80
        6 Reading books           80
        7 Vegetable               60
        8 Exercise                80
        9 helping others          100
        10  To curse                -70
        11  To torment              -100
        12  Cigarette               -60
        13  Alcohol                 -60
        14  Drug                    -100
        15  a meat diet             -60
        16  Fight                   -100
        17  a bad idea              -60
        18  Discrimination          -80
     */
    public static String getItemNm(String tmp){
        String ret = "";
        if("p01".equals(tmp)){
            ret = "Do it yourself";
        }
        else if("p02".equals(tmp)){
            ret = "Helping parents";
        }
        else if("p03".equals(tmp)){
            ret = "No cursing";
        }
        else if("p04".equals(tmp)){
            ret = "Volunteer activity";
        }
        else if("p05".equals(tmp)){
            ret = "Use of public transportation";
        }
        else if("p06".equals(tmp)){
            ret = "Reading books";
        }
        else if("p07".equals(tmp)){
            ret = "Vegetable";
        }
        else if("p08".equals(tmp)){
            ret = "Exercise";
        }
        else if("p09".equals(tmp)){
            ret = "Helping others";
        }
        else if("p10".equals(tmp)){
            ret = "To curse";
        }
        else if("p11".equals(tmp)){
            ret = "To torment";
        }
        else if("p12".equals(tmp)){
            ret = "Cigarette";
        }
        else if("p13".equals(tmp)){
            ret = "Alcohol";
        }
        else if("p14".equals(tmp)){
            ret = "Drug";
        }
        else if("p15".equals(tmp)){
            ret = "a meat diet";
        }
        else if("p16".equals(tmp)){
            ret = "Fight";
        }
        else if("p17".equals(tmp)){
            ret = "a bad idea";
        }
        else if("p18".equals(tmp)){
            ret = "Discrimination";
        }

        return ret;
    }
}
