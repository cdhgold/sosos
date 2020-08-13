package com.cdhgold.topmeet.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;

/*
HttpURLConnection 서버통신
 */
public class GetMember implements Callable<String> {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    private Context context;
    private String getGbn = "";
    public GetMember(Context ctx, String tmp){
        this.context = ctx;
        this.getGbn = tmp;  // (M, F ), ONE 전체가져오기와, 한사람만 가져오기
    }
    @Override
    public String call() throws Exception {
        String result = ""  ;
        String DEVICEID = PreferenceManager.getString(context, "DEVICEID");
 Log.i("thread","DEVICEID==========="+DEVICEID);
        try {
            URL url = new URL("http://konginfo.co.kr/topbd/topbd/getMem");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setUseCaches(false); conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            HashMap<String, String> map = new HashMap<>();
            if(!"ONE".equals(getGbn)){
                map.put("gender", getGbn);
            }else{
                map.put("DEVICEID", DEVICEID);
            }

            StringBuffer sbParams = new StringBuffer();
            boolean isAnd = false;

            for(String key: map.keySet()){
                sbParams.append(key).append("=").append(map.get(key));
            }
            wr.write(sbParams.toString());
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
            String nickname = "";
            Log.i("thread","json==========="+json.toString());
            if("ONE".equals(getGbn) ) {// 한사람만 가져오기

                result = json.toString();
            }else{
                //all  멤버전체가져오기
                result = json.toString();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
