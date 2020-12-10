package com.viblo.localdb;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper myDbHelper;

    EditText inp_username;
    EditText inp_password;
    Button btn_login;
    Button btn_to_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadDB();

        inp_username = findViewById(R.id.inp_username);
        inp_password = findViewById(R.id.inp_password);
        btn_login = findViewById(R.id.btn_login);
        btn_to_register = findViewById(R.id.btn_to_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inp_username.getText().toString().trim();
                String password = inp_password.getText().toString().trim();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please fill out all fields" + username, Toast.LENGTH_SHORT).show();
                } else {
                    String query = "SELECT * FROM user WHERE uname='" + username + "' and pass='" + password + "'";
                    Cursor c = myDbHelper.rawQuery(query, null);
                    if (c != null && c.moveToFirst()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        inp_username.setText("");
        inp_password.setText("");
        LinearLayout loginLayout = (LinearLayout) findViewById(R.id.layout_login);
        loginLayout.requestFocus();
    }

    private void loadDB() {
        myDbHelper = new DatabaseHelper(LoginActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
}
