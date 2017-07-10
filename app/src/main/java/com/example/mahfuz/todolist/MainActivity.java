package com.example.mahfuz.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mahfuz.todolist.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText todoTaskTitle;
    private EditText todoTaskDetails;
    private View ad_view;
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayList<Task> taskList = null;

    private  TasksAdapter tasksAdapter =null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskListView = (ListView) findViewById(R.id.lv_list_todo);
        
        updateUI();
    }

    private void updateUI() {



        mHelper = new TaskDbHelper(this);
        taskList = mHelper.getData();


        if (tasksAdapter == null) {
            tasksAdapter = new TasksAdapter(this, R.layout.item_todo, taskList);
            mTaskListView.setAdapter(tasksAdapter);
        } else {
            tasksAdapter.clear();
            tasksAdapter.addAll(taskList);
            tasksAdapter.notifyDataSetChanged();
        }



    }

    public void deleteTask(View view) {
//        View parent = (View) view.getParent();
//        TextView taskTextView = (TextView) parent.findViewById(R.id.txt_task_title);
//        String task = String.valueOf(taskTextView.getText());
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.delete(TaskContract.TaskEntry.TABLE,
//                TaskContract.TaskEntry.COL_TASK_NAME + " = ?",
//                new String[]{task});
//        db.close();
//        updateUI();

        int position = Integer.parseInt(view.getTag().toString());
        Task t = tasksAdapter.getItem(position);
        mHelper.deleteTask(t);
        updateUI();
    }

    public void updateTask(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        final Task t = tasksAdapter.getItem(position);

        LayoutInflater inflater = this.getLayoutInflater();
        // inflate the View
        ad_view = inflater.inflate(R.layout.add_todo, null);
        todoTaskTitle = (EditText) ad_view.findViewById(R.id.task_title);
        todoTaskDetails = (EditText) ad_view.findViewById(R.id.task_description);
        todoTaskTitle.setText(t.getName());
        todoTaskDetails.setText(t.getDescription());

        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Edit Task")
                .setMessage("Update Task")
                .setView(ad_view)
                .setPositiveButton("Update", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        String taskTitle = String.valueOf(todoTaskTitle.getText());
                        String taskDetails = String.valueOf(todoTaskDetails.getText());
                        Log.d(TAG, "Task to add: " + taskTitle + " " + taskDetails);
                        mHelper.updateTask(t.getId(),taskTitle,taskDetails, getDateTime());
                        updateUI();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_task:
                Log.d(TAG, "Add New Task");

                //final EditText todo_title = new EditText(this);
                //LayoutInflater inflater = getActivity().getLayoutInflater();
                LayoutInflater inflater = this.getLayoutInflater();
                // inflate the View
                ad_view = inflater.inflate(R.layout.add_todo, null);


                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("Add a new Task")
                                            .setMessage("Write Something")
                                            .setView(ad_view)
                                            .setPositiveButton("Add", new DialogInterface.OnClickListener(){

                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    todoTaskTitle = (EditText) ad_view.findViewById(R.id.task_title);
                                                    todoTaskDetails = (EditText) ad_view.findViewById(R.id.task_description);

                                                    String taskTitle = String.valueOf(todoTaskTitle.getText());
                                                    String taskDetails = String.valueOf(todoTaskDetails.getText());
                                                    Log.d(TAG, "Task to add: " + taskTitle + " " + taskDetails);
                                                    mHelper.insertTask(taskTitle,taskDetails, getDateTime(),getDateTime());
                                                    updateUI();

                                                }
                                            })
                                            .setNegativeButton("Cancel", null)
                                            .create();

                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
