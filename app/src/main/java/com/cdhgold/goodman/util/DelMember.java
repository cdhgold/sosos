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
회원 삭제   :  결제실패시나 , 탈퇴시
 */
public class DelMember implements Callable<String> {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    private Context context;
    public DelMember(Context ctx){
        this.context = ctx;
    }
    @Override
    public String call() throws Exception {
        String result = ""  ;
        String eml = PreferenceManager.getString(context, "eml"); // pk
        Log.i("thread","eml==========="+eml);
        try {

            URL url = new URL("http://konginfo.co.kr/topbd/topbd/makeMem");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setUseCaches(false); conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF8");

            HashMap<String, String> map = new HashMap<>();
            map.put("eml", eml);

            StringBuffer sbParams = new StringBuffer();
            boolean isAnd = false;

            for(String key: map.keySet()){
                sbParams.append(key).append("=").append(map.get(key));
            }

            String spost = sbParams.toString();
            wr.write(spost );
            wr.flush();
            wr.close();
            StringBuffer json = new StringBuffer();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    json.append(inputLine); // 처리결과
                }
                in.close();
            }
            conn.disconnect();
            String delMem = "";
            Log.i("thread","json==========="+json.toString());
            if(!json.equals("")) {
                JSONObject jsonObject = new JSONObject(json.toString());
                delMem = jsonObject.getString("delMem");
            }
            result = delMem;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
