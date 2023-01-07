package com.leaf.collegeidleapp.activities.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
    ImageButton NaviDrawer,Searchbutton,Mapbutton,Fabbutton,ibLearning,ibElectronic,ibDaily,ibSports;

    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 設置佈局
        setContentView(R.layout.activity_main);

        //去除標題欄位
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        lvAllCommodity = findViewById(R.id.lv_all_commodity);
        dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getApplicationContext());
        allCommodities = dbHelper.readAllCommodities();
        adapter.setData(allCommodities);
        // 設置Viewlist的適配器
        lvAllCommodity.setAdapter(adapter);

        //NavigationDrawer


        // 抓取上個頁面的儲存navidrawer對象
        final Bundle bundle = this.getIntent().getExtras();
        final String Uid = bundle.getString("username");

        final TextView tvStuNumber = findViewById(R.id.tv_student_number);
        String str = "";
        if (bundle != null) {
            // 抓取數據
            str = "歡迎" + bundle.getString("username") + ",你好!";

        }
        tvStuNumber.setText(str);
        //目前登入的學生帳號
        final String stuNum = tvStuNumber.getText().toString().substring(2, tvStuNumber.getText().length() - 4);
Log.d("MainActivity測試用戶id",stuNum);

        //跳轉到添加商品介面
        ImageButton IbAddProduct = findViewById(R.id.ib_add_product);
        IbAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCommodityActivity.class);
                if (bundle != null) {
                    //獲取學生學號
                    bundle.putString("user_id", stuNum);
                    bundle.putString("uid",bundle.getString("username"));//把uid傳到下一個控制鍵
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        //跳轉到個人中心介面
        ImageButton IbPersonalCenter = findViewById(R.id.ib_personal_center);
        IbPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                if (bundle != null) {
                    //抓取學生學號
                    bundle.putString("username1", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
        //跳轉到售出訂單介面
        ImageButton ibDingdan = findViewById(R.id.dingdan);
        ibDingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
                if (bundle != null) {
                    //抓取學生學號
                    bundle.putString("Uid", stuNum);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        //跳轉到我買到的訂單頁面
        ImageButton ibBuy = findViewById(R.id.dingdan_buy_ib);
        ibBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBuyActivity.class);
                if (bundle != null) {
                    //抓取學生學號
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
        //設置每個item的點擊事件
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

        // 長按購買事件
        lvAllCommodity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示:").setMessage("確定購買此商品嗎?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Commodity commodity = (Commodity) adapter.getItem(position);
                        //添加商品到訂單表（order）
                        MyOrderDbHelper myOrderDbHelper = new MyOrderDbHelper(getApplicationContext(), MyOrderDbHelper.DB_NAME, null, 1);
                        Order order = new Order();
                        order.setBuyId(Uid);//buyid是買方的id，stuNum是目前登入的學生學號
                        order.setDescription(commodity.getDescription());
                        order.setPhone(commodity.getPhone());
                        order.setPicture(commodity.getPicture());
                        order.setPrice(commodity.getPrice());
                        order.setTitle(commodity.getTitle());
                        order.setUserId(commodity.getStuId());//設置物品主人id（賣方id）

                        //order.setUserId(commodity.getStuId());//設置物品主人id

                        myOrderDbHelper.addMyOrder(order);
                        //刪除商品
                        dbHelper.deleteMyCommodity(commodity.getTitle(),commodity.getDescription(),commodity.getPrice());
                        Toast.makeText(MainActivity.this,"購買成功",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });


        //點擊不同的類別,顯示不同的商品資訊
        NaviDrawer = findViewById(R.id.navidrawer);
        ibLearning = findViewById(R.id.ib_learning_use);
        ibElectronic = findViewById(R.id.ib_electric_product);
        ibDaily = findViewById(R.id.ib_daily_use);
        ibSports = findViewById(R.id.ib_sports_good);
        Searchbutton = findViewById(R.id.searchbutton);
        Mapbutton = findViewById(R.id.mapbutton);
        Fabbutton = findViewById(R.id.fabbutton);

        final Bundle bundle2 = new Bundle();
        //study_goods
        NaviDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",5);
                bundle2.putString("Uid",Uid);
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
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

                Log.d("這是生活用品:",Uid);

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
        //study_goods

        //search button jump to google.com
        Searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        //map button go jump to google map
        Mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Map point based on address
                Uri location = Uri.parse("geo:22.756600, 120.336256");
                Intent intent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(intent);
            }
        });

        //fab jump to profile
        Fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putInt("status",6);
                bundle2.putString("Uid",Uid);
                Intent intent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
    }

}