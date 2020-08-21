package com.cdhgold.topmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
/*
쪽지보내기
 */
public class MsgActivity extends Activity  implements View.OnClickListener{
    private TextView msg;
    private Button btnSnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Popup의 Title을 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.msg_main);
        msg = (TextView)findViewById(R.id.msg);
        btnSnd = (Button)findViewById(R.id.btnSnd);
        btnSnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSnd: // 쪽지보내기

                break;
        }
    }
}