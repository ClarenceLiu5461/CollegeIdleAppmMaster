package com.leaf.collegeidleapp.activities.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.activities.orders.ReviewCommodityActivity;
import com.leaf.collegeidleapp.adapter.AllCommodityAdapter;
import com.leaf.collegeidleapp.bean.Commodity;
import com.leaf.collegeidleapp.bean.Order;
import com.leaf.collegeidleapp.util.CommodityDbHelper;
import com.leaf.collegeidleapp.util.MyOrderDbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 不同类型商品信息的活动类
 */
public class CommodityTypeActivity extends AppCompatActivity {

    TextView tvCommodityType;
    ListView lvCommodityType;
    ListView lvAllCommodity;//按照不同类别列出商品（根据status来）
    List<Commodity> commodities = new LinkedList<>();

    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_type);

        final Bundle bundle = this.getIntent().getExtras();
        final String uid = bundle.getString("Uid");

        //final String uid =  this.getIntent().getIntExtra("Uid",0)+"";//获取用户id，用来进行
Log.d("CommodityT测试Uid", uid);

//根据不同的状态显示不同的界面
        int status = this.getIntent().getIntExtra("status",0);
        lvAllCommodity = findViewById(R.id.list_commodity);


//为每一个item设置点击事件
        lvAllCommodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commodity commodity = (Commodity) lvAllCommodity.getAdapter().getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",position);
                bundle1.putByteArray("picture",commodity.getPicture());
                bundle1.putString("title",commodity.getTitle());
                bundle1.putString("description",commodity.getDescription());
                bundle1.putFloat("price",commodity.getPrice());
                bundle1.putString("phone",commodity.getPhone());
                bundle1.putString("stuId",uid+"");
                Intent intent = new Intent(CommodityTypeActivity.this, ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });




        //返回事件
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCommodityType = findViewById(R.id.tv_type);
        lvCommodityType = findViewById(R.id.list_commodity);
        dbHelper = new CommodityDbHelper(getApplicationContext(),CommodityDbHelper.DB_NAME,null,1);
        adapter = new AllCommodityAdapter(getApplicationContext());

        if(status == 1) {
            tvCommodityType.setText("学习用品");
        }else if(status == 2) {
            tvCommodityType.setText("电子用品");
        }else if(status == 3) {
            tvCommodityType.setText("生活用品");
        }else if(status == 4) {
            tvCommodityType.setText("体育用品");
        }
        //根据不同类别显示不同的商品信息
        commodities = dbHelper.readCommodityType(tvCommodityType.getText().toString());
        adapter.setData(commodities);
        lvCommodityType.setAdapter(adapter);
        // 长按购买事件
        final ListView  lvAllCommodity = findViewById(R.id.list_commodity);
        lvAllCommodity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CommodityTypeActivity.this);
                builder.setTitle("提示:").setMessage("确定购买此商品吗?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Commodity commodity = (Commodity) adapter.getItem(position);
                        //添加商品到订单表（order）
                        MyOrderDbHelper myOrderDbHelper = new MyOrderDbHelper(getApplicationContext(), MyOrderDbHelper.DB_NAME, null, 1);
                        Order order = new Order();
                        order.setBuyId(String.valueOf(uid));//buyid是购买方的id，stuNum是当前登录的学生学号

                        Log.d("CommondityT测试Uid", String.valueOf(uid));
                        order.setDescription(commodity.getDescription());
                        order.setPhone(commodity.getPhone());
                        order.setPicture(commodity.getPicture());
                        order.setPrice(commodity.getPrice());
                        order.setTitle(commodity.getTitle());
                        order.setUserId(commodity.getStuId());//设置物品主人id
                        myOrderDbHelper.addMyOrder(order);
                        //删除商品
                        dbHelper.deleteMyCommodity(commodity.getTitle(),commodity.getDescription(),commodity.getPrice());
                        Toast.makeText(CommodityTypeActivity.this,"购买成功!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });

        //刷新界面
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Commodity> allCommodities = dbHelper.readAllCommodities();
                adapter.setData(allCommodities);
                lvAllCommodity.setAdapter(adapter);
            }
        });
    }
}
