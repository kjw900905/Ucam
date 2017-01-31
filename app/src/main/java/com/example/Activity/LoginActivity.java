package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kjw90.ucam.FindIdPwActivity;

import static com.example.Activity.R.id.finding_Button;
import static com.example.Activity.R.id.sign_In_Button;
import static com.example.Activity.R.id.sign_Up_Button;

public class LoginActivity extends AppCompatActivity {
    public Button log_In_Button;
    public TextView join_Button;
    public TextView find_id_pw_Button;

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username_Input);
        password = (EditText)findViewById(R.id.password_Input);

        log_In_Button = (Button)findViewById(sign_In_Button);
        log_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Limited Access to clarify whether the information is corresponding with DB
                if(chk_ID_PW(username, password)) {
                    //SelectOne(strId);
                    Intent intent = new Intent(getApplicationContext(), InActivity.class);
                    startActivity(intent);
                }
            }
        });

        join_Button = (TextView)findViewById(sign_Up_Button);
        join_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        find_id_pw_Button = (TextView)findViewById(finding_Button);
        find_id_pw_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindIdPwActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean chk_ID_PW(EditText id, EditText pw) {
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        if(ID.length() == 0 || PW.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            return false;
        }

        /* TODO: Relate with DB Information (b/w Registered information and Username & Password)
        *  As the purpose of this method is exception handling,
        *  DO NOT FORGET TO RETURN THE BOOLEAN VALUE OF DECISION
        *  (true or false)
        * */


        //If the whole conditions are satisfied
        return true;
    }
}

/*
    public void signIn(View v){
        Toast.makeText(getApplicationContext(), "로그인 완료", Toast.LENGTH_LONG).show();
        finish();
    }
*/
