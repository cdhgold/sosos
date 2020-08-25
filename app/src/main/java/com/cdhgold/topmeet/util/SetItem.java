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
item 구매
 */
public class SetItem implements Callable<String> {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    String item = "";
    String nickname = "";
    String eml = "";
    String amt = ""; // item 금액 ( $ )

    private Context context;
    public SetItem(Context ctx, String item   ){
        this.context = ctx;
        this.item = item ; // 상품id
        nickname = PreferenceManager.getString(ctx,"nickname");
        eml = PreferenceManager.getString(ctx,"eml");
        if("p01".equals(item)){
            amt =  "20"; // 신발
        }
        else if("p02".equals(item)){
            amt =  "30"; // 시계
        }
        else if("p02".equals(item)){
            amt =  "30"; // 시계
        }
        else if("p03".equals(item) || "p04".equals(item)){
            amt =  "50"; // 반지, 목걸이
        }
        else if("p05".equals(item)){
            amt =  "100"; // 자동차
        }
        else  {
            amt =  "1"; // 책,봉사활동,채식,대중교통이용
        }



    }
    @Override
    public String call() throws Exception {
        String result = ""  ;

        Log.i("thread","item==========="+item);
        try {
            url = new URL("http://konginfo.co.kr/topbd/topbd/setItem");

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
            map.put("prod", item);
            map.put("nickname", nickname);
            map.put("eml", eml);
            map.put("amt", amt);

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
