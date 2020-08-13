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
  결제 fragment
 */
public class PayFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageButton btn  ;
    private MregiFragment mregif = new MregiFragment(); // 등록화면
    String skuId = ""; // 상품id
    public PayFragment(String gbn){
        this.skuId = gbn;

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if("p_member".equals(skuId)){
            view = inflater.inflate(R.layout.mem_pay, container, false);
        }else{
            view = inflater.inflate(R.layout.activity_main, container, false);
        }

        //최초 앱설치한사람은 회원가입요청
        btn = (ImageButton)view.findViewById(R.id.btnMember);
        btn.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.memPay: // 회원가입 결제


                break;

        }
    }

}