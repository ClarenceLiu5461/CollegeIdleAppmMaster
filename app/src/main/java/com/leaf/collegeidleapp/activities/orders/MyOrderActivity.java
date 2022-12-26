package com.leaf.collegeidleapp.activities.orders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.adapter.MyOrderAdapter;

import com.leaf.collegeidleapp.bean.Order;

import com.leaf.collegeidleapp.util.MyOrderDbHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 已售出的订单信息
 */
public class MyOrderActivity extends AppCompatActivity {

    ListView lvMyOrder;
    List<Order> myOrders = new ArrayList<>();
    TextView tvStuId;

    MyOrderDbHelper dbHelper;
    //CommodityDbHelper commodityDbHelper;
    MyOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Log.d("MyOrderA","++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //返回
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tvStuId = findViewById(R.id.tv_stuId);
        tvStuId.setText(this.getIntent().getStringExtra("Uid"));
        lvMyOrder = findViewById(R.id.lv_my_order);
        dbHelper = new MyOrderDbHelper(getApplicationContext(),MyOrderDbHelper.DB_NAME,null,1);
        //myOrders = dbHelper.readMyCollections(tvStuId.getText().toString());
        myOrders = dbHelper.readMyOrder(tvStuId.getText().toString());

        adapter = new MyOrderAdapter(getApplicationContext());
        adapter.setData(myOrders);
        lvMyOrder.setAdapter(adapter);
        //设置长按删除事件
        lvMyOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyOrderActivity.this);
                builder.setTitle("提示:").setMessage("确定删除此收藏商品吗?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Order order = (Order) adapter.getItem(position);
                        //删除收藏商品项
                        dbHelper.deleteMyOrder(order.getTitle(),order.getDescription(),order.getPrice());
                        Toast.makeText(MyOrderActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return false;
            }
        });
        //页面刷新
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOrders = dbHelper.readMyOrder(tvStuId.getText().toString());
                adapter.setData(myOrders);
                lvMyOrder.setAdapter(adapter);
            }
        });
    }
}