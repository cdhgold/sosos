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

import com.cdhgold.topmeet.Fragm.MdetailFragment;
import com.cdhgold.topmeet.Fragm.MemlFragment;
import com.cdhgold.topmeet.Fragm.PayFragment;
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

public class MainActivity extends AppCompatActivity  implements MemInterf  {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MnewFragment newmember = new MnewFragment();
    private MoldFragment oldmember = new MoldFragment();
    private MemlFragment emlFrg = new MemlFragment();

    private PayFragment paymFrg = new PayFragment("p_member"); // 회비 결제
    private FragmentTransaction transaction ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_fragm);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view); // 하단메뉴
        bottomNav.setOnNavigationItemSelectedListener(new ItemSelectListener());

/*
      첫 화면 지정 - 회원가입여부 확인후 , 화면 전환
            eml 로 등록여부확인 : 서버통신
        등록회원은 PreferenceManager 값을 가져와서 사용,
        값이 없으면 eml 값을 받는 화면으로 이동
*/
        //서버통신 회원유무확인
        String ret = "";
        String eml = PreferenceManager.getString(this, "eml");
        if("".equals(eml) || "null".equals(eml)  ){    //eml화면으로 이동
            replaceFragment(emlFrg);
            return;
        }
        GetMember callable = new GetMember(this,"ONE","O");
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


        transaction = fragmentManager.beginTransaction();

        // 결제가 안됐으면 , 회원결재창으로 이동...
        String nickname = "",payment = "",amt = "";
        if (!"null".equals(ret) && !"".equals(ret)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(ret);
                    nickname = jsonObject.getString("nickname");
                    payment = jsonObject.getString("payment"); // F 면 결재창으로
                    amt = jsonObject.getString("amt"); //
                    amt = Util.getComma(amt);
                    PreferenceManager.setString(getApplicationContext() , "amt",amt);
                    PreferenceManager.setString(getApplicationContext() , "nickname",nickname);
                    PreferenceManager.setString(getApplicationContext() , "payment",payment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            Log.i("thread", "1===========" + nickname);
            if(!"".equals(nickname) && "S".equals(payment) ) {
                replaceFragmentThread(oldmember);
            }
            else if(!"".equals(nickname) && "F".equals(payment) ) { // 회비결제화면으로
                replaceFragmentThread(paymFrg);
            }else{
                //회원결재창
                //BillingManager bill = new BillingManager(this);
                //bill.setProd("p_member");
                String inApp = "OK";
//inApp = PreferenceManager.getString(getContext(), "inApp");
                if("OK".equals(inApp)) {// 회원등록 : 결제성공시
                    replaceFragmentThread(oldmember);
                }
            }
        }else{  // 비회원
            replaceFragmentThread(newmember);
        }

    }// end create


    public void replaceFragmentThread(Fragment fragment ) {
        transaction.replace(R.id.infoFrameLayout, fragment).commitAllowingStateLoss();

    }
    //fragment 전환
    public void replaceFragment(Fragment fragment ) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.infoFrameLayout,  fragment).commitAllowingStateLoss();
    }

    @Override
    public void memDel() {

    }
/*
상세보기로가기
 */
    @Override
    public void onItemClick(String eml) {

    }

    /*
    하단메뉴 home하단메뉴 home하단메뉴 home하단메뉴 home하단메뉴 home
     */
    private class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.nav_home: // 첫화면 (메인 )
                    String payment = PreferenceManager.getString(getApplicationContext() , "payment" );
                    String eml = PreferenceManager.getString(getApplicationContext() , "eml" );

                    if(!"".equals(eml) && "S".equals(payment) ) {// 회원결제가 성공이면
                        transaction.replace(R.id.infoFrameLayout, oldmember).commitAllowingStateLoss();
                    }else if(!"".equals(eml) && "F".equals(payment) ) {// 결제실패 ,결재창으로

                        transaction.replace(R.id.infoFrameLayout, paymFrg).commitAllowingStateLoss();
                    }else{ //회원가입이 안되있으면.. 첫화면으로 .
                        transaction.replace(R.id.infoFrameLayout, newmember).commitAllowingStateLoss();
                    }
                    break;

            }
            return true;
        }
    }


}