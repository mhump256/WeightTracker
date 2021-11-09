package com.mod6.cs360weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeightActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button btNewEntry;
    private Button btGoal;

    WeightHistoryDatabase WDB;
    DBHelper UDB;

    //Pop up for new entry
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newentrypopup_Date, newentrypopup_Weight, newentrypopup_Intake, newentrypopup_Used;
    private Button newentrypopup_save, newentrypopup_cancel;

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

        //Listener for New Entry
        btNewEntry = findViewById(R.id.btNewEntry);
        btNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(WeightActivity2.this, WeightGoal.class));
                createNewWeight();
            }
        });

        //Listener for Goal
        btGoal = findViewById(R.id.btGoalWeight);
        btGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeightActivity2.this, WeightGoal.class));
            }
        });

    }

    //New entry popup
    public void createNewWeight(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View entryPopupView = getLayoutInflater().inflate(R.layout.popup, null);

        newentrypopup_Date = (EditText) entryPopupView.findViewById(R.id.newentrypopup_Date);
        newentrypopup_Weight = (EditText) entryPopupView.findViewById(R.id.newentrypopup_Weight);
        newentrypopup_Intake = (EditText) entryPopupView.findViewById(R.id.newentrypopup_Intake);
        newentrypopup_Used = (EditText) entryPopupView.findViewById(R.id.newentrypopup_Used);

        newentrypopup_save = (Button) entryPopupView.findViewById(R.id.newentrypopup_Save);
        newentrypopup_cancel = (Button) entryPopupView.findViewById(R.id.newentrypopup_Cancel);

        dialogBuilder.setView(entryPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newentrypopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define save button
            }
        });

        newentrypopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define cancel button
                dialog.dismiss();
            }
        });
    }
}