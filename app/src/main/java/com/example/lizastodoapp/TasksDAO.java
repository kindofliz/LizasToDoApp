package com.example.lizastodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



//The TasksDAO (Database Accessor Object) will use the TasksSQLiteHelper to
//create new entries into the database, read entries, edit entries, and delete entries.

public class TasksDAO {
    //Fields
    private SQLiteDatabase database;
    private TasksSQLiteHelper dbHelper;
    private String [] allColumns = {
            TasksSQLiteHelper.COLUMN_ID,
            TasksSQLiteHelper.COLUMN_PRIORITY,
            TasksSQLiteHelper.COLUMN_DATE,
            TasksSQLiteHelper.COLUMN_TASK,
            TasksSQLiteHelper.COLUMN_COMPLETED};


    //Constructor
    public TasksDAO(Context context) {
        dbHelper = new TasksSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    //This will take a Task object and enter it into the database
    public Task createTask(Task t) {
        ContentValues values = new ContentValues();

        //Place t data into a value object
        //Handle boolean values

        int priority = 0;
        if (t.isPriority()) {
            priority = 1;
        }

        int done = 0;
        if (t.isCompleted()) {
            done = 1;
        }

        values.put(TasksSQLiteHelper.COLUMN_PRIORITY, priority);
        values.put(TasksSQLiteHelper.COLUMN_DATE, t.getDate());
        values.put(TasksSQLiteHelper.COLUMN_TASK, t.getTask());
        values.put(TasksSQLiteHelper.COLUMN_COMPLETED, done);

        long insertId = database.insert(TasksSQLiteHelper.TABLE_TASKS, null, values);

        Cursor cursor = database.query(TasksSQLiteHelper.TABLE_TASKS, allColumns,
                TasksSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToLast();
        Task newTask = cursorToTask(cursor);
        cursor.close();

        return newTask;
    }


    //This removes a task from the database:
    public void deleteTask(Task t) {
        int id = t.getTaskId();
        database.delete(TasksSQLiteHelper.TABLE_TASKS, TasksSQLiteHelper.COLUMN_ID +
                " = " + id, null);
    }


    //This modifies the data in a Task within the Database
    public void editTask(Task t) {
        ContentValues values = new ContentValues();
        int id = t.getTaskId();

        int priority = 0;
        if (t.isPriority()) {
            priority = 1;
        }

        int done = 0;
        if (t.isCompleted()) {
            done = 1;
        }


        values.put(TasksSQLiteHelper.COLUMN_PRIORITY, priority);
        values.put(TasksSQLiteHelper.COLUMN_DATE, t.getDate());
        values.put(TasksSQLiteHelper.COLUMN_TASK, t.getTask());
        values.put(TasksSQLiteHelper.COLUMN_COMPLETED, done);

        database.update(TasksSQLiteHelper.TABLE_TASKS, values, TasksSQLiteHelper.COLUMN_ID +
                " = " + id, null);

    }


    //This returns a List <Task> of all the Tasks in the database
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();

        Cursor cursor = database.query(TasksSQLiteHelper.TABLE_TASKS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            taskList.add(task);
            cursor.moveToNext();
        }

        return taskList;
    }


    //The cursorToTask() function allows for the selection of a single record from the database
    private Task cursorToTask(Cursor cursor) {

        int id = cursor.getInt(0);
        int priority = cursor.getInt(1);
        String date = cursor.getString(2);
        String taskString = cursor.getString(3);
        int complete = cursor.getInt(4);

        Task t = new Task();

        t.setTaskId(id);
        t.setPriority(priority == 1);
        t.setDate(date);
        t.setTask(taskString);
        t.setCompleted(complete == 1);

        return t;

    }


    //Finally, the getTaskByID() will return a single Task from the database
    public Task getTaskById(int id) {
        //Create a cursor
        Cursor cursor = database.query(TasksSQLiteHelper.TABLE_TASKS, allColumns,
                TasksSQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null, null);
        return (cursor.moveToFirst()) ? cursorToTask(cursor) : null;
    }

}
