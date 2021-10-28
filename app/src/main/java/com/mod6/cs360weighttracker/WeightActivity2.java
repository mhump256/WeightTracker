package com.mod6.cs360weighttracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WeightActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_history);



        ArrayList<weightHistoryView> exampleList = new ArrayList<>();
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/15/20", "208"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/16/20", "207"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/17/20", "206"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/18/20", "206"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/19/20", "206"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/20/20", "205"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/21/20", "204"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/22/20", "203"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/23/20", "202"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/23/20", "202"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/23/20", "202"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/23/20", "202"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/23/20", "202"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/23/20", "202"));




        mRecyclerView = findViewById(R.id.weightRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}