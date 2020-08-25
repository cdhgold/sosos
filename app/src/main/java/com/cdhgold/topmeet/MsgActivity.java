package com.cdhgold.topmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cdhgold.topmeet.util.SetMsg;
import com.cdhgold.topmeet.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
쪽지보내기
 */
public class MsgActivity extends Activity  implements View.OnClickListener{
    private TextView msg;
    private Button btnSnd;
    String eml = "";
    String fromEml = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Popup의 Title을 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.msg_main);
        msg = (TextView)findViewById(R.id.msg);
        btnSnd = (Button)findViewById(R.id.btnSnd);
        btnSnd.setOnClickListener(this);
        Intent intent = getIntent();
        eml = intent.getExtras().getString("eml");
        fromEml = intent.getExtras().getString("fromEml");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSnd: // 쪽지보내기
                String smsg = msg.getText().toString();
                String rjson = "";
                if("".equals(smsg.trim())){
                    Util.showAlim("Please Input Message!",this);
                    return;
                }else{
                    SetMsg callable = new SetMsg(this, smsg,eml,fromEml);
                    FutureTask futureTask = new FutureTask(callable);
                    Thread thread = new Thread(futureTask);
                    thread.start();
                    try {
                        rjson = (String)futureTask.get();
                        if("OK".equals(rjson)){
                            finish();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}