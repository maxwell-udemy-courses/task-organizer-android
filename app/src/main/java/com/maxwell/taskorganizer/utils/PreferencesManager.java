package com.maxwell.taskorganizer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maxwell.taskorganizer.auth.models.UserTask;
import com.maxwell.taskorganizer.list.models.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {
    private final String PREFS_NAME = "task_prefs";
    private final String PREFS_TASK = "prefs_task_list";
    private final String PREFS_USER_TASK = "prefs_user_task";
    private final String PREFS_CURRENT_USER = "prefs_current_user";
    private Context ctx;
    private UserTask currentUser;

    public PreferencesManager(Context ctx) {
        this.ctx = ctx;
    }

    public String getPref(String key){
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public void setPref(String key, String value){
        if(value == null){
            value = "";
        }

        SharedPreferences.Editor editor = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public UserTask getCurrentUser() {
        String jsonCurrentUser = getPref(PREFS_CURRENT_USER);

        if(jsonCurrentUser.isEmpty()){
            return null;
        } else {
            return new Gson().fromJson(jsonCurrentUser, UserTask.class);
        }
    }

    public void setCurrentUser(UserTask currentUser) {
        Gson gson = new Gson();

        String jsonCurrentUser = gson.toJson(currentUser);
        setPref(PREFS_CURRENT_USER, jsonCurrentUser);
    }

    private void saveUserList(List<UserTask> userTasks){
        Gson gson = new Gson();

        String jsonUserTask = gson.toJson(userTasks);
        setPref(PREFS_USER_TASK, jsonUserTask);
    }

    public void saveUser(UserTask userTask){
        List<UserTask> userTasksList = loadUserList();

        userTasksList.add(userTask);

        saveUserList(userTasksList);
    }

    public List<UserTask> loadUserList() {
        String jsonUserTask = getPref(PREFS_USER_TASK);

        if(jsonUserTask.isEmpty()){
            return new ArrayList<UserTask>();
        } else {
            Type taskType = new TypeToken<List<UserTask>>(){}.getType();

            return new Gson().fromJson(jsonUserTask, taskType);
        }
    }

    public void resetUserList(){
        List<UserTask> userList = new ArrayList<>();

        saveUserList(userList);
    }

    public void saveTaskList(List<Task> tasks){
        Gson gson = new Gson();

        String jsonTask = gson.toJson(tasks);
        setPref(PREFS_TASK, jsonTask);
    }

    public void saveTask(Task task){
        List<Task> tasks = loadTaskList();

//        if(tasks == null){
//            tasks = new ArrayList<>();
//        }

        tasks.add(task);

        saveTaskList(tasks);
    }

    public void resetTaskList(){
        List<Task> tasks = new ArrayList<>();

        saveTaskList(tasks);
    }

    public List<Task> loadTaskList(){
        String jsonTask = getPref(PREFS_TASK);

        if(jsonTask.isEmpty()){
            return new ArrayList<Task>();
        } else {
            Type taskType = new TypeToken<List<Task>>(){}.getType();

            return new Gson().fromJson(jsonTask, taskType);
        }

    }
}
