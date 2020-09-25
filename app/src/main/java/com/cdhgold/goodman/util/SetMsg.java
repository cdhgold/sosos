package com.cdhgold.goodman.util;

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
    String msg = "";
    String nicknm = "";

    private Context context;
    public SetMsg(Context ctx, String msg,String eml ,String fromEml ,String nicknm ){
        this.context = ctx;
        this.msg = msg ;
        this.fromEml = fromEml ;
        this.eml = eml ;
        this.nicknm = nicknm ;

    }
    @Override
    public String call() throws Exception {
        String result = ""  ;

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
            map.put("nicknm", nicknm);

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


            String setMsg = "";
            Log.i("thread","json==========="+json.toString());
            if(!"".equals(json.toString()) && !"null".equals(json.toString())) {
                JSONObject jsonObject = new JSONObject(json.toString());
                setMsg = jsonObject.getString("setMsg");
            }

            result = setMsg;
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
