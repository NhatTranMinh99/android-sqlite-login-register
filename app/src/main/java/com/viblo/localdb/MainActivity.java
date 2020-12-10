package com.viblo.localdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt1;
    TextView txt2;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.hello_txt);
        txt2 = findViewById(R.id.flag_txt);
        btn_logout = findViewById(R.id.btn_logout);

        txt1.setText("Hello, " + getIntent().getStringExtra("username"));
        txt2.setText("No flag here");

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
