package com.example.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void submit_Button(View v){
        Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_LONG).show();
        finish();
    }
}