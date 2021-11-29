package com.example.lizastodoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

//This class holds an instance of the TasksDAO and a List of the Task objects

public class ToDoList {
    private List<Task> toDoList;
    private TasksDAO tasksDataSource;



    //Constructor
    public ToDoList(Context c) {
        toDoList = new ArrayList<Task>(0);
        tasksDataSource = new TasksDAO(c);
        tasksDataSource.open();
    }


    //Methods for ToDoList interact with the TasksDAO to get, create, edit, delete, and return
    //all Tasks
    public Task getTask(int taskID) {
        return tasksDataSource.getTaskById(taskID);
    }

    public Task createTask( Task t) {
        return tasksDataSource.createTask(t);
    }

    public void editTask(Task t) {
        tasksDataSource.editTask(t);
    }

    public void deleteTask(Task t) {
        tasksDataSource.deleteTask(t);
    }

    public List<Task> getAllTasks() {
        toDoList = tasksDataSource.getAllTasks();
        return toDoList;
    }
}
