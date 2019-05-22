package com.maxwell.taskorganizer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maxwell.taskorganizer.auth.LoginActivity;
import com.maxwell.taskorganizer.utils.DialogManager;
import com.maxwell.taskorganizer.utils.PreferencesManager;

public abstract class BaseActivity extends AppCompatActivity {
    public PreferencesManager pm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pm = new PreferencesManager(this);
    }

    public void dialogEmptyFields(){
        DialogManager dialogManager = new DialogManager(BaseActivity.this, "Error!", "Debe completar todos los campos.");
        Dialog dialog = dialogManager.buildDialog();
        dialog.show();
    }

    public void dialogLoginFail(){
        DialogManager dialogManager = new DialogManager(BaseActivity.this, "Error!", "El usuario o la contraseña son inválidos.");
        Dialog dialog = dialogManager.buildDialog();
        dialog.show();
    }

    public void dialogUserExists(){
        DialogManager dialogManager = new DialogManager(BaseActivity.this, "Usuario ya existe!", "El usuario ingresado ya esta registrado.");
        Dialog dialog = dialogManager.buildDialog();
        dialog.show();
    }

    public void dialogUsersClear(){
        DialogManager dialogManager = new DialogManager(BaseActivity.this, "Usuarios borrados!", "Los usuarios fueron borrados con éxito.");
        Dialog dialog = dialogManager.buildDialog();
        dialog.show();
    }

    public void dialogUserCreated(){
        DialogManager dialogManager = new DialogManager(BaseActivity.this, "Usuario Registrado!", "El usuario ha sido registrado con éxito.");
        Dialog dialog = dialogManager.buildDialog();
        dialog.show();

        dialogManager.getBtDialog().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
