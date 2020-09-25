package com.cdhgold.goodman.Fragm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.goodman.MainActivity;
import com.cdhgold.goodman.MemInterf;
import com.cdhgold.goodman.R;
import com.cdhgold.goodman.util.DelMember;
import com.cdhgold.goodman.util.PreferenceManager;
import com.cdhgold.goodman.util.SetMember;
import com.cdhgold.goodman.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  신규 회원가입 등록 화면 fragment
  캐릭터를 선택한다 .

 */
public class MregiFragment extends Fragment  implements View.OnClickListener, MemInterf {

    private View view;
    ImageView kid  ;  // 남성
    ImageView fkid  ; // 여성

    EditText nickNm ;
    EditText eml ;      // pk
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
        eml = (EditText)view.findViewById(R.id.eml);
        info = (EditText)view.findViewById(R.id.info);
        age = (RadioGroup) view.findViewById(R.id.age);
        age.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        memIns = (Button) view.findViewById(R.id.memIns);
        memIns.setOnClickListener(this);
        //최초 앱설치한사람은 회원가입요청
        kid = (ImageView)view.findViewById(R.id.kid);
        kid.setOnClickListener(this);

        fkid = (ImageView)view.findViewById(R.id.fkid);
        fkid.setOnClickListener(this);

        return view;
    }
    // 연령대
    // twenties. thirties. forties,fifties,sixties,seventies,
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.a10){
                sage = "Teenage";
            }
            else if(i == R.id.a20){
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
        int frameNumber = 0;
        switch (view.getId()) {

            case R.id.memIns: // 등록
                String seml = eml.getText().toString();
                String nm =  nickNm.getText().toString();
                String sinfo =  info.getText().toString();
                PreferenceManager.setString( getContext(),"nickNm", nm);
                PreferenceManager.setString( getContext(),"info", sinfo);
                PreferenceManager.setString( getContext(),"age", sage);
                PreferenceManager.setString( getContext(),"gender", gender);
                PreferenceManager.setString( getContext(),"eml", seml);

                memIns();
                break;
            case R.id.kid: // male
                gender = ((MainActivity)getActivity()).setAnimate(kid, "kid");

                break;
            case R.id.fkid: // female
                gender = ((MainActivity)getActivity()).setAnimate(fkid, "fkid");

                break;

        }
    }
    /*
    등록
     */
    public void memIns(){
        if("".equals(gender) || "".equals(sage) ){
            Util.showAlim("Please Gender and Age !", getContext());
            return;
        }
        SetMember setMem = new SetMember(getContext(),"NEW");
        FutureTask futureTask = new FutureTask(setMem);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            String ret = (String)futureTask.get(); // 결과
            if("OK".equals(ret)) {// 가입후 화면이동
                MoldFragment oldmember = new MoldFragment();
                ((MainActivity)getActivity()).replaceFragment(oldmember);
            }else if("err".equals(ret)){
                Util.showAlim("Please NickName and introduce!", getContext());
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

    @Override
    public void onItemClick(String eml) {

    }
}