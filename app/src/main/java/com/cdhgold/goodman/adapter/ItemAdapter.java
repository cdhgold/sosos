package com.cdhgold.goodman.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdhgold.goodman.MemInterf;
import com.cdhgold.goodman.R;
import com.cdhgold.goodman.util.MemberVo;
import com.cdhgold.goodman.util.ProdVo;
import com.cdhgold.goodman.util.Util;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/*
회원 상세보기
구매내역리스트
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context context;
    private MemInterf listener;
    private ImageView imageView;
    private String eml = "";
    private MemberVo data;          // 회원정보
    private ArrayList<ProdVo> pvo;  // 구매내역

    private int pos = 0;
    public ItemAdapter(Context context, ArrayList<ProdVo> list, MemInterf listener ){
        this.context = context;
        this.pvo = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder ItemViewHolder, int i) {
        ProdVo vo = pvo.get(i);
        String amt = vo.getAmt();
        String prod = vo.getProd(); // 이미지로 표현
        String regdt = vo.getRegdt();
        String prodNm = "";
        /*
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
        ItemViewHolder.productPrice.setText(amt);
        prodNm = Util.getItemNm(prod);
        ItemViewHolder.productName.setText(prodNm);
        ItemViewHolder.productRegdt.setText(regdt);
        if("p01".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p01);
        }
        else if("p02".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p02);
        }
        else if("p03".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p03);
        }
        else if("p04".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p04);
        }
        else if("p05".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p05);
        }
        else if("p06".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p06);
        }
        else if("p07".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p07);
        }
        else if("p08".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p08);
        }
        else if("p09".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p09);
        }
        else if("p10".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p10);
        }
        else if("p11".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p11);
        }
        else if("p12".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p12);
        }
        else if("p13".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p13);
        }
        else if("p14".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p14);
        }
        else if("p15".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p15);
        }
        else if("p16".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p16);
        }
        else if("p17".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p17);
        }
        else if("p18".equals(prod)){
            ItemViewHolder.prodImg.setImageResource(R.drawable.p18);
        }

    }

    @Override
    public int getItemCount() {
        if(pvo == null)
            return 0;
        else
            return pvo.size();
    }



    // url 이미지를 set drawadble
    private Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable drawa = Drawable.createFromStream(is, "src name");
            return drawa;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }


    public Drawable getImage(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Drawable drawable = new BitmapDrawable(null, bitmap);
        return drawable;
    }
/*
    Shoes.                      신발 $2  P01
    Wristwatch                  시계 $30  P02
    Jewelry                     반지 $50  P03
    Necklace                    목걸이 $50 P04
    Car                         자동차 $100 P05
    Book                        책 $1 P06
    Volunteer activity          봉사활동 $1 P07
    Vegetable                   채식 $1  P08
    using public transportation 대중교통이용 $1 P09
 */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView prodImg;  // item 품목

        TextView productName;
        TextView productPrice;
        TextView productRegdt;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            prodImg = itemView.findViewById(R.id.prodImg);
            productPrice = itemView.findViewById(R.id.prodAmt);
            productName = itemView.findViewById(R.id.prodNm);
            productRegdt= itemView.findViewById(R.id.prodRegdt);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition() ;

            if (pos != RecyclerView.NO_POSITION) {

            }
        }
    }

}
