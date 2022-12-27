package com.leaf.collegeidleapp.activities.myInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.collegeidleapp.R;
import com.leaf.collegeidleapp.bean.User;
import com.leaf.collegeidleapp.util.UserDbHelper;

import java.util.LinkedList;

/**
 * 修改密碼活動類
 */
public class ModifyPwdActivity extends AppCompatActivity {

    TextView tvStuNumber;
    EditText etOriginPwd,etNewPwd,etConfirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        //取消事件
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStuNumber = findViewById(R.id.tv_stu_number);
        tvStuNumber.setText(this.getIntent().getStringExtra("stu_number"));
        etOriginPwd = findViewById(R.id.et_original_pwd);
        etNewPwd = findViewById(R.id.et_new_pwd);
        etConfirmPwd = findViewById(R.id.et_confirm_new_pwd);
        Button btnModify = findViewById(R.id.btn_modify_pwd);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先保證輸入合法
                if(CheckInput()) {
                    String stuNumber = tvStuNumber.getText().toString();
                    UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(),UserDbHelper.DB_NAME,null,1);
                    LinkedList<User> users = dbHelper.readUsers();
                    for(User user : users) {
                        //首先找到用戶名
                        if(user.getUsername().equals(stuNumber)) {
                            if(!etOriginPwd.getText().toString().equals(user.getPassword())) {
                                //提示初始密碼輸入錯誤
                                Toast.makeText(getApplicationContext(),"初始密碼輸入錯誤!",Toast.LENGTH_SHORT).show();
                            }else {
                                //執行修改密碼操作
                                user.setPassword(etNewPwd.getText().toString());
                                boolean flag = dbHelper.updateUser(tvStuNumber.getText().toString(),etNewPwd.getText().toString());
                                if(flag) {
                                    Toast.makeText(getApplicationContext(),"修改密碼成功!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"修改密碼失敗!",Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }

    //判斷輸入的合法性
    public boolean CheckInput() {
        String OriginalPwd = etOriginPwd.getText().toString();
        String NewPwd = etNewPwd.getText().toString();
        String NewConfirmPwd = etConfirmPwd.getText().toString();
        if(OriginalPwd.trim().equals("")) {
            Toast.makeText(this,"原始密碼不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(NewPwd.trim().equals("")) {
            Toast.makeText(this,"新密碼不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(NewConfirmPwd.trim().equals("")) {
            Toast.makeText(this,"確認新密碼不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!NewPwd.trim().equals(NewConfirmPwd.trim())) {
            Toast.makeText(this,"兩次密碼輸入不一致!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
