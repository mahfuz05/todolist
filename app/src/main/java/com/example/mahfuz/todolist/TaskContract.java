package com.example.mahfuz.todolist;

import android.provider.BaseColumns;

/**
 * Created by mahfuz on 7/9/17.
 */

public class TaskContract {

    public static final String  DB_NAME = "com.example.mahfuz.todo";

    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {

        public static final String TABLE =  "tasks";
        //(id, name, description, dateCreated, dateUpdated

       // public static final String COL_TASK_ID = "id";
        public static final String COL_TASK_NAME = "name";
        public static final String COL_TASK_DESCRIPTION = "description";
        public static final String COL_TASK_CREATED = "dateCreated";
        public static final String COL_TASK_UPDATED = "dateUpdated";

    }
}
