package com.mod6.cs360weighttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentProviderClient;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.mod6.cs360weighttracker.RegistrationActivity.credentials;

public class MainActivity extends AppCompatActivity {
    private Button logButton;
    private Button registerButton;
    private Switch smsSwitch;
    private int SMS_PERMISSION = 1;

    private EditText eUsername;
    private EditText ePassword;
    private TextView eAttempts;
    private TextView eRegister;

    boolean isValid = false;
    private int counter = 5;

    UsernameDBHelperTest DB;

    SharedPreferences sharedPreferences;

    /*Launch first screen*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        eUsername = findViewById(R.id.eUsername);
        ePassword = findViewById(R.id.ePassword);
        eAttempts = findViewById(R.id.eAttempts);
        eRegister = findViewById(R.id.eNewuser);

        /*Initialize DB*/
        DB = new UsernameDBHelperTest(this);

        /*Listener for SMS Switch*/
        smsSwitch = (Switch) findViewById(R.id.smsSwitch);
        smsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();
                } else {
                    requestSmsPermission();
                }
            }
        });


        /*Listener for login button*/
        logButton = (Button) findViewById(R.id.bLogin);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = eUsername.getText().toString();
                String inputPassword = ePassword.getText().toString();

                /*Verify User input info*/
                if(inputName.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                } else{


                    isValid = validate(inputName, inputPassword);
                    /*Limit the attempts to login*/
                    if(!isValid){
                        counter--;
                        Toast.makeText(MainActivity.this, "Incorrect login information", Toast.LENGTH_SHORT).show();
                        eAttempts.setText("Attempts remaining " + counter);

                        if(counter == 0){
                            logButton.setEnabled(false);
                        }

                    }else {
                        /*Launch Weight History view*/
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(MainActivity.this, WeightActivity2.class));
                    }
                }

            }
        });

        /*Listener for new user*/
        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

    }

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to alert you when your goal is reached")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*Verify Login Credentials*/
    private boolean validate(String name, String password){
        Boolean checkUser = DB.checkusernamepassword(name, password);
        if (checkUser)
            return true;
        else
            return false;
    }
}