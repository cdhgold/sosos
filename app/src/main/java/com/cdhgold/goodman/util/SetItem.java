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
item 구매
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
        if("p01".equals(item)||"p07".equals(item)){
            amt =  "60";
        }
        else if("p02".equals(item) || "p03".equals(item)||"p09".equals(item) ){
            amt =  "100";
        }
        else if("p04".equals(item) ){
            amt =  "90";
        }
        else if("p05".equals(item) || "p06".equals(item) || "p08".equals(item)){
            amt =  "80";
        }
        else if("p10".equals(item)){
            amt =  "-70";
        }
        else if("p11".equals(item)||"p14".equals(item)||"p16".equals(item)){
            amt =  "-100";
        }
        else if("p12".equals(item)||"p13".equals(item)||"p15".equals(item)
                ||"p17".equals(item)||"p18".equals(item) ){
            amt =  "-60";
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
                setMsg = jsonObject.getString("setItem");
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
