package com.cdhgold.topmeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.cdhgold.topmeet.util.PreferenceManager;
import com.cdhgold.topmeet.util.Util;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); //xml , java 소스 연결
        // device id get
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = Util.md5(android_id).toUpperCase();
//Log.i("device id=",deviceId);
        PreferenceManager.setString(this, "DEVICEID",deviceId);// device id

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent); //다음화면으로 넘어감
                finish();
            }
        },3000); //3초 뒤에 Runner객체 실행하도록 함
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}