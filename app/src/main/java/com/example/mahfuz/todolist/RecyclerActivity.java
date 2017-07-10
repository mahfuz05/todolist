package com.example.mahfuz.todolist;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahfuz.todolist.Decoration.DividerItemDecoration;
import com.example.mahfuz.todolist.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskDbHelper mHelper;
    private ArrayList<Task> taskList;
    private View ad_view;

    private static final String TAG = "RecycleActivity";
    private EditText todoTaskTitle;
    private EditText todoTaskDetails;
    //private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mHelper = new TaskDbHelper(this);
        //taskList = mHelper.getData();
        //mAdapter = new MyTaskAdapter(taskList);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

// set the adapter

        //mRecyclerView.setAdapter(mAdapter);
        updateUI();

        //mToolbar = (Toolbar) findViewById(R.id.);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        // specify an adapter (see also next example)

        //mRecyclerView.setAdapter(mAdapter);

    }

    private void updateUI() {



        //mHelper = new TaskDbHelper(this);




        if (mAdapter == null) {
            Log.d("TTT", " Null");
            taskList = mHelper.getData();
            mAdapter = new MyTaskAdapter(taskList);
            mRecyclerView.setAdapter(mAdapter);
        } else {

            taskList = mHelper.getData();
            mRecyclerView.setAdapter(new MyTaskAdapter(taskList));
            mRecyclerView.invalidate();
        }



    }

    public void deleteTask(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        final Task t = taskList.get(position);
        mHelper.deleteTask(t);
        mAdapter.notifyItemRemoved(position);
        updateUI();
    }

    public void updateTask(View view) {
//        int itemPosition = mRecyclerView.indexOfChild(view);
//        Log.e("Clicked and Position ",String.valueOf(view.getTag()));

        final int position = Integer.parseInt(view.getTag().toString());
        final Task t = taskList.get(position);

        LayoutInflater inflater = this.getLayoutInflater();
        // inflate the View
        ad_view = inflater.inflate(R.layout.add_todo, null);
        todoTaskTitle = (EditText) ad_view.findViewById(R.id.task_title);
        todoTaskDetails = (EditText) ad_view.findViewById(R.id.task_description);
        todoTaskTitle.setText(t.getName());
        todoTaskDetails.setText(t.getDescription());

        AlertDialog dialog = new AlertDialog.Builder(RecyclerActivity.this)
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
                        mAdapter.notifyItemChanged(position);
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
        return true;
       // return super.onCreateOptionsMenu(menu);
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


                AlertDialog dialog = new AlertDialog.Builder(RecyclerActivity.this)
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
