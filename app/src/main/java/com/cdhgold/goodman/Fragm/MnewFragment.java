package com.cdhgold.goodman.Fragm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.goodman.MainActivity;
import com.cdhgold.goodman.R;
import com.cdhgold.goodman.util.Util;


/*
  신규 회원가입 fragment
 */
public class MnewFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageButton btn  ;
    private MregiFragment mregif = new MregiFragment(); // 등록화면


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
                // I'm part of the universe, and I've come for a while as a human. Let's be good people.
                String alim = "I'm part of the universe.\n ";
                alim +=" I've come for a while as a human. \n Let's be good people.";
                alim +="\n Would you like to proceed?";
                new AlertDialog.Builder(getContext())
                .setTitle("NOTICE!!! ")
                .setMessage(alim)
                .setIcon(R.drawable.member_btn)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        ((MainActivity)getActivity()).replaceFragment(mregif);
                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 취소시 처리 로직
                        Util.showAlim("Thank you!",getContext());
                    }})
                .show();




                break;

        }
    }

}