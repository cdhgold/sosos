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
HttpURLConnection 서버통신
 */
public class GetMember implements Callable<String> {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    private Context context;
    private String getGbn = "";
    private String knd = "";

    public GetMember(Context ctx, String tmp, String knd){ // knd - flag값 (회원상세, 기타 )
        this.context = ctx;
        this.getGbn = tmp;  // (M, F ), ONE 전체가져오기와, 한사람만 가져오기, 또는 eml
        this.knd = knd; // ALL, Detail
    }
    @Override
    public String call() throws Exception {
        String result = ""  ;
        String eml = PreferenceManager.getString(context, "eml");
 Log.i("thread","eml==========="+eml);
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
            if(  "ALL".equals(knd)){ // 회원보기 ( 남, 여 )
                map.put("gender", getGbn);
            }else if(  "O".equals(knd)){ // 본인정보
                map.put("eml", eml);
            }else if( "Detail".equals(knd)){// 상세보기( 선택멤버 )
                map.put("eml", this.getGbn);
                map.put("gbn", "detail");

            }

            StringBuffer sbParams = new StringBuffer();
            boolean isAnd = false;

            for(String key: map.keySet()){
                sbParams.append(key).append("=").append(map.get(key));
                sbParams.append("&");
            }
            wr.write(sbParams.substring(0,sbParams.length()-1));
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
            result = json.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
