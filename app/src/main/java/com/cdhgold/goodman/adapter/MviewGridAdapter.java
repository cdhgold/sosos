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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/*
회원보기용 어댑터 3열그리드
fg,fm,mg,mm, 아이템구매합계액
 */
public class MviewGridAdapter extends RecyclerView.Adapter<MviewGridAdapter.MembViewHolder> {
    private Context context;
    private MemInterf listener;
    private ImageView imageView;
    private String eml = "";
    private ArrayList<MemberVo> data;
    private int pos = 0;
    public MviewGridAdapter(Context context, ArrayList<MemberVo> data, MemInterf listener ){
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MviewGridAdapter.MembViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.member_card, viewGroup, false);
        return new MviewGridAdapter.MembViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MviewGridAdapter.MembViewHolder membViewHolder, int i) {
        MemberVo vo = data.get(i);
        pos = vo.getSeq() ;
        eml = vo.geteml();
        String nicknm = vo.getNickname();
        String tamt = vo.getTotItem();  // 총구매금액
        String gender = vo.getGender(); // mg, mm, fg, fm
        int kid = context.getResources().getIdentifier(gender, "drawable", context.getPackageName());
        membViewHolder.productImage.setImageResource(kid); //gender

        membViewHolder.productPrice.setText(tamt);
        membViewHolder.productName.setText(nicknm);

    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.size();
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

    public class MembViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        private Context context;

        public MembViewHolder(@NonNull View itemView) {
            super(itemView);
            //notifyItemRangeChanged(0, data.size());
            productImage = itemView.findViewById(R.id.imageView);
            productPrice = itemView.findViewById(R.id.totAmt);
            productName = itemView.findViewById(R.id.nickNm);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition() ;

            if (pos != RecyclerView.NO_POSITION) {
                MemberVo vo = data.get(pos);
                String eml = vo.geteml();
                listener.onItemClick(eml ); // 상세보기
            }
        }
    }

}
