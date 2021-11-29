package com.example.lizastodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //We need to give the MainActivity.java class the ability to read data from the database and
    //display in the ListView object. In addition, we want to have the user be able to select an Task
    //object from the list and edit the Task. Add the following fields:

    private ListView listViewTasks;
    private List<Task> tasks;
    private ToDoList toDoList;


    //In the onCreate() function we will initialize the fields and call a function
    // to populate the listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasks = new ArrayList<>(0);
        toDoList = new ToDoList(this);

        listViewTasks = (ListView) findViewById(R.id.listViewTasks);

        populateListViewTasks();

        listViewTasks.setOnItemClickListener(listViewListener);
    }


    //We will now write the function to populate the ListView object with all the Tasks in the
    //database. Notice the use of the toDoList instance to get all the Task objects.
    private void populateListViewTasks() {
        //Create list of strings
        List<String> taskStrings = new ArrayList<>(0);

        tasks = toDoList.getAllTasks();

        for (int i = 0; i < tasks.size(); i++) {
            taskStrings.add(tasks.get(i).toString());
        }

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskStrings);

        listViewTasks.setAdapter(arrayAdapter);
    }


    //The inner class OnItemClickListener will respond to the User’s touch of an item in the list
    //and call a function to open the EditTask Activity.
    private AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Get Task ID of selected task
            int taskId = tasks.get(position).getTaskId();
            //Make intent
            goToEditTask(taskId);
        }
    };


    //Finally, the function goToEditTask() will open the EditTask Activity and display a given task.
    //Note the sue of .putExtra in line 102 to pass the Task id to the EditTask Activity
    protected void goToEditTask(int id) {
        Intent editTask = new Intent(this, EditTaskActivity.class);
        editTask.putExtra("com.example.lizastodolist.taskId", id);
        startActivity(editTask);
    }


    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddToDo:
                Intent addTask = new Intent(this, EditTaskActivity.class);
                startActivity(addTask);
                break;
        }



    }
}