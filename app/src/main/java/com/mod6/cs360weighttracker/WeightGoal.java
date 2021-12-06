package com.mod6.cs360weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class WeightGoal extends AppCompatActivity {
    private int goal;
    private int goalComp;
    private TextView tvGoalWeight;
    private ProgressBar pbProgressBar;
    private TextView tvProgress;
    private EditText etWeightGoal;
    private Button bUpdate;
    private double goalProgr = 0;
    private TextView tvBestWeight;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goal);

        tvGoalWeight = findViewById(R.id.tvGoalWeight);
        tvProgress = findViewById(R.id.tvPercentage);
        etWeightGoal = findViewById(R.id.etEnterGoal);
        bUpdate = findViewById(R.id.btUpdate);

        //Receive User's name from previous activities
        String userName = getIntent().getStringExtra("message_key");

        //Initialize database
        DB = new DBHelper(this);
        DBHelper userDbHelper = new DBHelper(WeightGoal.this);
        userDbHelper.createGoalTable(userName);


        goal = organizeGoalTable(userName);
        tvGoalWeight.setText("Current Goal " + goal);
        goalComp = GoalPerc(userName, goal);
        goalProgr = goalComp;
        tvProgress.setText(goalProgr + "%");

        weightDifference(userName);

        //Goal update listener
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userGoal = etWeightGoal.getText().toString();
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                String date = year + "-" + month + "-" + day;

                DB.updateGoalTable(userName, date, userGoal);
                startActivity(getIntent());
            }
        });

        updateProgressBar();
    }

    //Update Progress bar
    private void updateProgressBar() {
        pbProgressBar = findViewById(R.id.pbProgressBar);
        pbProgressBar.setProgress((int) goalProgr);
    }

    //Organize table to retrieve newest goal
    private int organizeGoalTable(String userName) {
        //Query goal table for array and arrange table
        Integer goal = null;
        ArrayList<WeightGoal> goalList = new ArrayList<>();
        String tableGoal = "Goal_Table_" + userName;
        String target = "Goal";
        String column = "ID";


        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor = db.query(tableGoal, new String[]{target}, null,
                null, null, null, column + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            goal = cursor.getInt(0);
            cursor.close();
        } else {
            goal = 0;
        }
        return goal;
    }

    //Daily Weight Differences
    private void weightDifference(String userName) {
        String tableWeight = "Weight_Table_" + userName;
        SQLiteDatabase db = DB.getReadableDatabase();

        tvBestWeight = findViewById(R.id.tvBestWeight);

        //Subtract current weight from previous weight to find difference
        Cursor cursor = db.rawQuery("Select Date, Weight, LAG(Weight, -1, 0) " +
                "OVER (ORDER BY Date DESC) AS previous_weight," +
                "Weight-LAG(Weight, -1, 0) OVER (ORDER BY Date DESC) AS difference, " +
                "(CalorieIntake-CalorieLost)*(-1) AS deficit" +
                " FROM " +tableWeight+ " ORDER BY difference ASC", null);

        //Cursor to grab the date, weight difference and calorie deficit for best day
        if (cursor != null && cursor.moveToFirst()){
            String Test = cursor.getString(3);
            String Test2 = cursor.getString(0);
            String Test3 = cursor.getString(4);
            tvBestWeight.setText("Most Weight lost " + Test + "lbs, on " + Test2 + " with a calorie deficit of " + Test3);
        }
        db.close();
        cursor.close();
    }

    //Goal completion based off initial weight, current weight, and current goal
    private int GoalPerc(String userName, int goal) {
        //Query First Weight Table
        int firstWeight = 0;
        double goalDiffNum;
        double goalDiffDenom;
        int currGoal;
        int currWeight = 0;
        int goalCompPerc;
        String tableWeight = "Weight_Table_" + userName;
        String goalWeight = "Goal_Table_" + userName;
        String target1 = "Weight";
        String target2 = "Goal";
        String column1 = "Date";


        //Grab first weight
        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor1 = db.query(tableWeight, new String[]{target1}, null, null, null,
                null, column1 + " ASC");

        if (cursor1 != null && cursor1.moveToFirst()) {
            firstWeight = cursor1.getInt(0);
            cursor1.close();
        }

        //Grab current goal weight
        Cursor cursor2 = db.query(goalWeight, new String[]{target2}, null, null, null,
                null, column1 + " DESC");
        if (cursor2 != null && cursor2.moveToFirst()) {
            currGoal = cursor2.getInt(0);
            cursor2.close();
        }

        //Grab current weight
        Cursor cursor3 = db.query(tableWeight, new String[]{target1}, null, null, null,
                null, column1 + "  DESC");
        if (cursor3 != null && cursor3.moveToFirst()) {
            currWeight = cursor3.getInt(0);
            cursor3.close();
        }

        goalDiffDenom = (firstWeight - goal);
        goalDiffNum = (firstWeight - currWeight);
        goalCompPerc = (int) ((goalDiffNum / goalDiffDenom) * 100);

        if (goalDiffNum <= 0) {
            Toast.makeText(WeightGoal.this, "Invalid Goal", Toast.LENGTH_SHORT).show();
        }
        System.out.println("Current percentage " + goalDiffNum + " " + goalDiffDenom + " " + goalCompPerc);
        return goalCompPerc;
    }
}