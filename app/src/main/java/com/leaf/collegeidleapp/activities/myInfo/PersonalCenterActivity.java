package com.leaf.collegeidleapp.activities.myInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leaf.collegeidleapp.LoginActivity;
import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.activities.orders.MyOrderActivity;

/**
 * 個人中心主界面Activity類
 */
public class PersonalCenterActivity extends AppCompatActivity {

    TextView TvStuNumber;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        //去除標題欄位
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //取出登錄時的登錄名
        TvStuNumber = findViewById(R.id.tv_student_number);
        String StuNumber = this.getIntent().getStringExtra("username1");
        TvStuNumber.setText(StuNumber);
        //返回主界面
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //點擊修改密碼按鈕
        final Button btnModifyPwd = findViewById(R.id.btn_modify_password);
        btnModifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyPwdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number",TvStuNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //點擊查看我的發布按鈕
        Button btnMyGoods = findViewById(R.id.btn_my_goods);
        btnMyGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCommodityActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_id",TvStuNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //點擊查看我的收藏按鈕
        Button btnMyCollection = findViewById(R.id.btn_my_collection);
        btnMyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCollectionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stuId",TvStuNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //點擊個人信息按鈕
        Button btnUserInfo = findViewById(R.id.btn_user_info);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number1",TvStuNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        //點擊出售訂單記錄按鈕
        Button btnMyOrder = findViewById(R.id.btn_order);
        btnMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Uid",TvStuNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        //退出登錄按鈕點擊事件
        Button btnLogOut = findViewById(R.id.btn_logout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalCenterActivity.this);
                builder.setTitle("提示:").setMessage("確認退出系統嗎?").setIcon(R.drawable.icon_user).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //跳轉到登錄界面
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }
}
