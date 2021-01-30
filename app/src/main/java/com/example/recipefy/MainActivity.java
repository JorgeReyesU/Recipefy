package com.example.recipefy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button registerbtn, loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        registerbtn = findViewById(R.id.register_btn);
        registerbtn.setOnClickListener(this);
        loginbtn = findViewById(R.id.login_btn);
        loginbtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                startActivity(new Intent(MainActivity.this, register_activity.class));
                break;
            case R.id.login_btn:
                startActivity(new Intent(MainActivity.this, login_activity.class));
                break;
        }
    }
}