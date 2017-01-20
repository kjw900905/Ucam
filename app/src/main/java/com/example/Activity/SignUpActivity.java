package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText name, id, pw, studNum, univName;
    CheckBox male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void submit_Button(View v){
        name = (EditText)findViewById(R.id.input_Name);
        id = (EditText)findViewById(R.id.input_Id);
        pw = (EditText)findViewById(R.id.input_Pw);
        studNum = (EditText)findViewById(R.id.input_Stu_Num);
        univName = (EditText)findViewById(R.id.input_School_Name);
        male = (CheckBox)findViewById(R.id.check_Box_M);
        female = (CheckBox)findViewById(R.id.checkBoxW);

        String Name = name.getText().toString();
        String ID = id.getText().toString();
        String PW = pw.getText().toString();
        String StudNum = studNum.getText().toString();
        String UnivName = univName.getText().toString();

        if(chkString(Name, ID, PW, StudNum, UnivName) && chkMaleFemale(male, female)) {
            Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void search_Button(View v){
        Toast.makeText(getApplicationContext(), "검색중...", Toast.LENGTH_SHORT).show();

        /* TODO: 검색 화면으로 넘어가기
        *  1) DB와 연동하여 검색
        *  2) 검색을 위한 액티비티 따로 형성
        * */
    }

    private boolean chkString(String Name, String ID, String PW, String StudNum, String UnivName){
        int[] chk = { Name.length(), ID.length(), PW.length(), StudNum.length(), UnivName.length() };

        for(int i = 0; i < 5; i++){
            if(chk[i] <= 0){
                Toast.makeText(getApplicationContext(), "정보를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        /*
        *  그 외에 다른 특수한 조건 사항이 있으면 추가할 것 (예: 특정 특수 문자 금지 등)
        *  (참인 것에 대해서 return true 하는 것이 아니라 거짓인 것에 대해서 return false 할 것)
        * */

        return true;
    }

    private boolean chkMaleFemale(CheckBox male, CheckBox female){
        boolean isMaleChecked = male.isChecked();
        boolean isFemaleChecked = female.isChecked();

        if(isMaleChecked && isFemaleChecked){
            Toast.makeText(getApplicationContext(),
                    "양성애자가 아니시라면 한 가지 성별만 선택하십시오.\n" +
                            "양성애자는 가입이 승인되지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isMaleChecked || isFemaleChecked){
            if(isMaleChecked){
                Toast.makeText(getApplicationContext(), "남성", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "여성", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        Toast.makeText(getApplicationContext(), "성별을 체크해 주세요.", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void cancel_Button(View v){
        Toast.makeText(getApplicationContext(), "가입 취소", Toast.LENGTH_SHORT).show();
        finish();
    }
}