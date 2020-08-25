package com.cdhgold.topmeet.Fragm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.PreferenceManager;


/*
  등록 멤버  : 처음화면
 */
public class MoldFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageView fbtn  ;
    ImageView mbtn  ;
    ImageView ibtn  ;
    ImageView myImg ; // 내이미지
    TextView myNm ; // 내별명
    TextView myAmt ;    // 내총구매금액
    String eml = "";
    String nickname 	= "";
    String info 	= "";
    String age 		= "";
    String gender 	= "";
    String amt 	= ""; // 총 아이템구매금액

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mem_old, container, false);

        //최초 앱설치한사람은 회원가입요청
        fbtn = (ImageView)view.findViewById(R.id.female);   // 여성회원보기
        fbtn.setOnClickListener(this);
        mbtn = (ImageView)view.findViewById(R.id.male);     // 남성회원보기
        mbtn.setOnClickListener(this);
        ibtn = (ImageView)view.findViewById(R.id.item);     // item 구매
        ibtn.setOnClickListener(this);
        myImg =   (ImageView)view.findViewById(R.id.myImg);
        myNm =   (TextView)view.findViewById(R.id.myNm);
        myAmt =   (TextView)view.findViewById(R.id.myAmt);
        eml = PreferenceManager.getString(getContext(), "eml");
        nickname = PreferenceManager.getString(getContext(), "nickname");
        info = PreferenceManager.getString(getContext(), "info");
        age = PreferenceManager.getString(getContext(), "age");
        gender = PreferenceManager.getString(getContext(), "gender");
        amt = PreferenceManager.getString(getContext(), "amt");
        amt = amt == null ? "0": amt;
        myNm.setText(nickname+" ");
        myAmt.setText(amt);

        if("mg".equals(gender)){
            myImg.setImageResource(R.drawable.mg);
        }
        else if("mm".equals(gender)){
            myImg.setImageResource(R.drawable.mm);
        }
        else if("fg".equals(gender)){
            myImg.setImageResource(R.drawable.fg);
        }
        else if("fm".equals(gender)){
            myImg.setImageResource(R.drawable.fm);
        }
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