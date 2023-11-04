package com.example.mydoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.mydoes.Adapter.MyDoesAdapter;
import com.example.mydoes.model.MyDoesModel;
import com.example.mydoes.utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

    private RecyclerView myRecyclerview;
    private FloatingActionButton fab;
    private DatabaseHelper mydb;
    private List<MyDoesModel> mlist;
    private MyDoesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title for the Toolbar
        getSupportActionBar().setTitle("MyDoes");

        myRecyclerview=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        mydb=new DatabaseHelper(MainActivity.this);
        mlist=new ArrayList<>();
        adapter=new MyDoesAdapter(mydb,MainActivity.this);

        myRecyclerview.setHasFixedSize(true);
        myRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerview.setAdapter(adapter);


        mlist=mydb.getAllTasks();
        Collections.reverse(mlist);
        adapter.seTasks(mlist);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(myRecyclerview);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        mlist=mydb.getAllTasks();
        Collections.reverse(mlist);
        adapter.seTasks(mlist);
        adapter.notifyDataSetChanged();
    }
}