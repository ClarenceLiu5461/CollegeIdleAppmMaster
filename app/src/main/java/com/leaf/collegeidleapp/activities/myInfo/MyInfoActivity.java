package com.leaf.collegeidleapp.activities.myInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.activities.myInfo.ModifyInfoActivity;
import com.leaf.collegeidleapp.bean.Student;
import com.leaf.collegeidleapp.util.StudentDbHelper;

import java.util.LinkedList;

/**
 * 我的個人信息活動類
 */
public class MyInfoActivity extends AppCompatActivity {

    TextView tvStuName,tvStuMajor,tvStuPhone,tvStuQq,tvStuAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        Button btnBack = findViewById(R.id.btn_back);
        //返回點擊事件,銷毀當前界面
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //從bundle中獲取用戶賬號/學號
        final TextView tvUserNumber = findViewById(R.id.tv_stu_number);
        tvUserNumber.setText(this.getIntent().getStringExtra("stu_number1"));
        tvStuName = findViewById(R.id.tv_stu_name);
        tvStuMajor = findViewById(R.id.tv_stu_major);
        tvStuPhone = findViewById(R.id.tv_stu_phone);
        tvStuAddress = findViewById(R.id.tv_stu_address);
        StudentDbHelper dbHelper = new StudentDbHelper(getApplicationContext(),StudentDbHelper.DB_NAME,null,1);
        LinkedList<Student> students = dbHelper.readStudents(tvUserNumber.getText().toString());
        if(students != null) {
            for(Student student : students) {
                tvStuName.setText(student.getStuName());
                tvStuMajor.setText(student.getStuMajor());
                tvStuPhone.setText(student.getStuPhone());
                tvStuAddress.setText(student.getStuAddress());
            }
        }else {
            tvStuName.setText("暫未填寫");
            tvStuMajor.setText("暫未填寫");
            tvStuPhone.setText("暫未填寫");
            tvStuAddress.setText("暫未填寫");
        }
        Button btnModifyInfo = findViewById(R.id.btn_modify_info);
        //跳轉到修改用戶信息界面
        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number2",tvUserNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //刷新按鈕點擊事件
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentDbHelper dbHelper1 = new StudentDbHelper(getApplicationContext(),StudentDbHelper.DB_NAME,null,1);
                LinkedList<Student> students = dbHelper1.readStudents(tvUserNumber.getText().toString());
                if(students != null) {
                    for(Student student : students) {
                        tvStuName.setText(student.getStuName());
                        tvStuMajor.setText(student.getStuMajor());
                        tvStuPhone.setText(student.getStuPhone());
                        tvStuAddress.setText(student.getStuAddress());
                    }
                }
            }
        });

    }
}
