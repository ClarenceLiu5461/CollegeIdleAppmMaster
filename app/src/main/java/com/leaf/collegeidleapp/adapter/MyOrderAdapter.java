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

import com.leaf.collegeidleapp.bean.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 我的出售订单Adapter类
 */
public class MyOrderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<Order> orders = new ArrayList<>();

    HashMap<Integer, View> location = new HashMap<>();

    public MyOrderAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(location.get(position) == null){
            convertView = layoutInflater.inflate(R.layout.layout_my_collection,null);//这里要改layout，默认使用colloction布局了
            Order order = (Order) getItem(position);
            holder = new ViewHolder(convertView,order);
            location.put(position,convertView);
            convertView.setTag(holder);
        }else{
            convertView = location.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    //定义静态类,包含每一个item的所有元素
    static class ViewHolder {
        ImageView ivCommodity;
        TextView tvTitle,tvDescription,tvPrice,tvPhone;

        public ViewHolder(View itemView,Order oder) {
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            ivCommodity = itemView.findViewById(R.id.iv_commodity);
            tvTitle.setText(oder.getTitle());
            tvDescription.setText(oder.getDescription());
            tvPrice.setText(String.valueOf(oder.getPrice())+"元");
            tvPhone.setText(oder.getPhone());
            byte[] picture = oder.getPicture();
            Bitmap img = BitmapFactory.decodeByteArray(picture,0,picture.length);
            ivCommodity.setImageBitmap(img);
        }
    }
}
