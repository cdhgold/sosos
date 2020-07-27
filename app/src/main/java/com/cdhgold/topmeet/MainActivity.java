package com.cdhgold.topmeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.cdhgold.topmeet.Fragm.MnewFragment;
import com.cdhgold.topmeet.util.GetMember;
import com.cdhgold.topmeet.util.PreferenceManager;
/*
회원가입여부에따라 화면 분기
 */

public class MainActivity extends AppCompatActivity    {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MnewFragment newmember = new MnewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_fragm);
        /* 첫 화면 지정 - 회원가입여부 확인후 , 화면 전환
            deviceid 로 등록여부확인 : 서버통신
         */
        String deviceid = PreferenceManager.getString(this, "DEVICEID");// device id
        //서버통신 회원유무확인
        GetMember gm = new GetMember(this);
        gm.start();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.infoFrameLayout, newmember).commitAllowingStateLoss();

    }

}