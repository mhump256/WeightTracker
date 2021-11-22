package com.mod6.cs360weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private Button eRegister;

    public static Users credentials;

    //Initialize User Database
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        eRegName = findViewById(R.id.etRegName);
        eRegPassword = findViewById(R.id.etRegPassword);
        eRegister = findViewById(R.id.btnRegister);

        DB = new DBHelper(this);

        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regUsername = eRegName.getText().toString();
                String regPassword = eRegPassword.getText().toString();

                //Check if user is in database
                boolean checkInsertData = DB.checkUsername(regUsername);

                if(!checkInsertData){

                    if(regUsername.isEmpty() || regPassword.isEmpty()){

                        Toast.makeText(RegistrationActivity.this, "Please Enter Information", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        //Testing encryption
                        try {
                            String encryptName = EncryptDecrypt.encrypt(regUsername);
                            String encryptPass = EncryptDecrypt.encrypt(regPassword);
                            credentials = new Users(encryptName, encryptPass);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //credentials = new Users(regUsername, regPassword);

                        DBHelper userDBHelper = new DBHelper(RegistrationActivity.this);
                        userDBHelper.addOne(credentials);

                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                }
            }
        });
    }
}