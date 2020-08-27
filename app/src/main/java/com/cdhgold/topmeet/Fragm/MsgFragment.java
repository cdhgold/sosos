package com.cdhgold.topmeet.Fragm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.MemInterf;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.adapter.MsgAdapter;
import com.cdhgold.topmeet.adapter.MviewGridAdapter;
import com.cdhgold.topmeet.util.GetMember;
import com.cdhgold.topmeet.util.GetMsg;
import com.cdhgold.topmeet.util.MemberVo;
import com.cdhgold.topmeet.util.MsgVo;
import com.cdhgold.topmeet.util.PreferenceManager;
import com.cdhgold.topmeet.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  내쪽지 보기 (본인 )
 */
public class MsgFragment extends Fragment  implements MemInterf  {

    private View view;
    private String memGbn = "";
    private ArrayList<MsgVo> data = new ArrayList<MsgVo>();

    private RecyclerView mview;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.msg_view, container, false);
        showProduct(); //쪽지보기 content, sender

        return view;
    }
    private  void showProduct()  {
        // 회원정보를 가져온다. ( M, F )
        String rjson = "";
        String eml = PreferenceManager.getString(getContext(),"fromEml") ; // 내 eml
        GetMsg callable = new GetMsg(getContext(),eml );    
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
        mview = view.findViewById(R.id.msgView);
        mview.setLayoutManager(new LinearLayoutManager(getActivity()));// 필수

         try {
             JSONArray jsonarray = new JSONArray(rjson);

             for(int i = 0; i< jsonarray.length() ;i ++){
                 JSONObject jsonObj = (JSONObject) jsonarray.get(i);
                 eml = (String) jsonObj.get("eml");
                 String message = (String) jsonObj.get("message");
                 String fromEml = (String) jsonObj.get("fromEml");
                 String regdt = (String) jsonObj.get("regdt");
                 String nicknm = (String) jsonObj.get("nicknm");

                 MsgVo vo = new MsgVo();
                 vo.setFromEml(fromEml);
                 vo.setMessage(message);
                 vo.setRegdt(regdt);
                 vo.setNicknm(nicknm);
                 data.add(vo);
             }

             MsgAdapter adapter = new MsgAdapter(getContext(), data, this );
             mview.setAdapter(adapter);
         }catch(Exception e){
             e.printStackTrace();
         }

    }


    @Override
    public void memDel() {

    }
/*
상세보기로 가기 ( MviewGridAdapter에서 call )
 */
    @Override
    public void onItemClick(String eml) {
        MdetailFragment mdetailFrg = new MdetailFragment(eml);
        ((MainActivity)getActivity()).replaceFragment(mdetailFrg);

    }
}