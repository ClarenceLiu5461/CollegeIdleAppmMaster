package com.leaf.collegeidleapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.bean.Commodity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 主界面所有商品列表的適配器
 */
public class AllCommodityAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<Commodity> commodities = new ArrayList<>();
    //對每一個item保存其位置
    HashMap<Integer,View> location = new HashMap<>();

    public AllCommodityAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Commodity> commodities) {
        this.commodities = commodities;
        notifyDataSetChanged();//不用重新刷新Activity，通知Activity更新ListView
    }

    // 顯示列表個數
    @Override
    public int getCount() {
        return commodities.size();
    }

    @Override
    public Object getItem(int position) {
        return commodities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(location.get(position) == null){
            convertView = layoutInflater.inflate(R.layout.layout_all_commodity,null);
            Commodity commodity = (Commodity) getItem(position);
            holder = new ViewHolder(convertView,commodity);
            //保存view的位置position
            location.put(position,convertView);
            convertView.setTag(holder);
        }else{
            convertView = location.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    //定義靜態類,定義每一個item的所有元素
    static class ViewHolder {
        ImageView ivCommodity;
        TextView tvTitle,tvType,tvDescription,tvPrice;

        public ViewHolder(View itemView,Commodity commodity) {
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivCommodity = itemView.findViewById(R.id.iv_commodity);
            tvTitle.setText(commodity.getTitle());
            tvDescription.setText(commodity.getDescription());
            tvPrice.setText(String.valueOf(commodity.getPrice())+"元");
            tvType.setText(commodity.getCategory());
            byte[] picture = commodity.getPicture();
            //從位元組數組中解碼生成不可變的位圖
            //public static Bitmap decodeByteArray(byte[] data, int offset, int length)
            Bitmap img = BitmapFactory.decodeByteArray(picture,0,picture.length);
            ivCommodity.setImageBitmap(img);
        }
    }
}
