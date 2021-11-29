package com.example.lizastodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

//add the functionality in this class to interact with the database
public class EditTaskActivity extends AppCompatActivity {
    //Add an instance of ToDoList as a field in EditTaskActivity
    private ToDoList toDoList;

    //Modify the onCreate() to initialize the toDoList object and take in the intent data from the
    //MainActivity class.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        toDoList = new ToDoList(this);

        Intent intent = getIntent();
        int taskId = intent.getIntExtra("com.example.lizastodolist.taskId", 0);
        if (taskId != 0) {
            populateTaskData(taskId);
        }
    }


    //Write the function populateTaskData() to fill in the data of the UI with a Task:
    private void populateTaskData(int taskId) {
        //Bind to UI fields
        TextView textID = (TextView) findViewById(R.id.textID);
        CheckBox checkPriority = (CheckBox) findViewById(R.id.checkBoxPriority);
        EditText editDate = (EditText) findViewById(R.id.editDueDate);
        EditText editTask = (EditText) findViewById(R.id.editTask);
        CheckBox checkBoxDone = (CheckBox) findViewById(R.id.checkBoxDone);

        //Create task
        Task t = toDoList.getTask(taskId);

        //Display Data
        textID.setText(String.valueOf(getTaskId()));
        checkPriority.setChecked(t.isPriority());
        editDate.setText(t.getDate());
        editTask.setText(t.getTask());
        checkBoxDone.setChecked(t.isCompleted());

    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddTask:
                addToDo();
                break;
            case R.id.buttonEditTask:
                editTaskData();
                break;
            case R.id.buttonDeleteTask:
                deleteTask();
                break;
            case R.id.buttonMainMenu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void editTaskData() {
        //Bind to UI Fields
        TextView textID = (TextView) findViewById(R.id.textID);
        CheckBox checkPriority = (CheckBox) findViewById(R.id.checkBoxPriority);
        EditText editDate = (EditText) findViewById(R.id.editDueDate);
        EditText editTask = (EditText) findViewById(R.id.editTask);
        CheckBox checkBoxDone = (CheckBox) findViewById(R.id.checkBoxDone);

        //check if box has data:
        if (textID.getText().toString().length() > 0) {
            int id = Integer.valueOf(textID.getText().toString());
            Task t = toDoList.getTask(id);

            t.setPriority(checkPriority.isChecked());
            t.setDate(editDate.getText().toString());
            t.setTask(editTask.getText().toString());
            t.setCompleted(checkBoxDone.isChecked());

            toDoList.editTask(t);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void addToDo() {
        //Bind to UI Fields
        TextView textID = (TextView) findViewById(R.id.textID);
        CheckBox checkPriority = (CheckBox) findViewById(R.id.checkBoxPriority);
        EditText editDate = (EditText) findViewById(R.id.editDueDate);
        EditText editTask = (EditText) findViewById(R.id.editTask);
        CheckBox checkBoxDone = (CheckBox) findViewById(R.id.checkBoxDone);

        Task t = new Task();

        //Handle date data
        t.setPriority(checkPriority.isChecked());
        t.setDate(editDate.getText().toString());
        t.setTask(editTask.getText().toString());
        t.setCompleted(checkBoxDone.isChecked());

        Task newTask = toDoList.createTask(t);

        //Display the ID of the task
        textID.setText(String.valueOf(newTask.getTaskId()));

        //textID.setText("Test");
    }


    private void deleteTask() {
        //Bind to UI Fields
        TextView textID = (TextView) findViewById(R.id.textID);

        //check if box has data
        if (textID.getText().toString().length() > 0) {
            int id = Integer.valueOf(textID.getText().toString());
            Task t = toDoList.getTask(id);
            toDoList.deleteTask(t);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}