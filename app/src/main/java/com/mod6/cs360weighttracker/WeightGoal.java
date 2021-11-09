package com.mod6.cs360weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WeightGoal extends AppCompatActivity {
    private int progr = 80;
    private TextView tvProgress;
    private ProgressBar pbProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goal);

        updateProgressBar();
    }

    private void updateProgressBar() {
        tvProgress = findViewById(R.id.tvProgress);
        pbProgressBar = findViewById(R.id.pbProgressBar);

        pbProgressBar.setProgress(progr);
        tvProgress.setText(progr + "%");
    }
}