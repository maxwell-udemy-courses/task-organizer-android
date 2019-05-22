package com.maxwell.taskorganizer.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.list.OnTaskEmpty;
import com.maxwell.taskorganizer.list.TaskAdapter;
import com.maxwell.taskorganizer.list.models.Task;
import com.maxwell.taskorganizer.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class TaskSearchFragment extends Fragment implements OnTaskEmpty {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        EditText etTaskTerm = v.findViewById(R.id.etTaskTerm);
        RecyclerView rvTaskList = v.findViewById(R.id.rvTaskList);

        final List<Task> tasks = new ArrayList<>();

        final TaskAdapter adapter = new TaskAdapter(tasks, getActivity(), this);

        rvTaskList.setAdapter(adapter);

        rvTaskList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

        rvTaskList.setLayoutManager(manager);

        PreferencesManager pm = new PreferencesManager(getActivity());

        final List<Task> finalTasks = pm.loadTaskList();

        etTaskTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 3){
                    String term = s.toString();

                    for(Task task : finalTasks){
                        if(task.getTitle().toLowerCase().contains(term)){
                            if(!tasks.contains(task)){
                                tasks.add(task);
                            }
                        }
                    }
                } else {
                    tasks.clear();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void taskEmpty() {

    }
}
