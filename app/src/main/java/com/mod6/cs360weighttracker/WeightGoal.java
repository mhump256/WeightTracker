package com.mod6.cs360weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Calendar;

public class WeightGoal extends AppCompatActivity {
    private int progr = 80;
    private TextView tvGoalWeight;
    private ProgressBar pbProgressBar;
    private EditText etWeightGoal;
    private Button bUpdate;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goal);

        tvGoalWeight = findViewById(R.id.tvGoalWeight);
        etWeightGoal = findViewById(R.id.etEnterGoal);
        bUpdate = findViewById(R.id.btUpdate);

        //Receive User's name from previous activities
        String userName = getIntent().getStringExtra("message_key");

        DB = new DBHelper(this);

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userGoal = etWeightGoal.getText().toString();

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR) + 1;
                String date = year + "-" + month + "-" + day;

                DB.updateDailyTable(userName, date, null, userGoal, null, null);

                tvGoalWeight.setText(userGoal);
            }
        });

        updateProgressBar();
    }

    private void updateProgressBar() {
        //pbProgressBar = findViewById(R.id.tvProgress);
        pbProgressBar = findViewById(R.id.pbProgressBar);

        pbProgressBar.setProgress(progr);
        //pbProgressBar.setProgress(progr + "%");
    }
}