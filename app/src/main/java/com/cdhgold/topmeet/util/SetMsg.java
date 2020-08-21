package com.cdhgold.topmeet.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;

/*
쪽지보내기 
 */
public class SetMsg implements Callable<String> {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    String fromEml = "";
    String eml = "";

    private Context context;
    public SetMsg(Context ctx, String eml ){
        this.context = ctx;
        this.fromEml = eml ;
    }
    @Override
    public String call() throws Exception {
        String result = ""  ;
        String msg = PreferenceManager.getString(context, "msg");


        Log.i("thread","fromEml==========="+fromEml);
        try {
            url = new URL("http://konginfo.co.kr/topbd/topbd/setMsg");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF8");

            HashMap<String, String> map = new HashMap<>();
            map.put("eml", eml);
            map.put("msg", msg);
            map.put("fromEml", fromEml);

            StringBuffer sbParams = new StringBuffer();
            boolean isAnd = false;

            for(String key: map.keySet()){
                sbParams.append(key).append("=").append(map.get(key));
                sbParams.append("&");
            }

            String spost = sbParams.toString();
            wr.write(spost.substring(0,spost.length()-1));
            wr.flush();
            wr.close();
 Log.i("thread","spost==========="+spost.substring(0,spost.length()-1));
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


            String setMem = "";
            Log.i("thread","json==========="+json.toString());
            if(!"".equals(json.toString()) && !"null".equals(json.toString())) {
                JSONObject jsonObject = new JSONObject(json.toString());
                setMem = jsonObject.getString("setMem");
            }

            result = setMem;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

}
