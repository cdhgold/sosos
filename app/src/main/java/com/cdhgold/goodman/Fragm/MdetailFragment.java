package com.cdhgold.goodman.Fragm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdhgold.goodman.MemInterf;
import com.cdhgold.goodman.MsgActivity;
import com.cdhgold.goodman.R;
import com.cdhgold.goodman.adapter.ItemAdapter;
import com.cdhgold.goodman.util.GetMember;
import com.cdhgold.goodman.util.MemberVo;
import com.cdhgold.goodman.util.PreferenceManager;
import com.cdhgold.goodman.util.ProdVo;
import com.cdhgold.goodman.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  멤버 상세보기
  멤버, 아이템 리스트 구매내역
  쪽지보내기
 */
public class MdetailFragment extends Fragment  implements View.OnClickListener , MemInterf {

    private View view;
    private String eml = "";
    private MemberVo data = new MemberVo() ;
    public MdetailFragment(String eml){
        this.eml = eml; //  해당멤버의 eml
    }
    private RecyclerView itemList;
    private ImageView myImg;
    private String m_eml = ""; // 회원 eml
    private TextView myNm ;
    private TextView myAge;
    private TextView myAmt;
    private TextView myInfo;
    private TextView myRegdt;
    private ImageButton msgBtn;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mem_detail, container, false);

        myImg = (ImageView)view.findViewById(R.id.myImg);   // 회원이미지
        myNm = (TextView)view.findViewById(R.id.myNm);   //
        myAge = (TextView)view.findViewById(R.id.myAge);   //
        myAmt = (TextView)view.findViewById(R.id.myAmt);   //
        myInfo = (TextView)view.findViewById(R.id.myInfo);   //
        myRegdt = (TextView)view.findViewById(R.id.myRegdt);   //
        msgBtn = (ImageButton)view.findViewById(R.id.msgBtn);   // 쪽지보내기
        msgBtn.setOnClickListener(this);
        showProduct(); //아이템구매 내역을 보여준다

        return view;
    }
    private  void showProduct()  {
        // 회원정보를 가져온다. ( 멤버, 아이템 정보  )
        String rjson = "";
        GetMember callable = new GetMember(getContext(),this.eml, "Detail");
        FutureTask futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            rjson = (String)futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        itemList = view.findViewById(R.id.itemList); // 아이템구매리스트
        itemList.setLayoutManager(new LinearLayoutManager(getActivity()));// 필수


         try {
             JSONObject jObject = new JSONObject(rjson);
             String memJson  = jObject.getString("memb");   // 회원정보
             String itemJson  = jObject.getString("item");  // 아이템구매내역
             JSONObject memObj = new JSONObject(memJson);
             m_eml = (String) memObj.get("eml");
             String m_gender = (String) memObj.get("gender");
             String m_age = (String) memObj.get("age");
             String m_nickname = (String) memObj.get("nickname");
             String m_info = (String) memObj.get("info");
             String m_regdt = (String) memObj.get("regdt");
             String m_amt = (String) memObj.get("amt");
             m_amt = Util.getComma(m_amt);
             int kid = this.getResources().getIdentifier(m_gender, "drawable", getActivity().getPackageName());
             myImg.setImageResource(kid); //gender

            myNm.setText(m_nickname);
            myAge.setText(m_age);
            myAmt.setText(m_amt);
            myInfo.setText(m_info);
            myRegdt.setText(m_regdt);

            data.seteml(m_eml);
            data.setTotItem(m_amt);
            data.setGender(m_gender);
            data.setNickname(m_nickname);
            data.setAge(m_age);
            data.setInfo(m_info);
            data.setRegdt(m_regdt);
             JSONArray jsonarray = new JSONArray(itemJson); // 아이템 배열 prod , amt , regdt
                /*
                신발 $100 , 		P01
                시계 $300, 		    P02
                반지 $500, 		    P03
                목걸이 $500 		    P04
                자동차 $1000 		P05
                책 $1			    P06
                봉사활동 $1		    P07
                채식		 $1	    P08
                대중교통이용	 $1	    P09

                 */

             ArrayList<ProdVo> plist = new ArrayList<ProdVo>();
             for(int i = 0; i< jsonarray.length() ;i ++){
                 JSONObject jsonObj = (JSONObject) jsonarray.get(i);
                 String prod = (String) jsonObj.get("prod");
                 String amt = (String) jsonObj.get("amt");
                 String regdt = (String) jsonObj.get("regdt");
                 amt = Util.getComma(amt);
                 Log.d("prod  ",prod);
                 ProdVo vo = new ProdVo();
                 vo.setProd(prod);
                 vo.setAmt(amt);
                 vo.setRegdt(regdt);
                 plist.add(vo);
                 data.setList(plist);
             }

             ItemAdapter adapter = new ItemAdapter(getContext(), data.getList(), this );
             itemList.setAdapter(adapter); // 구매내역
         }catch(Exception e){
             e.printStackTrace();
         }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.msgBtn: // 쪽지보내기
                //eml  MsgActivity
                String fromEml = PreferenceManager.getString(getContext(), "fromEml");; // 보내는사람 , 나
                String nickname = PreferenceManager.getString(getContext(), "nickname");;

                Intent intent = new Intent(getContext(), MsgActivity.class);

                intent.putExtra("eml",m_eml);        /* 받는사람 eml */
                intent.putExtra("fromEml",fromEml);  /* 앱 로긴한 사람 */
                intent.putExtra("nicknm",nickname);

                startActivity(intent);
                break;

        }
    }

    @Override
    public void memDel() {

    }

    @Override
    public void onItemClick(String eml) {

    }
}