package com.mod6.cs360weighttracker;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class WeightActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private WeightHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button btNewEntry;
    private Button btGoal;

    DBHelper UDB;

    //Pop up for new weight entry
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newentrypopup_Weight, newentrypopup_Intake, newentrypopup_Used, newentrypopup_Date;
    private Button newentrypopup_save, newentrypopup_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_history);

        //Pull username from previous activity to create unique table
        String userName = getIntent().getStringExtra("message_key");
        Intent nameTransfer = new Intent(WeightActivity2.this, WeightGoal.class);
        nameTransfer.putExtra("message_key", userName);


        UDB = new DBHelper(this);
        DBHelper userDBHelper = new DBHelper(WeightActivity2.this);
        userDBHelper.createDailyTable(userName);
        userDBHelper.orderDailyTable(userName);

        //Query table for array on RecyclerView in descending order for date
        ArrayList<weightHistoryView> exampleList = new ArrayList<>();
        String tableName = "Weight_Table_" + userName;
        String selectQuery = "SELECT * FROM " + "Weight_Table_" + userName;
        String column = "Date";

        SQLiteDatabase db = UDB.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, null, null,
                null, null, column + " DESC");

        //Move to first row
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background,
                    cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }

        //Setup Recycler View with cards of Date and Current weight
        mRecyclerView = findViewById(R.id.weightRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new WeightHistoryAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        cursor.close();
        userDBHelper.close();

        //Listener for New Entry
        btNewEntry = findViewById(R.id.btNewEntry);
        btNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewWeight();
            }
        });

        //Listener for Goal
        btGoal = findViewById(R.id.btGoalWeight);
        btGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nameTransfer = new Intent(WeightActivity2.this,
                        WeightGoal.class);
                nameTransfer.putExtra("message_key", userName);
                startActivity(nameTransfer);
            }
        });

    }

    //New entry popup
    public void createNewWeight() {
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

        //Pass username to Weight Goal
        String userName = getIntent().getStringExtra("message_key");
        Intent nameTransfer = new Intent(WeightActivity2.this, WeightGoal.class);
        nameTransfer.putExtra("message_key", userName);

        newentrypopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputDate = newentrypopup_Date.getText().toString();
                String inputWeight = newentrypopup_Weight.getText().toString();
                String inputCalorieInt = newentrypopup_Intake.getText().toString();
                String inputCalorieUse = newentrypopup_Used.getText().toString();

                //Verify data is entered properly
                if (inputDate.isEmpty() || inputWeight.isEmpty() || inputCalorieInt.isEmpty() || inputCalorieUse.isEmpty()) {
                    Toast.makeText(WeightActivity2.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                } else {

                    UDB.updateDailyTable(userName, inputDate, inputWeight, inputCalorieInt, inputCalorieUse);
                    dialog.dismiss();
                    finish();
                    startActivity(getIntent());
                }

                newentrypopup_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //define cancel button
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });
            }
        });

    }
}