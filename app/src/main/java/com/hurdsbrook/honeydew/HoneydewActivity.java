package com.hurdsbrook.honeydew;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Main HoneyDew activity. By default displays lists of tasks.
 */
public class HoneydewActivity extends AppCompatActivity {

    static final int ADD_TASK_REQUEST = 1;
    private final String TASK_TO_ADD = "RETURNED_TASK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new TaskListFragment());
        ft.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
    }

    private void addItem(View v) {
        Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(addTaskIntent, ADD_TASK_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == RESULT_OK) {
                Task taskToAdd = data.getParcelableExtra(TASK_TO_ADD);
                bundleAndAddTask(taskToAdd);
            }
        }
    }

    private void bundleAndAddTask(Task taskToAdd) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TASK_TO_ADD, taskToAdd);
        TaskListFragment updatedTaskList = new TaskListFragment();
        updatedTaskList.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, updatedTaskList);
        ft.commitAllowingStateLoss();
    }
}
