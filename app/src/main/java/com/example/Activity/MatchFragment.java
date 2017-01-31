/*
package com.example.Activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.Beans.Variable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MatchFragment extends Fragment {
    private String m_strInterests; // 관심분야
    private String m_strDetailInterests; // 세부항목
    private String m_strNumPeople; // 인원

    private EditText edtInterests; // "관심분야" 에디트
    private EditText edtDetailInterests; // "세부항목" 에디트
    private EditText edtNumPeople; // "인원" 에디트

    private Button btnInterests; // "관심분야" 버튼
    private Button btnDetailInterests; // "세부항목" 버튼
    private Button btnNumPeople; // "인원" 버튼
    private Button btnSearch; // "찾기" 버튼

    private int selectInterests = 0;
    private int selectDetailInterests = 0;
    private int selectNumPeople = 0;

    public void MatchFragment() {
        // null
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        edtInterests = (EditText)view.findViewById(R.id.edtInterests); // "관심분야" 에디트
        edtDetailInterests = (EditText)view.findViewById(R.id.edtDetailInterests); // "세부항목" 에디트
        edtNumPeople = (EditText)view.findViewById(R.id.edtNumPeople); // "인원" 에디트

        btnInterests = (Button)view.findViewById(R.id.btnInterests); // "관심분야" 버튼
        btnDetailInterests = (Button)view.findViewById(R.id.btnDetailInterests); // "세부항목" 버튼
        btnNumPeople = (Button)view.findViewById(R.id.btnNumPeople); // "인원" 버튼
        btnSearch = (Button)view.findViewById(R.id.btnSearch); // "찾기" 버튼

        setstrInterests(edtInterests.getText().toString());
        setstrDetailInterests(edtDetailInterests.getText().toString());
        setstrNumPeople(edtNumPeople.getText().toString());

        // "관심분야" 버튼 onClick
        btnInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(this)
                        .setTitle("관심분야를 선택하세요.")
                        .setIcon(R.drawable.ic_menu_gallery)
                        .setSingleChoiceItems(R.array.Interests, selectInterests, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectInterests = which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String[] interests = getResources().getStringArray(R.array.Interests);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

        // "세부항목" 버튼 onClick
        btnDetailInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        // "인원" 버튼 onClick
        btnNumPeople.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        // "찾기" 버튼 onClick
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //UpdateSearch(getstrInterests(), getstrDetailInterests(), getstrNumPeople());
            }
        });


        return view;
    }

    public void btnInterestsClick(View view) {
        final Char
    }

    public void UpdateSearch(String strInterests, String strNumPeople) {
        class UpdateSearchTask extends AsyncTask<String, Void, String> {
            /*
            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }


            protected String doInBackground(String[] params) {
                // 파라미터를 받아오는 부분
                String strInterests = (String) params[0];
                String strNumPeople = (String) params[1];

                try {
                    // 서버에 넘겨주기 위한 데이터를 서버에서 읽을 수 있는 내용으로 인코딩하는 작업
                    String data = "";
                    data += URLEncoder.encode("interests", "UTF-8") + "=" + URLEncoder.encode(strInterests, "UTF-8");
                    data += "&" + URLEncoder.encode("numpeople", "UTF-8") + "=" + URLEncoder.encode(strNumPeople, "UTF-8");

                    // 서버와 연결을 시도하는 부분
                    URL url = new URL(Variable.m_SERVER_URL + Variable.m_PHP_UPDATE_SEARCH);
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
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    // onPostExecute 부분으로 스트링을 전달함
                    return sb.toString().trim();
                } catch(Exception exception) {
                    return new String(exception.getMessage());
                }
            }

            /*
            }protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

        }

        UpdateSearchTask updateSearchTask = new UpdateSearchTask();
        updateSearchTask.execute(strInterests, strNumPeople);
    }

    // set 관심분야
    public void setstrInterests(String strInterests) {
        m_strInterests = strInterests;
    }

    // set 세부항목
    public void setstrDetailInterests(String strDetailInterests) {
        m_strDetailInterests = strDetailInterests;
    }

    // get 인원
    public void setstrNumPeople(String strNumPeople) {
        m_strNumPeople = strNumPeople;
    }

    // get 관심분야
    public String getstrInterests() {
        return m_strInterests;
    }

    // get 세부항목
    public String getstrDetailInterests() {
        return m_strDetailInterests;
    }

    // get 인원
    public String getstrNumPeople() {
        return m_strNumPeople;
    }
}
*/
