package com.example.mahfuz.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class TasksAdapter extends ArrayAdapter<Task> {

    private Context context;
    private ArrayList<Task> tasks;
    TaskDbHelper mHelper;

    public TasksAdapter(Context context, int textViewResourceId, ArrayList<Task> tasks) {
        super(context, textViewResourceId, tasks);

        this.context = context;
        this.tasks = tasks;
        mHelper = new TaskDbHelper(this.context);

    }

    public void updateReceiptsList(List<Task> newlist) {
        tasks.clear();
        tasks.addAll(newlist);
        this.notifyDataSetChanged();
    }



    private class ViewHolder {
        TextView tvTaskTitle;
        TextView tvTaskDescription;
        TextView tvTaskUpdatedAt;
        Button btEditTask;
        Button btDeleteTask;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_todo, null);

            holder = new ViewHolder();
            holder.tvTaskTitle = (TextView) convertView.findViewById(R.id.txt_task_title);
            holder.tvTaskDescription = (TextView) convertView.findViewById(R.id.txt_task_description);
            holder.tvTaskUpdatedAt = (TextView) convertView.findViewById(R.id.txt_task_updated_at);
            holder.btEditTask = (Button) convertView.findViewById(R.id.btn_edit_task);
            holder.btDeleteTask = (Button) convertView.findViewById(R.id.btn_delete_task);

            holder.btEditTask.setTag(position);
            holder.btDeleteTask.setTag(position);

           // holder.btEditTask.setOnClickListener(this);
            //holder.btDeleteTask.setOnClickListener(this);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task singleTask = tasks.get(position);
        holder.tvTaskTitle.setText(singleTask.getName());
        holder.tvTaskDescription.setText(singleTask.getDescription());
        holder.tvTaskUpdatedAt.setText(getDateTime(singleTask.getUpdatedAt()));

        return convertView;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Nullable
    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //Date date = new Date();
        return dateFormat.format(date);
    }
}
