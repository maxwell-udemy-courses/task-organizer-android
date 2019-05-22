package com.maxwell.taskorganizer.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxwell.taskorganizer.R;

public class TaskDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        TextView tvTaskTitle = v.findViewById(R.id.tvTaskTitle);
        TextView tvTaskDesc = v.findViewById(R.id.tvTaskDesc);

        tvTaskTitle.setText(getArguments().getString("task_title"));
        tvTaskDesc.setText(getArguments().getString("task_desc"));
    }
}
