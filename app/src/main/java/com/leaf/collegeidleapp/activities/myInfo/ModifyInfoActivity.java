package com.leaf.collegeidleapp.activities.myInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.bean.Student;
import com.leaf.collegeidleapp.util.StudentDbHelper;

import java.util.LinkedList;

/**
 * 修改個人信息Activity類
 */
public class ModifyInfoActivity extends AppCompatActivity {

    EditText etStuName,etMajor,etPhone,etQq,etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        Button btnBack = findViewById(R.id.btn_back);
        //返回按鈕點擊事件
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //利用bundle傳遞學號
        final TextView tvStuNumber = findViewById(R.id.tv_stu_number);
        tvStuNumber.setText(this.getIntent().getStringExtra("stu_number2"));
        etStuName = findViewById(R.id.et_stu_name);
        etMajor = findViewById(R.id.et_stu_major);
        etPhone = findViewById(R.id.et_stu_phone);
        etAddress = findViewById(R.id.et_stu_address);
        StudentDbHelper dbHelper = new StudentDbHelper(getApplicationContext(),StudentDbHelper.DB_NAME,null,1);
        LinkedList<Student> students = dbHelper.readStudents(tvStuNumber.getText().toString());
        //如果查找到的學生信息不為空
        if(students != null) {
            for(Student student : students) {
                etStuName.setText(student.getStuName());
                etMajor.setText(student.getStuMajor());
                etPhone.setText(student.getStuPhone());
                etQq.setText(student.getStuQq());
                etAddress.setText(student.getStuAddress());
            }
        }
        Button btnSaveInfo = findViewById(R.id.btn_save_info);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判斷輸入不為空
                if(CheckInput()) {
                    StudentDbHelper dbHelper = new StudentDbHelper(getApplicationContext(),StudentDbHelper.DB_NAME,null,1);
                    Student student = new Student();
                    student.setStuNumber(tvStuNumber.getText().toString());
                    student.setStuName(etStuName.getText().toString());
                    student.setStuMajor(etMajor.getText().toString());
                    student.setStuPhone(etPhone.getText().toString());
                    student.setStuAddress(etAddress.getText().toString());
                    dbHelper.saveStudent(student);
                    Toast.makeText(getApplicationContext(),"用戶信息保存成功!",Toast.LENGTH_SHORT).show();
                    //銷毀當前界面
                    finish();
                }
            }
        });
    }

    //檢查輸入是否為空
    public boolean CheckInput() {
        String StuName = etStuName.getText().toString();
        String StuMajor = etMajor.getText().toString();
        String StuPhone = etPhone.getText().toString();
        String StuAddress = etAddress.getText().toString();
        if(StuName.trim().equals("")) {
            Toast.makeText(this,"姓名不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuMajor.trim().equals("")) {
            Toast.makeText(this,"專業不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuPhone.trim().equals("")) {
            Toast.makeText(this,"聯系方式不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuAddress.trim().equals("")) {
            Toast.makeText(this,"地址不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
