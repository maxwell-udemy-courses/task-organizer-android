package com.maxwell.taskorganizer.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxwell.taskorganizer.HomeActivity;
import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.list.models.Task;
import com.maxwell.taskorganizer.utils.PreferencesManager;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Task> tasks;
    private Context ctx;
    private OnTaskEmpty onTaskEmpty;

    public TaskAdapter(List<Task> tasks, Context ctx, OnTaskEmpty onTaskEmpty) {
        this.tasks = tasks;
        this.ctx = ctx;
        this.onTaskEmpty = onTaskEmpty;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int i) {
        holder.tvTaskTitle.setText(tasks.get(i).getTitle());
        holder.tvTaskDesc.setText(tasks.get(i).getDescription());

        holder.btTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                PreferencesManager pm = new PreferencesManager(ctx);
                pm.saveTaskList(tasks);

                if(tasks.isEmpty()){
                    onTaskEmpty.taskEmpty();
                }
            }
        });

        holder.llTaskContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailFragment fragment = new TaskDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString("task_title", tasks.get(holder.getAdapterPosition()).getTitle());
                bundle.putString("task_desc", tasks.get(holder.getAdapterPosition()).getDescription());

                fragment.setArguments(bundle);

                ((HomeActivity) ctx).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContainer, fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
