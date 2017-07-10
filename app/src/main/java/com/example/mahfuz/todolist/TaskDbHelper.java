package com.example.mahfuz.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mahfuz.todolist.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by mahfuz on 7/10/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE + " ( "+
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TaskContract.TaskEntry.COL_TASK_NAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DESCRIPTION + " TEXT NOT NULL, "+
                TaskContract.TaskEntry.COL_TASK_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                TaskContract.TaskEntry.COL_TASK_UPDATED + " DATETIME DEFAULT CURRENT_TIMESTAMP );";

            db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(" DROP TABLE IF EXISTS "+ TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

    public ArrayList<Task> getData() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<Task>();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[] {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COL_TASK_NAME,
                        TaskContract.TaskEntry.COL_TASK_DESCRIPTION,
                        TaskContract.TaskEntry.COL_TASK_CREATED,
                        TaskContract.TaskEntry.COL_TASK_UPDATED},
                null, null,null,null,null);

        while (cursor.moveToNext()) {

                tasks.add( new Task(
                    cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_NAME)),
                    cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DESCRIPTION)),
                        getDateFromString(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_CREATED))),
                        getDateFromString(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_UPDATED)))
                ));


        }
        cursor.close();
        db.close();
        return tasks;
    }

    public boolean insertTask(String taskTitle, String taskDetails, String createdAt, String updatedAt ) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(TaskContract.TaskEntry.COL_TASK_NAME , taskTitle);
        values.put(TaskContract.TaskEntry.COL_TASK_DESCRIPTION, taskDetails);
        values.put(TaskContract.TaskEntry.COL_TASK_CREATED, createdAt);
        values.put(TaskContract.TaskEntry.COL_TASK_UPDATED, updatedAt);


        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        return true;
    }

    public boolean updateTask(int id, String taskTitle, String taskDetails, String updatedAt ) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(TaskContract.TaskEntry.COL_TASK_NAME , taskTitle);
        values.put(TaskContract.TaskEntry.COL_TASK_DESCRIPTION, taskDetails);
       // values.put(TaskContract.TaskEntry.COL_TASK_CREATED, createdAt);
        values.put(TaskContract.TaskEntry.COL_TASK_UPDATED, updatedAt);


        db.update(TaskContract.TaskEntry.TABLE,
                values,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{Integer.toString(id)});
        db.close();

        return true;
    }

    public boolean deleteTask(Task task) {


        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{Integer.toString(task.getId())});
        db.close();

        return true;
    }

    private Date getDateFromString(String dt) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
             date = sdf.parse(dt);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
