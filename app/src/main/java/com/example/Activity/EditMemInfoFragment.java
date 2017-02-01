package com.example.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.Beans.Student;
import com.example.Beans.Variable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class EditMemInfoFragment extends Fragment {

    EditText name, id, pw, studNum, univName;
    RadioGroup rdGroupEdit;
    JSONArray peoples = null;
    Student studData;
    String myJSON;

    public EditMemInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_mem_info, container, false);

        /* TODO: ID를 InActivity로부터 받아서 탐색.
        *  - 현 상태는 null로 초기화
        *  - SelectOne 내부의 showStudInfo 함수 자료탐색 오류 수정 필요
        * */
        String Searching_ID = null;

        name = (EditText) view.findViewById(R.id.input_Name_Edit);
        id = (EditText) view.findViewById(R.id.input_Id_Edit);
        pw = (EditText) view.findViewById(R.id.input_Pw_Edit);
        studNum = (EditText) view.findViewById(R.id.input_Stu_Num_Edit);
        univName = (EditText) view.findViewById(R.id.input_School_Name_Edit);
        rdGroupEdit = (RadioGroup) view.findViewById(R.id.rdGroup_Edit);

        SelectOne(Searching_ID);

        // Inflate the layout for this fragment
        Button btnSearch = (Button) view.findViewById(R.id.search_School_Edit);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "검색중...", Toast.LENGTH_SHORT).show();
                //TODO: 검색 관련 코드 추가
            }
        });

        Button btnUnregister = (Button) view.findViewById(R.id.unregister_Button);
        btnUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "회원탈퇴 완료\n이전 정보는 열람할 수 없습니다.", Toast.LENGTH_SHORT).show();

                Delete(id.getText().toString());

                getActivity().onBackPressed();
            }
        });

        Button btnSubmit = (Button) view.findViewById(R.id.submit_Edit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String ID = id.getText().toString();
                String PW = pw.getText().toString();
                String StudNum = studNum.getText().toString();
                String UnivName = univName.getText().toString();
                int radioCheck = rdGroupEdit.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) container.findViewById(radioCheck);
                String Gender = rb.getText().toString();

                if (chkString_Edit(Name, ID, PW, StudNum, UnivName, Gender)) {
                    Toast.makeText(getActivity(), "회원정보 수정 완료", Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                }
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.cancel_Edit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "수정 취소", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private boolean chkString_Edit(String Name, String ID, String PW, String StudNum, String UnivName, String Gender) {
        String[] chk = {Name, ID, PW, StudNum, UnivName, Gender};

        for (int i = 0; i < 6; i++) {
            if (chk[i].length() <= 0) {

                Toast.makeText(getActivity(), "정보를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(Gender != "Male" || Gender != "Female") {
            return false;
        }

        Update(Name, ID, PW, StudNum, UnivName, Gender);

        /*
        *  그 외에 다른 특수한 조건 사항이 있으면 추가할 것 (예: 특정 특수 문자 금지 등)
        *  (참인 것에 대해서 return true 하는 것이 아니라 거짓인 것에 대해서 return false 할 것)
        * */

        return true;
    }

    public void Update(String strName, String strId, String strPw, String strStuNum, String strSchoolName, String gender) {
        class UpdateTask extends AsyncTask<String, Void, String> {
            /*
            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }
            */

            protected String doInBackground(String[] params) {
                // 파라미터를 받아오는 부분
                String name = (String) params[0];
                String id = (String) params[1];
                String password = (String) params[2];
                String studentNumber = (String) params[3];
                String schoolName = (String) params[4];
                String gender = (String) params[5];

                try {
                    // 서버에 넘겨주기 위한 데이터를 서버에서 읽을 수 있는 내용으로 인코딩하는 작업
                    String data = "";
                    data += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                    data += "&" + URLEncoder.encode("studentNumber", "UTF-8") + "=" + URLEncoder.encode(studentNumber, "UTF-8");
                    data += "&" + URLEncoder.encode("schoolName", "UTF-8") + "=" + URLEncoder.encode(schoolName, "UTF-8");
                    data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");

                    // 서버와 연결을 시도하는 부분
                    URL url = new URL(Variable.m_SERVER_URL + Variable.m_PHP_UPDATE);

                    URLConnection con = url.openConnection();

                    // 서버로 전달하는 데이터가 있는 경우에 outputstream을 이용하는 부분
                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    // 서버 연결 후 데이터를 가져오는 부분이 있을때 사용하는 코드
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    // onPostExecute 부분으로 스트링을 전달함
                    return sb.toString().trim();
                } catch (Exception exception) {
                    return new String(exception.getMessage());
                }
            }

            /*
            }protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            */
        }

        UpdateTask updateTask = new UpdateTask();
        updateTask.execute(strName, strId, strPw, strStuNum, strSchoolName, gender);
    }

    public void SelectOne(String strId) {
        class SelectOneTask extends AsyncTask<String, Void, String> {
            /*ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }
            */

            protected String doInBackground(String[] params) {
                String id = (String) params[0];

                try {
                    String data = "";
                    data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    URL url = new URL(Variable.m_SERVER_URL + Variable.m_PHP_SELECT_ONE);
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
                showStudInfo();
            }
        }

        SelectOneTask selectOneTask = new SelectOneTask();
        selectOneTask.execute(strId);
    }

    private void showStudInfo() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("result");

            JSONObject c = peoples.getJSONObject(0);
            String NAME = c.getString(Variable.m_TAG_NAME);
            String ID = c.getString(Variable.m_TAG_ID);
            String PW = c.getString(Variable.m_TAG_PW);
            String STUD_NUM = c.getString(Variable.m_TAG_STUD_NUM);
            String SCHOOL = c.getString(Variable.m_TAG_SCHOOL);
            String GENDER = c.getString(Variable.m_TAG_GENDER);

            name.setText(NAME);
            id.setText(ID);
            pw.setText(PW);
            studNum.setText(STUD_NUM);
            univName.setText(SCHOOL);

            if(GENDER == "Male") {
                rdGroupEdit.check(R.id.rd_Male_Edit);
            } else if(GENDER == "Female") {
                rdGroupEdit.check(R.id.rd_Female_Edit);
            }

        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void Delete(String strId) {
        class DeleteTask extends AsyncTask<String, Void, String> {
            /*ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }
            */

            protected String doInBackground(String[] params) {
                String id = (String) params[0];

                try {
                    String data = "";
                    data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    URL url = new URL(Variable.m_SERVER_URL + Variable.m_PHP_DELETE);
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

            /*
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            */
        }

        DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute(strId);
    }
}
