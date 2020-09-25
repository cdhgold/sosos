package com.cdhgold.goodman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.cdhgold.goodman.Fragm.MemlFragment;
import com.cdhgold.goodman.util.GetSetUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.cdhgold.goodman.Fragm.MnewFragment;
import com.cdhgold.goodman.Fragm.MoldFragment;
import com.cdhgold.goodman.util.PreferenceManager;

/*
회원가입여부에따라 화면 분기
 */

public class MainActivity extends AppCompatActivity  implements MemInterf  {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MnewFragment newmember = new MnewFragment();
    private MoldFragment oldmember = new MoldFragment();
    private MemlFragment emlFrg = new MemlFragment();
    private String[] kids = {"k02"
            ,"k03"
            ,"k04"
            ,"k05"
            ,"k06"
            ,"k07"
            ,"k08"
            ,"k09"
            ,"k10"
            ,"k11"
            ,"k12"
            ,"k16"
            ,"k17"
            ,"k18"
            ,"k19"
            ,"k20"
            ,"k22"
            ,"k24"
            ,"k25"
            ,"k27"
            ,"k30"
            ,"k37"
            ,"k38"
            ,"k39"
            ,"k40"
            ,"k42"
            ,"k46"
            ,"k47"
            ,"k48"
            ,"k50"
            ,"k52"
            ,"k54"
            ,"k57"
            ,"k58"
            ,"k59"
            ,"k61"
            ,"k62"
            ,"k63" };
    private String[] fkids = {	"k01"
            ,"k09"
            ,"k13"
            ,"k14"
            ,"k15"
            ,"k21"
            ,"k23"
            ,"k26"
            ,"k28"
            ,"k29"
            ,"k31"
            ,"k32"
            ,"k33"
            ,"k34"
            ,"k35"
            ,"k36"
            ,"k41"
            ,"k43"
            ,"k44"
            ,"k45"
            ,"k49"
            ,"k51"
            ,"k53"
            ,"k55"
            ,"k56"
            ,"k60" };

    //private PayFragment paymFrg = new PayFragment("p_member"); // 회비 결제
    private FragmentTransaction transaction ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_fragm);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view); // 하단메뉴
        bottomNav.setOnNavigationItemSelectedListener(new ItemSelectListener());
        transaction =  getSupportFragmentManager().beginTransaction();
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
        GetSetUtil gsutil = new GetSetUtil();
        String nickname = gsutil.selectMemb(eml, getApplicationContext()) ;

        if(!"".equals(nickname) && nickname != null   ) {
            replaceFragmentThread(oldmember); // 맴버화면
        }
        else   { // 비회원
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
    //animation set
    public String setAnimate(ImageView imgv ,String gbn) {
        int frameNumber= 0;
        AnimationDrawable drawable =
                (AnimationDrawable) imgv.getBackground();
        if (drawable != null && drawable.isRunning()) { // 동작중일 경우
            // 멈추기
            drawable.stop();
            Drawable currentFrame = drawable.getCurrent() ;
            Drawable checkFrame;
            for (int i = 0; i < drawable.getNumberOfFrames(); i++) {
                checkFrame = drawable.getFrame(i);
                if (checkFrame == currentFrame) {
                    frameNumber = i;// 선택 캐릭터
                    break;
                }
            }

        } else { // 멈춰있는 경우
            // 동작 개시하기
             // 애니메이션 동작 개시

                    drawable.start();


        }
        String ret = "";
        if(gbn.equals("kid")){
            ret = kids[frameNumber];
        }else{
            ret = fkids[frameNumber];
        }
 Log.d("choice==>",ret);
        return ret;
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
                    String eml = PreferenceManager.getString(getApplicationContext() , "eml" );

                    if(!"".equals(eml)   ) {// 회원결제가 성공이면
                        transaction.replace(R.id.infoFrameLayout, oldmember).commitAllowingStateLoss();
                    }else{ //회원가입이 안되있으면.. 첫화면으로 .
                        transaction.replace(R.id.infoFrameLayout, newmember).commitAllowingStateLoss();
                    }
                    break;

            }
            return true;
        }
    }


}