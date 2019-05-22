package com.maxwell.taskorganizer.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.BaseSelectionManager;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.list.OnTaskEmpty;
import com.maxwell.taskorganizer.list.TaskAdapter;
import com.maxwell.taskorganizer.list.models.Task;
import com.maxwell.taskorganizer.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class TaskCalendarFragment extends Fragment implements OnTaskEmpty {
    List<Task> prefsTasks;
    List<Task> tasks;
    TaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        initCalendar(v);
        initTaskList(v);
    }

    private void initTaskList(View v) {
        RecyclerView rvTaskList = v.findViewById(R.id.rvTaskList);

        tasks = new ArrayList<>();

        adapter = new TaskAdapter(tasks, getActivity(), this);

        rvTaskList.setAdapter(adapter);

        rvTaskList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

        rvTaskList.setLayoutManager(manager);
    }

    private void initCalendar(View v) {
        CalendarView cvTasks = v.findViewById(R.id.cvTasks);

        cvTasks.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        cvTasks.setSelectionType(SelectionType.SINGLE);

        final Set<Long> days = new TreeSet<>();

        PreferencesManager pm = new PreferencesManager(getActivity());
        prefsTasks = pm.loadTaskList();

        for (Task task : prefsTasks){
            days.add(task.getExpirationDate().getTime());
        }

        int textColor = Color.parseColor("#ff0000");
        int selectedTextColor = Color.parseColor("#ff4000");
        int disableTextColor = Color.parseColor("#ff8000");

        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disableTextColor);

        cvTasks.addConnectedDays(connectedDays);

        cvTasks.setSelectionManager(new BaseSelectionManager() {
            @Override
            public void toggleDay(@NonNull Day day) {
                day.getCalendar().set(Calendar.HOUR_OF_DAY, 0);
                day.getCalendar().set(Calendar.MINUTE, 0);
                day.getCalendar().set(Calendar.SECOND, 0);
                day.getCalendar().set(Calendar.MILLISECOND, 0);

                tasks = getTasksByDate(day.getCalendar().getTimeInMillis());

                adapter.setTasks(tasks);
                adapter.notifyDataSetChanged();

                Log.d("", "");
            }

            @Override
            public boolean isDaySelected(@NonNull Day day) {
                return false;
            }

            @Override
            public void clearSelections() {

            }
        });
    }

    private List<Task> getTasksByDate(Long date){
        List<Task> taskList = new ArrayList<>();

        for(Task task : prefsTasks){
            if(task.getExpirationDate().getTime() == date){
                taskList.add(task);
            }
        }

        return taskList;
    }

    @Override
    public void taskEmpty() {

    }
}
