package com.maxwell.taskorganizer.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.list.models.Task;
import com.maxwell.taskorganizer.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskListFragment extends Fragment implements OnTaskEmpty {
    LinearLayout llNoTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        RecyclerView rvTaskList = v.findViewById(R.id.rvTaskList);

        PreferencesManager pm = new PreferencesManager(getActivity());

        List<Task> tasks = pm.loadTaskList();

        TaskAdapter adapter = new TaskAdapter(tasks, getActivity(), this);

        rvTaskList.setAdapter(adapter);

        rvTaskList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

        rvTaskList.setLayoutManager(manager);

        llNoTask = v.findViewById(R.id.llNoTask);

        if(tasks.isEmpty()){
            llNoTask.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void taskEmpty() {
        llNoTask.setVisibility(View.VISIBLE);
    }
}
