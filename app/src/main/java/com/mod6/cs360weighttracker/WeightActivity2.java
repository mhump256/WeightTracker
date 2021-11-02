package com.mod6.cs360weighttracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WeightActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    WeightHistoryDatabase WDB;
    DBHelper UDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_history);

        String userName = getIntent().getStringExtra("message_key");
        Intent nameTransfer = new Intent(WeightActivity2.this, WeightHistoryDatabase.class);
        nameTransfer.putExtra("message_key", userName);

        UDB = new DBHelper(this);
        DBHelper userDBHelper = new DBHelper(WeightActivity2.this);
        userDBHelper.createDailyTable(userName);

        ArrayList<weightHistoryView> exampleList = new ArrayList<>();
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, userName, "208"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/16/20", "207"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/17/20", "206"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/18/20", "206"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/19/20", "206"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/20/20", "205"));
        exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background, "12/21/20", "204"));




        mRecyclerView = findViewById(R.id.weightRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new WeightHistoryAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}