package com.example.mydoes.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydoes.AddNewTask;
import com.example.mydoes.MainActivity;
import com.example.mydoes.R;
import com.example.mydoes.model.MyDoesModel;
import com.example.mydoes.utils.DatabaseHelper;

import java.util.List;

public class MyDoesAdapter extends RecyclerView.Adapter<MyDoesAdapter.MyViewHolder> {

    private List<MyDoesModel> mlist;
    private MainActivity activity;
    private DatabaseHelper mydb;

    public MyDoesAdapter(DatabaseHelper mydb,MainActivity activity){
        this.activity=activity;
        this.mydb=mydb;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyDoesModel item = mlist.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mydb.updateStatus(item.getId(),1);
                }else
                    mydb.updateStatus(item.getId(),0);
            }
        });
    }
    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }
    public void seTasks(List<MyDoesModel> mlist){
        this.mlist=mlist;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        MyDoesModel item=mlist.get(position);
        mydb.deleteTask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        MyDoesModel item=mlist.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());

        //PASSING THE DATA FROM ACTIVITY TO THE FRAGMENT

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());


    }





    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
