package com.cdhgold.topmeet.Fragm;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.MemInterf;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.BillingManager;
import com.cdhgold.topmeet.util.DelMember;
import com.cdhgold.topmeet.util.PreferenceManager;
import com.cdhgold.topmeet.util.SetMember;
import com.cdhgold.topmeet.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  신규 회원가입 등록 화면 fragment
  회원가입화면, 남1(mm근육질),남2(mg 일반),
  여1(fm 글래머),여2(fg 일반), nickname, 연령대(20대,30대,40대,50대,60대,70대 ) , introduce, deviceid

 */
public class MregiFragment extends Fragment  implements View.OnClickListener, MemInterf {

    private View view;
    ImageView fm  ; // 여성근육질
    ImageView fg  ; // 여성일반
    ImageView mm  ; // 남성근육질
    ImageView mg  ;// 남성일반
    EditText nickNm ;
    RadioGroup age  ;
    EditText info   ;
    Button memIns   ;
    String gender = "";
    String sage = "";
    String sinfo = "";
    String snickNm = "";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.member_reg, container, false);
        nickNm = (EditText)view.findViewById(R.id.nickNm);
        info = (EditText)view.findViewById(R.id.info);
        age = (RadioGroup) view.findViewById(R.id.age);
        age.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        memIns = (Button) view.findViewById(R.id.memIns);
        memIns.setOnClickListener(this);
        //최초 앱설치한사람은 회원가입요청
        fm = (ImageView)view.findViewById(R.id.fm);
        fm.setOnClickListener(this);
        fm.setColorFilter(null);

        fg = (ImageView)view.findViewById(R.id.fg);
        fg.setOnClickListener(this);
        fg.setColorFilter(null);

        mm = (ImageView)view.findViewById(R.id.mm);
        mm.setOnClickListener(this);
        mm.setColorFilter(null);

        mg = (ImageView)view.findViewById(R.id.mg);
        mg.setOnClickListener(this);
        mg.setColorFilter(null);
        return view;
    }
    // 연령대
    // twenties. thirties. forties,fifties,sixties,seventies,
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.a20){
                sage = "twenties";
            }
            else if(i == R.id.a30){
                sage = "thirties";
            }
            else if(i == R.id.a40){
                sage = "forties";
            }
            else if(i == R.id.a50){
                sage = "fifties";
            }
            else if(i == R.id.a60){
                sage = "sixties";
            }
            else if(i == R.id.a70){
                sage = "seventies";
            }
        }
    };

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {

            case R.id.memIns: // 등록
                String nm =  nickNm.getText().toString();
                String sinfo =  info.getText().toString();
                PreferenceManager.setString( getContext(),"nickNm", nm);
                PreferenceManager.setString( getContext(),"info", sinfo);
                PreferenceManager.setString( getContext(),"age", sage);
                PreferenceManager.setString( getContext(),"gender", gender);

                memIns();
                break;
            case R.id.fm: // 회원가입
                fm.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fg.setColorFilter(null);
                mm.setColorFilter(null);
                mg.setColorFilter(null);
                gender = "fm";
                break;
            case R.id.fg: // 회원가입
                fg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fm.setColorFilter(null);
                mm.setColorFilter(null);
                mg.setColorFilter(null);
                gender = "fg";
                break;
            case R.id.mm: // 회원가입
                mm.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fg.setColorFilter(null);
                fm.setColorFilter(null);
                mg.setColorFilter(null);
                gender = "mm";
                break;
            case R.id.mg: // 회원가입
                mg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fg.setColorFilter(null);
                fm.setColorFilter(null);
                mm.setColorFilter(null);
                gender = "mg";

                break;
        }
    }
    /*
    등록
     */
    public void memIns(){
        SetMember setMem = new SetMember(getContext());
        FutureTask futureTask = new FutureTask(setMem);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            String ret = (String)futureTask.get(); // 결과
            if("OK".equals(ret)) {// 가입후 화면이동
                //MoldFragment oldmember = new MoldFragment();
                //((MainActivity)getActivity()).replaceFragment(oldmember);
                Util.showAlim("Welcome!", getContext());
                //결제 $100 후 화면 이동. oldmember
                BillingManager bill = new BillingManager((MainActivity) getActivity(), this);
                bill.purchase("member");
            }else if("err".equals(ret)){
                Util.showAlim("Please NickName, introduce!", getContext());
            }else{// alert 차후에 시도하세요
                Util.showAlim("Please try again later!",getContext());
                MnewFragment newmember = new MnewFragment();
                ((MainActivity)getActivity()).replaceFragment(newmember);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    /*
    결제실패시 등록취소
     */
    @Override
    public void memDel() {
        DelMember delMem = new DelMember(getContext());
        FutureTask futureTask = new FutureTask(delMem);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            String ret = (String)futureTask.get(); // 결과
            if("OK".equals(ret)) {// 가입후 화면이동
                //MoldFragment oldmember = new MoldFragment();
                //((MainActivity)getActivity()).replaceFragment(oldmember);
                Util.showAlim("Please try again(Payment failure).", getContext());

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}