package com.cdhgold.goodman.Fragm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.goodman.MainActivity;
import com.cdhgold.goodman.R;
import com.cdhgold.goodman.util.GetSetUtil;
import com.cdhgold.goodman.util.PreferenceManager;


/*
  등록 멤버  : 처음화면
 */
public class MoldFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageView fbtn  ;
    ImageView mbtn  ;
    ImageView ibtn  ;
    ImageView msg  ;    // 내쪽지보기
    ImageView myImg ;   // 내이미지
    TextView myNm ;     // 내별명
    TextView myAmt ;    // 내총구매금액
    TextView numOfMemb ;    // 멤버 총인원
    String eml = "";
    String nickname 	= "";
    String info 	= "";
    String age 		= "";
    String gender 	= "";
    String amt 	= ""; // 총 아이템구매금액
    String mcnt = "Members : "; //멤버총수
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mem_old, container, false);
        GetSetUtil gsutil = new GetSetUtil();
        eml = PreferenceManager.getString(getContext(), "eml");
        String nickname = gsutil.selectMemb(eml, getContext() ) ; // 서버 재 조회

        //최초 앱설치한사람은 회원가입요청
        fbtn = (ImageView)view.findViewById(R.id.female);   // 여성회원보기
        fbtn.setOnClickListener(this);
        mbtn = (ImageView)view.findViewById(R.id.male);     // 남성회원보기
        mbtn.setOnClickListener(this);
        ibtn = (ImageView)view.findViewById(R.id.item);     // item 구매
        ibtn.setOnClickListener(this);
        msg = (ImageView)view.findViewById(R.id.msg);     // 쪽지보기
        msg.setOnClickListener(this);
        myImg =   (ImageView)view.findViewById(R.id.myImg);
        myNm =   (TextView)view.findViewById(R.id.myNm);
        myAmt =   (TextView)view.findViewById(R.id.myAmt);
        numOfMemb =   (TextView)view.findViewById(R.id.numOfMemb);  // 맴버 총수

        nickname = PreferenceManager.getString(getContext(), "nickname");
        info = PreferenceManager.getString(getContext(), "info");
        age = PreferenceManager.getString(getContext(), "age");
        gender = PreferenceManager.getString(getContext(), "gender");
        amt = PreferenceManager.getString(getContext(), "amt");
        amt = amt == null ? "0": amt;
        myNm.setText("Hi,My name is "+nickname+" ");
        myAmt.setText("My point : "+amt);
        String tmcnt = PreferenceManager.getString(getContext(), "mcnt");
        numOfMemb.setText(mcnt+tmcnt);        // 멤버총수
        int kid = this.getResources().getIdentifier(gender, "drawable", getActivity().getPackageName());
        myImg.setImageResource(kid); //gender
        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.female: // 여성회원보기
                MviewFragment fview = new MviewFragment("F");
                ((MainActivity)getActivity()).replaceFragment(fview);
                break;
            case R.id.male: // 남성회원보기
                MviewFragment mview = new MviewFragment("M");
                ((MainActivity)getActivity()).replaceFragment(mview);
                break;
            case R.id.msg: //  내쪽지보기
                MsgFragment msgview = new MsgFragment();
                ((MainActivity)getActivity()).replaceFragment(msgview);
                break;
            case R.id.item: // 아이템구매

/*
신발 $20 , 		P01
시계 $300, 		P02
반지 $500, 		P03
목걸이 $500 		P04
자동차 $1000 		P05
책 $1			 P06
봉사활동 $1		 P07
채식		 $1	 P08
대중교통이용	 $1	 P09

 */
                ItemFragment item = new ItemFragment();
                ((MainActivity)getActivity()).replaceFragment(item);
                break;
        }
    }

}