package com.cdhgold.topmeet.util;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/*
HttpURLConnection 서버통신
 */
public class GetMember extends Thread {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;

    private Context context;
    public GetMember(Context ctx){
        this.context = ctx;
    }
    public void run() {
        try { URL url = new URL("http://konginfo.co.kr/topbd/getMem");
            String result = "";
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setUseCaches(false); conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000); conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            //outputStream.write(postData.getBytes("UTF-8"));

            outputStream.flush(); outputStream.close();
            Log.i("PHPRequest","No Problem");
            //result = readStream(conn.getInputStream());
            conn.disconnect();

        }
        catch (Exception ex) {



        }


    }

}
