package com.maxwell.taskorganizer.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwell.taskorganizer.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTaskTitle, tvTaskDesc;
    public Button btTaskDelete;
    public LinearLayout llTaskContainer;

    public TaskViewHolder(@NonNull View v) {
        super(v);

        tvTaskTitle = v.findViewById(R.id.tvTaskTitle);
        tvTaskDesc = v.findViewById(R.id.tvTaskDesc);
        btTaskDelete = v.findViewById(R.id.btTaskDelete);
        llTaskContainer = v.findViewById(R.id.llTaskContainer);
    }
}
