package com.cdhgold.topmeet.Fragm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.GetMember;
import com.cdhgold.topmeet.util.PreferenceManager;
import com.cdhgold.topmeet.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  email( pk )  check 화면
  앱 시작시 email (PreferenceManager)  여부에따라 화면표시,
 */
public class MemlFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageButton mnewBtn  ;
    ImageButton memberBtn  ;
    TextView eml  ;

    private MregiFragment mregif = new MregiFragment(); // 등록화면
    private MoldFragment oldmember = new MoldFragment();
    private PayFragment paymFrg = new PayFragment("p_member"); // 회비 결제

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eml_main, container, false);

        //최초 앱설치한사람은 회원가입요청
        mnewBtn = (ImageButton)view.findViewById(R.id.mnew);    // 신규
        mnewBtn.setOnClickListener(this);
        memberBtn = (ImageButton)view.findViewById(R.id.member); // 기존멤버
        memberBtn.setOnClickListener(this);
        eml = (TextView)view.findViewById(R.id.eml);    // pk

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.member: // 회원정보확인후 MoldFragment화면으로 이동 ( 멤버 초기화면 )
                String seml = eml.getText().toString();
                String ret = "";
                if("".equals(seml)){
                    Util.showAlim("Please enter Email!",getContext() );
                }
                PreferenceManager.setString(getContext(), "eml", seml);
                GetMember callable = new GetMember(getContext(),"ONE","O");
                FutureTask futureTask = new FutureTask(callable);
                Thread thread = new Thread(futureTask);
                thread.start();
                try {
                    ret = (String)futureTask.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("thread", "2===========end " +ret );


                // 결제가 안됐으면 , 회원결재창으로 이동...
                String nickname = "",payment = "",amt = "", eml = "";
                if (!"null".equals(ret) && !"".equals(ret)) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(ret);
                        nickname = jsonObject.getString("nickname");
                        payment = jsonObject.getString("payment"); // F 면 결재창으로
                        eml = jsonObject.getString("eml"); //
                        amt = jsonObject.getString("amt"); //
                        amt = Util.getComma(amt);
                        if(!"null".equals(eml) ) {
                            PreferenceManager.setString(getContext(), "eml", eml);
                            PreferenceManager.setString(getContext(), "amt", amt);
                            PreferenceManager.setString(getContext(), "nickname", nickname);
                            PreferenceManager.setString(getContext(), "payment", payment);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("thread", "1===========" + nickname);
                    if(!"null".equals(eml) && "S".equals(payment) ) {
                        ((MainActivity)getActivity()).replaceFragment(oldmember);
                    }
                    else if(!"null".equals(eml) && "F".equals(payment) ) { // 회비결제화면으로
                        ((MainActivity)getActivity()).replaceFragment(paymFrg);
                    }else{
                        //회원결재창
                        //BillingManager bill = new BillingManager(this);
                        //bill.setProd("p_member");
                        ((MainActivity)getActivity()).replaceFragment(mregif);

                    }
                }
                break;
            case R.id.mnew: // 회원가입 화면으로 이동
                ((MainActivity)getActivity()).replaceFragment(mregif);
                break;
        }
    }

}