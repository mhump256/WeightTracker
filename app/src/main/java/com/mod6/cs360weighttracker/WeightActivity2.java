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
    private EditText newentrypopup_Weight, newentrypopup_Intake, newentrypopup_Used,
            newpopup_Year, newpopup_Month, newpopup_Day;
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
        while (!cursor.isAfterLast()) {

            int cIntake = Integer.parseInt(cursor.getString(3));
            int cUsed = Integer.parseInt(cursor.getString(4));
            String cDeficit = "Calories: " + String.valueOf((cIntake - cUsed));

            exampleList.add(new weightHistoryView(R.drawable.ic_launcher_background,
                    cursor.getString(2), cursor.getString(1), cDeficit));
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
                int inputYear = Integer.parseInt(newpopup_Year.getText().toString());
                int inputMonth = Integer.parseInt(newpopup_Month.getText().toString());
                int inputDay = Integer.parseInt(newpopup_Day.getText().toString());


                String inputDate = (inputYear + "-" + inputMonth + "-" + inputDay);
                String inputWeight = newentrypopup_Weight.getText().toString();
                String inputCalorieInt = newentrypopup_Intake.getText().toString();
                String inputCalorieUse = newentrypopup_Used.getText().toString();

                //Verify data is entered properly
                if (inputDate.isEmpty() || inputWeight.isEmpty() || inputCalorieInt.isEmpty() || inputCalorieUse.isEmpty()) {
                    Toast.makeText(WeightActivity2.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                } else {

                    //Keep number input to valid date ranges
                    if(inputYear > 9999 || inputMonth > 12 || inputDay > 31){
                        Toast.makeText(WeightActivity2.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                    }else {
                        UDB.updateDailyTable(userName, inputDate, inputWeight, inputCalorieInt, inputCalorieUse);
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        });

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
}