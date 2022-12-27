package com.leaf.collegeidleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.collegeidleapp.activities.home.MainActivity;
import com.leaf.collegeidleapp.bean.User;
import com.leaf.collegeidleapp.util.UserDbHelper;

import java.util.LinkedList;

/**
 * 登入介面Activity類
 *
 */
public class LoginActivity extends AppCompatActivity {

    EditText EtStuNumber,EtStuPwd;
    private String username;

    LinkedList<User> users = new LinkedList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //找到控制件
        TextView tvRegister = findViewById(R.id.tv_register);
        //跳轉註冊介面
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        // 找到編輯框控制件
        EtStuNumber = findViewById(R.id.et_username);
        EtStuPwd = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.btn_login);
        // 登入
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"登入成功!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                username = EtStuNumber.getText().toString();
                bundle.putString("username",username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button btnClear = findViewById(R.id.btn_clear);
        // 清除EditText內容.
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtStuNumber.setText("");
                EtStuPwd.setText("");
            }
        });

        Button btnLogout = findViewById(R.id.btn_logout);
        // 退出程式
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

}
