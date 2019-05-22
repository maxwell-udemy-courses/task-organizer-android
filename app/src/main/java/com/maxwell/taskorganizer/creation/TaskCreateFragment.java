package com.maxwell.taskorganizer.creation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.maxwell.taskorganizer.R;
import com.maxwell.taskorganizer.list.models.Task;
import com.maxwell.taskorganizer.utils.DialogManager;
import com.maxwell.taskorganizer.utils.PreferencesManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TaskCreateFragment extends Fragment {
    Calendar calendarExpiration, calendarToday;
    EditText etTaskName, etTaskDescription, etTaskExpiration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        calendarExpiration = Calendar.getInstance();
        calendarToday = Calendar.getInstance();

        etTaskName = v.findViewById(R.id.etTaskName);
        etTaskDescription = v.findViewById(R.id.etTaskDescription);
        etTaskExpiration = v.findViewById(R.id.etTaskExpiration);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarExpiration.set(Calendar.YEAR, year);
                calendarExpiration.set(Calendar.MONTH, month);
                calendarExpiration.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format);

                etTaskExpiration.setText(sdf.format(calendarExpiration.getTime()));
            }
        };

        etTaskExpiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, calendarExpiration.get(Calendar.YEAR), calendarExpiration.get(Calendar.MONTH), calendarExpiration.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button btCreateTask = v.findViewById(R.id.btCreateTask);

        btCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskName.getText().toString();
                String taskDescription = etTaskDescription.getText().toString();
                String taskExpiration = etTaskExpiration.getText().toString();

                if(taskName.isEmpty() || taskDescription.isEmpty() || taskExpiration.isEmpty()){
                    return;
                }

                calendarExpiration.set(Calendar.HOUR_OF_DAY, 0);
                calendarExpiration.set(Calendar.MINUTE, 0);
                calendarExpiration.set(Calendar.SECOND, 0);
                calendarExpiration.set(Calendar.MILLISECOND, 0);

                Task task = new Task(taskName, taskDescription, calendarToday.getTime(), calendarExpiration.getTime());

                PreferencesManager pm = new PreferencesManager(getActivity());

                pm.saveTask(task);

                taskCreatedSuccess();
            }
        });
    }

    private void taskCreatedSuccess(){
        DialogManager dm = new DialogManager(getActivity(), "Tarea Creada", "Tarea Creada con Exito!");
        Dialog dialog = dm.buildDialog();
        dialog.show();

        etTaskName.setText("");
        etTaskDescription.setText("");
        etTaskExpiration.setText("");
    }
}
