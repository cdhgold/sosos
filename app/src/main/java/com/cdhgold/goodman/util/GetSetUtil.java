package com.cdhgold.goodman.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/*
공통모듈 용
 */
public class GetSetUtil {
    public String selectMemb(String eml, Context ctx     ){
        //서버통신 회원유무확인
        String ret = "";

        GetMember callable = new GetMember(ctx,"ONE","O");
        FutureTask futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            ret = (String)futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 결제가 안됐으면 , 회원결재창으로 이동...
        String nickname = "",gender = "",amt = "" ,mcnt = "";
        JSONObject jsonObject = null;
        if (!"null".equals(ret) && !"".equals(ret)) {
            try {
                jsonObject = new JSONObject(ret);
                String memJson = jsonObject.getString("memb");         // 회원정보
                String totmembJson = jsonObject.getString("totmemb");  // 회원총수
                JSONObject memObj = new JSONObject(memJson);
                JSONObject cntObj = new JSONObject(totmembJson);


                mcnt = cntObj.getString("mcnt"); // 회원가입총수
                eml = memObj.getString("eml");
                PreferenceManager.setString(ctx, "mcnt", mcnt); // 회원총수
                if ("null".equals(mcnt) || "null".equals(eml)) { // 신규화면으로

                    return null;
                }

                nickname = memObj.getString("nickname");
                gender = memObj.getString("gender");
                amt = memObj.getString("amt"); //
                amt = Util.getComma(amt);

                PreferenceManager.setString(ctx, "amt", amt);
                PreferenceManager.setString(ctx, "nickname", nickname);
                PreferenceManager.setString(ctx, "gender", gender);
                PreferenceManager.setString(ctx, "fromEml", eml); // 내이메일 주소

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return nickname;
    }// end selectMemb


}
