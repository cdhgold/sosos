package com.cdhgold.topmeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.cdhgold.topmeet.Fragm.MnewFragment;
import com.cdhgold.topmeet.Fragm.MoldFragment;
import com.cdhgold.topmeet.util.GetMember;
import com.cdhgold.topmeet.util.PreferenceManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/*
회원가입여부에따라 화면 분기
 */

public class MainActivity extends AppCompatActivity    {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MnewFragment newmember = new MnewFragment();
    private MoldFragment oldmember = new MoldFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_fragm);
        /* 첫 화면 지정 - 회원가입여부 확인후 , 화면 전환
            deviceid 로 등록여부확인 : 서버통신
         */
        String deviceid = PreferenceManager.getString(this, "DEVICEID");// device id
        //서버통신 회원유무확인
        String ret = "";
        GetMember callable = new GetMember(this);
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
        Log.i("thread", "2===========end " +ret );
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!"".equals(ret)){// 회원
            transaction.replace(R.id.infoFrameLayout, oldmember).commitAllowingStateLoss();
        }else{  // 비회원
            transaction.replace(R.id.infoFrameLayout, newmember).commitAllowingStateLoss();
        }

    }

}