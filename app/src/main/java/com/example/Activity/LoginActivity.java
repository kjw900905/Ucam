package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.example.Activity.R.id.sign_In_Button;

public class LoginActivity extends AppCompatActivity {
    public Button log_In_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_In_Button = (Button)findViewById(sign_In_Button);
        log_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InActivity.class);
                startActivity(intent);
            }
        });
        
    }


}
