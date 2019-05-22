package com.maxwell.taskorganizer.auth;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.maxwell.taskorganizer.BaseActivity;
import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.auth.models.UserTask;
import com.maxwell.taskorganizer.utils.DialogManager;

import java.util.List;

public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstName = findViewById(R.id.etFirstName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etUserName = findViewById(R.id.etUserName);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btRegister = findViewById(R.id.btRegister);
        Button btResetUser = findViewById(R.id.btResetUser);

        btResetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.resetUserList();

                dialogUsersClear();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty()){
                    dialogEmptyFields();
                    return;
                }

                List<UserTask> userTaskList = pm.loadUserList();
                boolean userExits = false;

                for (UserTask user : userTaskList){
                    if(user.getUserName().equals(userName)){
                        userExits = true;
                        break;
                    }
                }

                if(userExits){
                    dialogUserExists();
                    return;
                }

                UserTask user = new UserTask(firstName, lastName, userName, password);
                pm.saveUser(user);

                dialogUserCreated();
            }
        });
    }
}
