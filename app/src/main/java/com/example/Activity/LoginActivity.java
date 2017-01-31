package com.example.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Beans.Variable;
import com.example.kjw90.ucam.FindIdPwActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.Activity.R.id.finding_Button;
import static com.example.Activity.R.id.sign_In_Button;
import static com.example.Activity.R.id.sign_Up_Button;

public class LoginActivity extends AppCompatActivity {
    public Button log_In_Button;

    private TextView join_Button;
    private TextView find_id_pw_Button;

    private String TAG_RESULTS = "result";
    private String php_ID = "id";
    private String myJSON;

    private EditText username;
    private EditText password;

    private ArrayList<HashMap<String, String>> personList;

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

                    EditText edt_Username_Input = (EditText) findViewById(R.id.username_Input);
                    String str_Username_Input = edt_Username_Input.getText().toString();
                    SelectOne(str_Username_Input);



                    //JSONObject jsonObj = new JSONObject(myJSON);

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

    public void SelectOne(String str_Username_Input) {
        class SelectOneTask extends AsyncTask<String, Void, String> {
            /*ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }
            */

            protected String doInBackground(String[] params) {
                String temp_ID = (String) params[0];

                try {
                    String data = "";
                    data += URLEncoder.encode(php_ID, "UTF-8") + "=" + URLEncoder.encode(temp_ID, "UTF-8");

                    URL url = new URL(Variable.m_SERVER_URL + Variable.m_PHP_CHECK_ID_PW);
                    URLConnection con = url.openConnection();

                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    return sb.toString().trim();
                } catch(Exception exception) {
                    return new String(exception.getMessage());
                }
            }

            protected  void onPostExecute(String result) {
                myJSON = result;
                check_ID_PW();

                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }

        SelectOneTask selectOneTask = new SelectOneTask();
        selectOneTask.execute(str_Username_Input);
    }

    public void check_ID_PW(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            String id = jsonObj.getString(php_ID);
            HashMap<String, String> persons = new HashMap<String, String>();

            Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

            persons.put(php_ID, id);
            personList.add(persons);

        } catch(Exception exception) {
            exception.printStackTrace();
        }
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
