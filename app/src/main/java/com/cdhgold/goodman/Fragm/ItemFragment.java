package com.cdhgold.goodman.Fragm;

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

import com.cdhgold.goodman.MainActivity;
import com.cdhgold.goodman.R;
import com.cdhgold.goodman.util.PreferenceManager;
import com.cdhgold.goodman.util.SetItem;
import com.cdhgold.goodman.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
  goodman 되기 item : item 선택은 하루에 한번만 ... 무료
1 Do it yourself		 	60
2 helping parents         100
3 No cursing               100
4 Volunteer activity      90
5 Use of public transportation     80
6 Reading books           80
7 Vegetable               60
8 Exercise                80
9 helping others          100
10  To curse                -70
11  To torment              -100
12  Cigarette               -60
13  Alcohol                 -60
14  Drug                    -100
15  a meat diet             -60
16  Fight                   -100
17  a bad idea              -60
18  Discrimination          -80

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
    ImageView p10;
    ImageView p11;
    ImageView p12;
    ImageView p13;
    ImageView p14;
    ImageView p15;
    ImageView p16;
    ImageView p17;
    ImageView p18;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_main, container, false);

        //아이템구매
        p01 = (ImageView)view.findViewById(R.id.p01);
        p01.setOnClickListener(this);
        p02 = (ImageView)view.findViewById(R.id.p02);
        p02.setOnClickListener(this);
        p03 = (ImageView)view.findViewById(R.id.p03);
        p03.setOnClickListener(this);
        p04 = (ImageView)view.findViewById(R.id.p04);
        p04.setOnClickListener(this);
        p05 = (ImageView)view.findViewById(R.id.p05);
        p05.setOnClickListener(this);
        p06 = (ImageView)view.findViewById(R.id.p06);
        p06.setOnClickListener(this);
        p07 = (ImageView)view.findViewById(R.id.p07);
        p07.setOnClickListener(this);
        p08 = (ImageView)view.findViewById(R.id.p08);
        p08.setOnClickListener(this);
        p09 = (ImageView)view.findViewById(R.id.p09);
        p09.setOnClickListener(this);
        p10 = (ImageView)view.findViewById(R.id.p10);
        p10.setOnClickListener(this);
        p11 = (ImageView)view.findViewById(R.id.p11);
        p11.setOnClickListener(this);
        p12 = (ImageView)view.findViewById(R.id.p12);
        p12.setOnClickListener(this);
        p13 = (ImageView)view.findViewById(R.id.p13);
        p13.setOnClickListener(this);
        p14 = (ImageView)view.findViewById(R.id.p14);
        p14.setOnClickListener(this);
        p15 = (ImageView)view.findViewById(R.id.p15);
        p15.setOnClickListener(this);
        p16 = (ImageView)view.findViewById(R.id.p16);
        p16.setOnClickListener(this);
        p17 = (ImageView)view.findViewById(R.id.p17);
        p17.setOnClickListener(this);
        p18 = (ImageView)view.findViewById(R.id.p18);
        p18.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        String sprod="", item = "";
        switch (view.getId()) {
            case R.id.p01:
                item = "p01";
                //sprod = "Do you want to buy it( Shoes: $20 )?" ;
                break;
            case R.id.p02:
                item = "p02";
                break;
            case R.id.p03:
                item = "p03";
                break;
            case R.id.p04:
                item = "p04";
                break;
            case R.id.p05:
                item = "p05";
                break;
            case R.id.p06:
                item = "p06";
                break;
            case R.id.p07:
                item = "p07";
                break;
            case R.id.p08:
                item = "p08";
                break;
            case R.id.p09:
                item = "p09";
                break;
            case R.id.p10:
                item = "p10";
                break;
            case R.id.p11:
                item = "p11";
                break;
            case R.id.p12:
                item = "p12";
                break;
            case R.id.p13:
                item = "p13";
                break;
            case R.id.p14:
                item = "p14";
                break;
            case R.id.p15:
                item = "p15";
                break;
            case R.id.p16:
                item = "p16";
                break;
            case R.id.p17:
                item = "p17";
                break;
            case R.id.p18:
                item = "p18";
                break;
        }

        setPurchase(item,sprod);
    }
    //구매
    public void setPurchase(String item, String prod){
        PreferenceManager.setString(getContext(),"item", item);
        new AlertDialog.Builder(getContext())
                .setTitle("GoodMan")
                .setMessage(prod)
                .setIcon(R.drawable.member_btn)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // yes
                        String prod = PreferenceManager.getString(getContext(),"item");
                        SetItem callable = new SetItem(getContext() , prod);
                        FutureTask futureTask = new FutureTask(callable);
                        Thread thread = new Thread(futureTask);
                        thread.start();
                        try {
                            String ret = (String)futureTask.get(); // NOT_TODAY( 하루한번 만 가능 )
                            if("NOT_TODAY".equals(ret)){//There is only one activity a day.
                                Util.showAlim("There is only one activity a day.", getContext());
                                MoldFragment old = new MoldFragment();
                                ((MainActivity)getActivity()).replaceFragment(old);
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