package com.cdhgold.topmeet.Fragm;

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

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.Util;


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
                // 공지, 회원가입은 100달러입니다. 앱운영은 개인사정상 언제든지 중단될수 있습니다.
                // 진행하시겠습니까?
                String alim = "The membership is 100 dollars.\n ";
                alim +=" App operation can be suspended \n at any time  due to personal circumstances.";
                alim +="\n Would you like to proceed?";
                new AlertDialog.Builder(getContext())
                .setTitle("NOTICE!!! ")
                .setMessage(alim)
                .setIcon(R.drawable.icon)
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