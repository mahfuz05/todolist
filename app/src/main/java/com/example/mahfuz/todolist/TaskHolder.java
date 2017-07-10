package com.example.mahfuz.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahfuz.todolist.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mahfuz on 7/10/17.
 */

public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private  TextView tvTaskTitle;
    private TextView tvTaskDescription;
    private  TextView tvTaskUpdatedAt;
    private Button btEditTask;
    private Button btDeleteTask;
    private Task task;


    public TaskHolder(View itemView) {
        super(itemView);

        tvTaskTitle = (TextView) itemView.findViewById(R.id.txt_task_title);
        tvTaskDescription = (TextView) itemView.findViewById(R.id.txt_task_description);
        tvTaskUpdatedAt = (TextView) itemView.findViewById(R.id.txt_task_updated_at);
        btEditTask = (Button) itemView.findViewById(R.id.btn_edit_task);
        btDeleteTask = (Button) itemView.findViewById(R.id.btn_delete_task);

        //btEditTask.setTag(position);
        //btDeleteTask.setTag(position);
    }

    public void bindTask(Task task) {
        this.task = task;

        tvTaskTitle.setText(task.getName());
        tvTaskDescription.setText(task.getDescription());
        tvTaskUpdatedAt.setText(getDateTime(task.getUpdatedAt()));


    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //Date date = new Date();
        return dateFormat.format(date);
    }

    public void bindTag(int position) {
        btEditTask.setTag(position);
        btDeleteTask.setTag(position);
    }

    @Override
    public void onClick(View view) {

    }
}
