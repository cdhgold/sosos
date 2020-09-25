package com.cdhgold.goodman.util;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;

/*
 내 쪽지 보기
 */
public class GetMsg implements Callable<String> {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    private Context context;
    private String fromEml = "";    //  쪽지를 보낸사람
    private String eml = "";        //  쪽지를 받은사람 ( 내 이메일)

    public GetMsg(Context ctx, String eml ){
        this.context = ctx;
        this.eml = eml;
    }
    @Override
    public String call() throws Exception {
        String result = ""  ;
        String eml = PreferenceManager.getString(context, "eml");
 Log.i("thread","eml==========="+eml);
        try {
            URL url = new URL("http://konginfo.co.kr/topbd/topbd/getMsg");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setUseCaches(false); conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            HashMap<String, String> map = new HashMap<>();
            map.put("eml", eml);

            StringBuffer sbParams = new StringBuffer();

            for(String key: map.keySet()){
                sbParams.append(key).append("=").append(map.get(key));
            }
            wr.write(sbParams.toString() );
            wr.flush();
            wr.close();
            StringBuffer json = new StringBuffer();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    json.append(inputLine);
                }
                in.close();
            }

            conn.disconnect();
            Log.i("thread","json==========="+json.toString());
            result = json.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
