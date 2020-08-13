package com.cdhgold.topmeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.cdhgold.topmeet.Fragm.MnewFragment;
import com.cdhgold.topmeet.Fragm.MoldFragment;
import com.cdhgold.topmeet.util.BillingManager;
import com.cdhgold.topmeet.util.GetMember;
import com.cdhgold.topmeet.util.PreferenceManager;
import com.cdhgold.topmeet.util.Util;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/*
회원가입여부에따라 화면 분기
 */

public class MainActivity extends AppCompatActivity    {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MnewFragment newmember = new MnewFragment();
    private MoldFragment oldmember = new MoldFragment();
    private AppUpdateManager mAppUpdateManager; // auto update
    private final int MY_REQUEST_CODE = 100;// auto update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_fragm);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view); // 하단메뉴
        bottomNav.setOnNavigationItemSelectedListener(new ItemSelectListener());
         /*
        uto update check
         */
        mAppUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        // 업데이트 사용 가능 상태인지 체크
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        // 사용가능 체크 리스너를 달아준다
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // 업데이트가 사용 가능한 상태 (업데이트 있음) -> 이곳에서 업데이트를 요청해주자
                    try {

                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                                AppUpdateType.IMMEDIATE,
                                // 현재 Activity
                                MainActivity.this,
                                // 전역변수로 선언해준 Code
                                MY_REQUEST_CODE);

                    } catch (IntentSender.SendIntentException e) {
                        Log.e("AppUpdater", "AppUpdateManager Error", e);
                        e.printStackTrace();
                    }
                } else {

                    // 업데이트가 사용 가능하지 않은 상태(업데이트 없음) -> 다음 액티비티로 넘어가도록
                }


            }
        });// auto update end

        /* 첫 화면 지정 - 회원가입여부 확인후 , 화면 전환
            deviceid 로 등록여부확인 : 서버통신
         */
        String deviceid = PreferenceManager.getString(this, "DEVICEID");// device id
        //서버통신 회원유무확인
        String ret = "";
        GetMember callable = new GetMember(this,"ONE");
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

        // 결제가 안됐으면 , 회원결재창으로 이동...
        String nickname = "",payment = "";
        if (!"null".equals(ret) && !"".equals(ret)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(ret);
                    nickname = jsonObject.getString("nickname");
                    payment = jsonObject.getString("payment"); // F 면 결재창으로
                    PreferenceManager.setString(getApplicationContext() , "payment",payment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            Log.i("thread", "1===========" + nickname);
            if(!"".equals(nickname) && "S".equals(payment) ){
                transaction.replace(R.id.infoFrameLayout, oldmember).commitAllowingStateLoss();// 기존 멤버화면
            }else{
                //회원결재창
                //BillingManager bill = new BillingManager(this);
                //bill.setProd("p_member");
                String inApp = "OK";
//inApp = PreferenceManager.getString(getContext(), "inApp");
                if("OK".equals(inApp)) {// 회원등록 : 결제성공시
                    transaction.replace(R.id.infoFrameLayout, oldmember).commitAllowingStateLoss();
                }
            }
        }else{  // 비회원
            transaction.replace(R.id.infoFrameLayout, newmember).commitAllowingStateLoss();
        }

    }

    //fragment 전환
    public void replaceFragment(Fragment fragment ) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.infoFrameLayout,  fragment).commitAllowingStateLoss();
    }
    //하단메뉴 home
    private class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.nav_home: // 첫화면 (메인 )
                    String payment = PreferenceManager.getString(getApplicationContext() , "payment" );
                    if("S".equals(payment) ) {// 회원결제가 성공이면
                        transaction.replace(R.id.infoFrameLayout, oldmember).commitAllowingStateLoss();
                    }else if("F".equals(payment) ) {// 결제실패 ,결재창으로

                    }else{ //결제가 안되
                        transaction.replace(R.id.infoFrameLayout, newmember).commitAllowingStateLoss();
                    }
                    break;

            }
            return true;
        }
    }
/*
auto upgrade
 */
    // auto update실패시
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
//Log.d("AppUpdate", "Update flow failed! Result code: " + resultCode); // 로그로 코드 확인
                Util.showAlim("앱업데이트 알림!",getApplicationContext());
                finishAffinity(); // 앱 종료
            }
        }
    }
    // auto update 앱재실행시
    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(
                new OnSuccessListener<AppUpdateInfo>() {
                    @Override
                    public void onSuccess(AppUpdateInfo appUpdateInfo) {
                        if (appUpdateInfo.updateAvailability()
                                == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // 인 앱 업데이트가 이미 실행중이었다면 계속해서 진행하도록
                            try {
                                mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, MY_REQUEST_CODE);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}