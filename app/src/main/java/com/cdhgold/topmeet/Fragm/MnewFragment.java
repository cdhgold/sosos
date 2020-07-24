package com.cdhgold.topmeet.Fragm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.R;


/*
  신규 회원가입 fragment
 */
public class MnewFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageButton btn  ;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);

        //최초 앱설치한사람은 회원가입요청
        btn = (ImageButton)view.findViewById(R.id.btnMember);
        btn.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btnMember: // 회원가입

                break;

        }
    }

}