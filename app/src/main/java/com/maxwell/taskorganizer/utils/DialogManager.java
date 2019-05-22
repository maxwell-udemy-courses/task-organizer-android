package com.maxwell.taskorganizer.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maxwell.taskorganizer.R;

public class DialogManager {
    private Context ctx;
    private String title, desc;
    private Button btDialog;
    private TextView tvDialogTitle, tvDialogDesc;

    public DialogManager(Context ctx, String title, String desc) {
        this.ctx = ctx;
        this.title = title;
        this.desc = desc;
    }

    public Dialog buildDialog(){
        final Dialog dialog = new Dialog(ctx);

        dialog.setContentView(R.layout.dialog_task);

        tvDialogTitle = dialog.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(title);

        tvDialogDesc = dialog.findViewById(R.id.tvDialogDesc);
        tvDialogDesc.setText(desc);

        btDialog = dialog.findViewById(R.id.btDialog);

        btDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public Button getBtDialog() {
        return btDialog;
    }
}
