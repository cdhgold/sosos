package com.cdhgold.topmeet.Fragm;

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
  등록 멤버 처음화면
 */
public class MoldFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageView fbtn  ;
    ImageView mbtn  ;
    ImageView ibtn  ;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mem_old, container, false);

        //최초 앱설치한사람은 회원가입요청
        fbtn = (ImageView)view.findViewById(R.id.female);
        fbtn.setOnClickListener(this);
        mbtn = (ImageView)view.findViewById(R.id.male);
        mbtn.setOnClickListener(this);
        ibtn = (ImageView)view.findViewById(R.id.item);
        ibtn.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.female: // 여성회원보기

                break;
            case R.id.male: // 남성회원보기

                break;
            case R.id.item: // 아이템구매

                break;
        }
    }

}