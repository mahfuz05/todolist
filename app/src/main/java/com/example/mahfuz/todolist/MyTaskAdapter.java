package com.example.mahfuz.todolist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahfuz.todolist.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahfuz on 7/10/17.
 */

public class MyTaskAdapter extends RecyclerView.Adapter<TaskHolder>{

    private ArrayList<Task> taskList = null;


    public MyTaskAdapter(ArrayList<Task> tasks) {
        this.taskList = tasks;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TaskHolder(inflatedView);

    }



    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {

        Task itemTask = taskList.get(position);
        Log.d("TTT"," "+itemTask.getName());
        holder.bindTask(itemTask);
        holder.bindTag(position);
    }

    @Override
    public int getItemCount() {

        return taskList.size();
    }


}


