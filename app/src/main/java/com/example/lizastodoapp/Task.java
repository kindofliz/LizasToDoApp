package com.example.lizastodoapp;


//This class will model the ToDoList item we want to store
public class Task {

    //Fields to Model Task object
    private int taskId;
    private boolean priority;
    private String date;
    private String task;
    private boolean isCompleted;


    //Constructor
    public Task() {
    }


    //Accessors and Modifiers
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }


    //custom toString
    public String toString() {
        String result = "";

        if(priority) {
            result += "Priority: ";
        }

        result += task + " " + date;

        return result;
    }
}
