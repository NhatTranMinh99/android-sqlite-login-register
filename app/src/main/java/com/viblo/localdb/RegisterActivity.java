package com.viblo.localdb;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper myDbHelper;

    EditText reg_username;
    EditText reg_password;
    EditText reg_repassword;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadDB();

        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_password);
        reg_repassword = findViewById(R.id.reg_repassword);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = reg_username.getText().toString().trim();
                String password = reg_password.getText().toString().trim();
                String repassword = reg_repassword.getText().toString().trim();
                if (username.equals("") || password.equals("") || repassword.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill out all fields" + username, Toast.LENGTH_SHORT).show();
                } else if ( !password.equals(repassword) ) {
                    Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "SELECT * FROM user WHERE uname='" + username + "'";
                    Cursor c = myDbHelper.rawQuery(query, null);
                    if (c != null && c.moveToFirst()) {
                        Toast.makeText(RegisterActivity.this, "Username " + username + " already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        myDbHelper.addUser(new User(username, password));
                        query = "SELECT * FROM user WHERE uname='" + username + "' and pass='" + password + "'";
                        c = myDbHelper.rawQuery(query, null);
                        if (c != null && c.moveToFirst()) {
                            Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void loadDB() {
        myDbHelper = new DatabaseHelper(RegisterActivity.this);
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
}
