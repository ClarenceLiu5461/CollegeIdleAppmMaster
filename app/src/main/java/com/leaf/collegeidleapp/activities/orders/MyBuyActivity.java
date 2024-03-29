package com.leaf.collegeidleapp.activities.orders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
 * 已買到的訂單信息
 */
public class MyBuyActivity extends AppCompatActivity {

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

        //去除標題欄位
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        Log.d("MyBuyA","++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //返回
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //設置顯示 ：購買記錄
        TextView textView = findViewById(R.id.tv_my_order);
        textView.setText("購買記錄");

        tvStuId = findViewById(R.id.tv_stuId);
        tvStuId.setText(this.getIntent().getStringExtra("Uid"));
        lvMyOrder = findViewById(R.id.lv_my_order);
        dbHelper = new MyOrderDbHelper(getApplicationContext(),MyOrderDbHelper.DB_NAME,null,1);
        //myOrders = dbHelper.readMyCollections(tvStuId.getText().toString());
        myOrders = dbHelper.readMyBuy(tvStuId.getText().toString());

        adapter = new MyOrderAdapter(getApplicationContext());
        adapter.setData(myOrders);
        lvMyOrder.setAdapter(adapter);
        //設置長按刪除事件
        lvMyOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyBuyActivity.this);
                builder.setTitle("提示:").setMessage("確定刪除此收藏商品嗎?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Order order = (Order) adapter.getItem(position);
                        //刪除收藏商品項
                        dbHelper.deleteMyOrder(order.getTitle(),order.getDescription(),order.getPrice());
                        Toast.makeText(MyBuyActivity.this,"刪除成功!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return false;
            }
        });
        //頁面刷新
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOrders = dbHelper.readMyBuy(tvStuId.getText().toString());
                adapter.setData(myOrders);
                lvMyOrder.setAdapter(adapter);
            }
        });
    }
}
