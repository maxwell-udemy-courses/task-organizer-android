package com.maxwell.taskorganizer.auth;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.maxwell.taskorganizer.BaseActivity;
import com.maxwell.taskorganizer.HomeActivity;
import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.auth.models.UserTask;
import com.maxwell.taskorganizer.utils.DialogManager;

import java.util.List;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUserName = findViewById(R.id.etUserName);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btLogin = findViewById(R.id.btLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if(userName.isEmpty() || password.isEmpty()){
                    dialogEmptyFields();
                    return;
                }

                List<UserTask> userTasks = pm.loadUserList();
                boolean correctPassword = false;
                UserTask currentUser = null;

                for(UserTask user : userTasks){
                    if(user.getUserName().equals(userName)){
                        if(user.getPassword().equals(password)){
                            correctPassword = true;
                            currentUser = user;
                        } else {
                            correctPassword = false;
                        }
                    }
                }

                if(correctPassword){
                    if(currentUser != null)
                        pm.setCurrentUser(currentUser);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    dialogLoginFail();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
