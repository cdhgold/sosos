package com.cdhgold.goodman.Fragm;

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
import com.cdhgold.goodman.util.PreferenceManager;
import com.cdhgold.goodman.util.SetMember;

import java.util.concurrent.FutureTask;


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
        if("p_member".equals(skuId)){ // 회원가입결제
            view = inflater.inflate(R.layout.mem_pay, container, false);
        }else{ // 아이템 결제
            view = inflater.inflate(R.layout.activity_main, container, false);
        }

        //최초 앱설치한사람은 회원가입요청
        btn = (ImageButton)view.findViewById(R.id.memPay);
        btn.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.memPay: // 회원가입 결제
//BillingManager bill = new BillingManager(getActivity());
//bill.setProd("p_member");

                String inApp = "OK" ;
                //inApp = PreferenceManager.getString(getContext(), "inApp");
                if(!"OK".equals(inApp)){// 회원등록 : 결제실패시 등록취소
                    // 결제실패 update
                    PreferenceManager.setString(getContext(), "pay","F");
                }else{
                    // 결제성공 update
                    PreferenceManager.setString(getContext(), "pay","S");
                }
                SetMember setMem = new SetMember(getContext(),"UP");
                FutureTask futureTask = new FutureTask(setMem); // 비동기
                Thread thread = new Thread(futureTask);
                thread.start();
                String ret = "";
                try {
                    ret = (String) futureTask.get(); // 결과
                    if ("OK".equals(ret)) {// 결제여부에 따라 next화면이동, 또는 결재창으로 이동
                        MoldFragment oldmember = new MoldFragment();
                        ((MainActivity)getActivity()).replaceFragment(oldmember);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }

}