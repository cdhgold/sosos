package com.cdhgold.topmeet.Fragm;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.R;


/*
  신규 회원가입 등록 화면 fragment
  회원가입화면, 남1(mm근육질),남2(mg 일반),
  여1(fm 글래머),여2(fg 일반), nickname, 연령대(20대,30대,40대,50대,60대,70대 ) , introduce, deviceid

 */
public class MregiFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageView fm  ; // 여성근육질
    ImageView fg  ; // 여성일반
    ImageView mm  ; // 남성근육질
    ImageView mg  ;// 남성일반


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.member_reg, container, false);

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
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.fm: // 회원가입
                fm.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fg.setColorFilter(null);
                mm.setColorFilter(null);
                mg.setColorFilter(null);

                break;
            case R.id.fg: // 회원가입
                fg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fm.setColorFilter(null);
                mm.setColorFilter(null);
                mg.setColorFilter(null);

                break;
            case R.id.mm: // 회원가입
                mm.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fg.setColorFilter(null);
                fm.setColorFilter(null);
                mg.setColorFilter(null);

                break;
            case R.id.mg: // 회원가입
                mg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.LIGHTEN);
                fg.setColorFilter(null);
                fm.setColorFilter(null);
                mm.setColorFilter(null);


                break;
        }
    }

}