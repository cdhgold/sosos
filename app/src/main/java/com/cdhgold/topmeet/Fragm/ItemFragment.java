package com.cdhgold.topmeet.Fragm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdhgold.topmeet.MainActivity;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.PreferenceManager;
import com.cdhgold.topmeet.util.SetItem;
import com.cdhgold.topmeet.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  아이템구매
    Shoes.                      신발 $20  P01
    Wristwatch                  시계 $30  P02
    Jewelry                     반지 $50  P03
    Necklace                    목걸이 $50 P04
    Car                         자동차 $100 P05
    Book                        책 $1 P06
    Volunteer activity          봉사활동 $1 P07
    Vegetable                   채식 $1  P08
    using public transportation 대중교통이용 $1 P09
 */
public class ItemFragment extends Fragment  implements View.OnClickListener {

    private View view;
    ImageButton itemBtn  ;
    ImageView p01;
    ImageView p02;
    ImageView p03;
    ImageView p04;
    ImageView p05;
    ImageView p06;
    ImageView p07;
    ImageView p08;
    ImageView p09;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_main, container, false);

        //아이템구매 버튼
        itemBtn = (ImageButton)view.findViewById(R.id.itemBtn);
        itemBtn.setOnClickListener(this);
        p01 = (ImageView)view.findViewById(R.id.prod01);
        p01.setOnClickListener(this);
        p02 = (ImageView)view.findViewById(R.id.prod02);
        p02.setOnClickListener(this);
        p03 = (ImageView)view.findViewById(R.id.prod03);
        p03.setOnClickListener(this);
        p04 = (ImageView)view.findViewById(R.id.prod04);
        p04.setOnClickListener(this);
        p05 = (ImageView)view.findViewById(R.id.prod05);
        p05.setOnClickListener(this);
        p06 = (ImageView)view.findViewById(R.id.prod06);
        p06.setOnClickListener(this);
        p07 = (ImageView)view.findViewById(R.id.prod07);
        p07.setOnClickListener(this);
        p08 = (ImageView)view.findViewById(R.id.prod08);
        p08.setOnClickListener(this);
        p09 = (ImageView)view.findViewById(R.id.prod09);
        p09.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        String sprod="", item = "";
        switch (view.getId()) {
            case R.id.prod01://신발 $20
                item = "p01";
                sprod = "Do you want to buy it( Shoes: $20 )?" ;
                break;
            case R.id.prod02://시계 $30
                item = "p02";
                sprod = "Do you want to buy it( Wristwatch: $30 )?" ;
                break;
            case R.id.prod03://반지 $50
                item = "p03";
                sprod = "Do you want to buy it( Jewelry: $50 )?" ;
                break;
            case R.id.prod04://목걸이 $50
                item = "p04";
                sprod = "Do you want to buy it( Necklace: $50 )?" ;
                break;
            case R.id.prod05://자동차 $100
                item = "p05";
                sprod = "Do you want to buy it( Car: $100 )?" ;
                break;
            case R.id.prod06://책 $1
                item = "p06";
                sprod = "Do you want to buy it( Book: $1 )?" ;
                break;
            case R.id.prod07://봉사활동 $1
                item = "p07";
                sprod = "Do you want to buy it( Volunteer : $1 )?" ;
                break;
            case R.id.prod08://채식		 $1
                item = "p08";
                sprod = "Do you want to buy it( Vegetable: $1 )?" ;
                break;
            case R.id.prod09://대중교통이용	 $1
                item = "p09";
                sprod = "Do you want to buy it( using public transportation: $1 )?" ;
                break;


        }

        setPurchase(item,sprod);
    }
    //구매
    public void setPurchase(String item, String prod){
        PreferenceManager.setString(getContext(),"item", item);
        new AlertDialog.Builder(getContext())
                .setTitle("PURCHASE")
                .setMessage(prod)
                .setIcon(R.drawable.icon)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // yes
                        String prod = PreferenceManager.getString(getContext(),"item");
                        SetItem callable = new SetItem(getContext() , prod);
                        FutureTask futureTask = new FutureTask(callable);
                        Thread thread = new Thread(futureTask);
                        thread.start();
                        try {
                            String ret = (String)futureTask.get();
                            //db처리정상이면 결제
                            if("OK".equals(ret)){
                                //BillingManager bill = new BillingManager(getActivity());
                                //bill.setProd("p_member");
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // no

                    }})
                .show();

    }

}