package com.leaf.collegeidleapp.activities.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.activities.orders.MyBuyActivity;
import com.leaf.collegeidleapp.activities.orders.MyOrderActivity;
import com.leaf.collegeidleapp.activities.myInfo.PersonalCenterActivity;
import com.leaf.collegeidleapp.activities.orders.ReviewCommodityActivity;
import com.leaf.collegeidleapp.adapter.AllCommodityAdapter;
import com.leaf.collegeidleapp.activities.addProduct.AddCommodityActivity;
import com.leaf.collegeidleapp.bean.Commodity;
import com.leaf.collegeidleapp.bean.Order;
import com.leaf.collegeidleapp.util.CommodityDbHelper;
import com.leaf.collegeidleapp.util.MyOrderDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面活动类
 *
 */
public class MainActivity extends AppCompatActivity {

    ListView lvAllCommodity;
    List<Commodity> allCommodities = new ArrayList<>();
    ImageButton ibLearning,ibElectronic,ibDaily,ibSports;

    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局
        setContentView(R.layout.activity_main);
        lvAllCommodity = findViewById(R.id.lv_all_commodity);
        dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getApplicationContext());
        allCommodities = dbHelper.readAllCommodities();
        adapter.setData(allCommodities);
        // 设置Viewlist的适配器
        lvAllCommodity.setAdapter(adapter);
        // 获取上一个页面的存储对象
        final Bundle bundle = this.getIntent().getExtras();
        final String Uid = bundle.getString("username");

        final TextView tvStuNumber = findViewById(R.id.tv_student_number);
        String str = "";
        if (bundle != null) {
            // 取数据
            str = "欢迎" + bundle.getString("username") + ",你好!";

        }
        tvStuNumber.setText(str);
        //当前登录的学生账号
        final String stuNum = tvStuNumber.getText().toString().substring(2, tvStuNumber.getText().length() - 4);
Log.d("MainActivity测试用户id",stuNum);

        //跳转到添加物品界面
        ImageButton IbAddProduct = findViewById(R.id.ib_add_product);
        IbAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCommodityActivity.class);
                if (bundle != null) {
                    //获取学生学号
                    bundle.putString("user_id", stuNum);
                    bundle.putString("uid",bundle.getString("username"));//把uid传到下一个控件
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        //跳转到个人中心界面
        ImageButton IbPersonalCenter = findViewById(R.id.ib_personal_center);
        IbPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                if (bundle != null) {
                    //获取学生学号
                    bundle.putString("username1", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
        //跳转到售出订单页面
        ImageButton ibDingdan = findViewById(R.id.dingdan);
        ibDingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
                if (bundle != null) {
                    //获取学生学号
                    bundle.putString("Uid", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        //跳转到我买到的订单页面
        ImageButton ibBuy = findViewById(R.id.dingdan_buy_ib);
        ibBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBuyActivity.class);
                if (bundle != null) {
                    //获取学生学号
                    bundle.putString("Uid", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });



        //刷新界面
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommodities = dbHelper.readAllCommodities();
                adapter.setData(allCommodities);
                lvAllCommodity.setAdapter(adapter);
            }
        });
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
                bundle1.putString("stuId",stuNum);
                Intent intent = new Intent(MainActivity.this, ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        // 长按购买事件
        lvAllCommodity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                        order.setBuyId(Uid);//buyid是购买方的id，stuNum是当前登录的学生学号
                        order.setDescription(commodity.getDescription());
                        order.setPhone(commodity.getPhone());
                        order.setPicture(commodity.getPicture());
                        order.setPrice(commodity.getPrice());
                        order.setTitle(commodity.getTitle());
                        order.setUserId(commodity.getStuId());//设置物品主人id（卖方id）

                        //order.setUserId(commodity.getStuId());//设置物品主人id

                        myOrderDbHelper.addMyOrder(order);
                        //删除商品
                        dbHelper.deleteMyCommodity(commodity.getTitle(),commodity.getDescription(),commodity.getPrice());
                        Toast.makeText(MainActivity.this,"购买成功!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });


        //点击不同的类别,显示不同的商品信息
        ibLearning = findViewById(R.id.ib_learning_use);
        ibElectronic = findViewById(R.id.ib_electric_product);
        ibDaily = findViewById(R.id.ib_daily_use);
        ibSports = findViewById(R.id.ib_sports_good);
        final Bundle bundle2 = new Bundle();
        //study_goods
        ibLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",1);
                bundle2.putString("Uid",Uid);
                Intent intent = new Intent(MainActivity.this, CommodityTypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        //electronic_products
        ibElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",2);
                bundle2.putString("Uid",Uid);
                Intent intent = new Intent(MainActivity.this,CommodityTypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        //life_goods
        ibDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",3);
                bundle2.putString("Uid",Uid);

                Log.d("这是生活用品:",Uid);

                Intent intent = new Intent(MainActivity.this,CommodityTypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        //sports_goods
        ibSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",4);
                bundle2.putString("Uid",Uid);
                Intent intent = new Intent(MainActivity.this,CommodityTypeActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
    }

}