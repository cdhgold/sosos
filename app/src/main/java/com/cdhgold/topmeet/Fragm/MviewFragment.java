package com.cdhgold.topmeet.Fragm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdhgold.topmeet.MemInterf;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.adapter.MviewGridAdapter;
import com.cdhgold.topmeet.util.GetMember;
import com.cdhgold.topmeet.util.MemberVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  등록 멤버 보기 화면 ( 남, 여 )
  각 회원별 총구매내역을 보여준다. ( Total item purchase amount
 */
public class MviewFragment extends Fragment  implements View.OnClickListener, MemInterf {

    private View view;
    private String memGbn = "";
    private ArrayList<MemberVo> data;
    public  MviewFragment(String gender){
        this.memGbn = gender; // 성별 구분 M , F, 남성 여성으로 구분해서 가져온다 .
    }
    private RecyclerView mview;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memb_view, container, false);
        showProduct(); //회원정보를 3열로 보여준다.

        return view;
    }
    private  void showProduct()  {
        // 회원정보를 가져온다.
        String rjson = "";
        GetMember callable = new GetMember(getContext(),"ALL");
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

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);

        mview = view.findViewById(R.id.memView);
        mview.setLayoutManager(layoutManager);
         try {
             JSONObject jsonObject = new JSONObject(rjson);
             // nickname = jsonObject.getString("nickname");
             JSONArray jsonarray = jsonObject.getJSONArray(rjson);
             for(int i = 0; i< jsonarray.length() ;i ++){
                // jsonarray.get(i)
             }
             MviewGridAdapter adapter = new MviewGridAdapter(getContext(), data, this );
             mview.setAdapter(adapter);
         }catch(Exception e){
             e.printStackTrace();
         }

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

    @Override
    public void memDel() {

    }

    @Override
    public void onItemClick(String deviceid) {

    }
}