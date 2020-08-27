package com.cdhgold.topmeet.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdhgold.topmeet.MemInterf;
import com.cdhgold.topmeet.R;
import com.cdhgold.topmeet.util.MemberVo;
import com.cdhgold.topmeet.util.MsgVo;
import com.cdhgold.topmeet.util.ProdVo;
import com.cdhgold.topmeet.util.Util;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
쪽지 보기
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {
    private Context context;
    private MemInterf listener;
    private ImageView imageView;
    private String eml = "";
    private MemberVo data;          // 회원정보
    private List<MsgVo> mvo;  // 구매내역
    private String sfromEml = "";
    private int pos = 0;
    public MsgAdapter(Context context, ArrayList<MsgVo> list, MemInterf listener ){
        this.context = context;
        this.mvo = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MsgAdapter.MsgViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_list, viewGroup, false);
        return new MsgAdapter.MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.MsgViewHolder msgViewHolder, int i) {
        MsgVo vo = mvo.get(i);
        //String eml = vo.getEml();
        sfromEml = vo.getFromEml();
        String msg = vo.getMessage();
        String regdt = vo.getRegdt();
        String nm = vo.getNicknm();
        msgViewHolder.cont.setText(msg);
        msgViewHolder.regdt.setTextSize(20);
        msgViewHolder.regdt.setText(regdt+"  : "+nm);

    }

    @Override
    public int getItemCount() {
        if(mvo == null)
            return 0;
        else
            return mvo.size();
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

    public class MsgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView cont;
        TextView fromEml;
        TextView regdt;
        ImageView imgReply;

        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);

            cont = itemView.findViewById(R.id.cont);
            regdt = itemView.findViewById(R.id.regdt);
            imgReply = itemView.findViewById(R.id.imgReply);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition() ;
            if (pos != RecyclerView.NO_POSITION) {
  Log.d("ms fromEml",sfromEml);
            }
        }
    }

}
