package com.cdhgold.topmeet.Fragm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.PreferenceManager;
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
        String mcnt = PreferenceManager.getString(getContext() , "mcnt"); // 100명 정원확인
        if("100".equals(mcnt.trim())){
            String tmp = "The membership quota(100) is closed.\n\n Please use it next time. \n\n Thank you!";
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Top1%Blind Date!");
            builder.setIcon(R.drawable.icon);
            builder.setMessage(tmp);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.finishAffinity(getActivity() ) ; // 액티비티를 종료하고
                            System.exit(0); // 프로세스를 종료
                        }
                    });
            builder.show();

        }

            return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btnMember: // 회원가입
                // 공지, 회원가입은 10달러입니다. 앱운영은 개인사정상 언제든지 중단될수 있습니다.
                // 진행하시겠습니까?
                String alim = "The membership(100) is 10 dollars.\n ";
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