/*
package com.example.Activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

/*public class MatchFragment extends Fragment {
    private String m_strInterests; // 관심분야
    private String m_strDetailInterests; // 세부항목
    private String m_strNumPeople; // 인원

    private EditText edtInterests; // "관심분야" EditText
    private EditText edtDetailInterests; // "세부항목" EditText
    private EditText edtNumPeople; // "인원" EditText

    private Button btnInterests; // "관심분야" Button
    private Button btnDetailInterests; // "세부항목" Button
    private Button btnNumPeople; // "인원" Button
    private Button btnSearch; // "찾기" Button

    private int selectInterests = 0; // "관심분야" RadioButton value
    private int o_selectInterests = -1; // 이전 "관심분야" value
    private boolean[] selectDetailInterestsGame = {false, false, false}; // "세부항목"의 "게임" CheckBox value
    private boolean[] selectDetailInterestsMeal = {false, false, false, false}; // "세부항목"의 "식사" CheckBox value
    private boolean[] selectDetailInterestsExercise = {false, false, false}; // "세부항목"의 "운동" CheckBox value
    private boolean[] selectDetailInterestsMajor = {false, false, false}; // "세부항목"의 "전공" CheckBox value
    private int selectNumPeople = 0; // "인원" RadioButton value

    public void MatchFragment() {
        // null
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        edtInterests = (EditText)view.findViewById(R.id.edtInterests); // "관심분야" EditText
        edtDetailInterests = (EditText)view.findViewById(R.id.edtDetailInterests); // "세부항목" EditText
        edtNumPeople = (EditText)view.findViewById(R.id.edtNumPeople); // "인원" EditText

        btnInterests = (Button)view.findViewById(R.id.btnInterests); // "관심분야" Button
        btnDetailInterests = (Button)view.findViewById(R.id.btnDetailInterests); // "세부항목" Button
        btnNumPeople = (Button)view.findViewById(R.id.btnNumPeople); // "인원" Button
        btnSearch = (Button)view.findViewById(R.id.btnSearch); // "찾기" Button

        // "관심분야" EditText onClick
        edtInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("관심분야를 선택하세요.");
                //builder.setIcon(R.drawable.ic_menu_gallery);
                builder.setSingleChoiceItems(R.array.Interests, selectInterests, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectInterests = which;
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // [이전에 선택한 것과 다른것을 선택하는 경우(이전 index와 현재 index가 다른 경우)]
                        // 1. "관심분야" EditText value 변경, "세부항목" EditText value 초기화, "인원" EditText value 초기화
                        // 2. 이전 index와 현재 index를 일치시킨다.
                        if(o_selectInterests != selectInterests) {
                            String[] interests = getResources().getStringArray(R.array.Interests); // app/res/values/strings.xml의 <string-array name="Interests">
                            edtInterests.setText(interests[selectInterests]); // "관심분야" EditText value 변경
                            edtDetailInterests.setText(""); // "세부항목" EditText value 초기화
                            edtNumPeople.setText(""); // "인원" EditText value 초기화
                            o_selectInterests = selectInterests; // 이전 index와 현재 index 일치
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        // "관심분야" Button onClick
        btnInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("관심분야를 선택하세요.");
                //builder.setIcon(R.drawable.ic_menu_gallery);
                builder.setSingleChoiceItems(R.array.Interests, selectInterests, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectInterests = which;
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // [이전에 선택한 것과 다른것을 선택하는 경우(이전 index와 현재 index가 다른 경우)]
                        // 1. "관심분야" EditText value 변경, "세부항목" EditText value 초기화, "인원" EditText value 초기화
                        // 2. 이전 index와 현재 index를 일치시킨다.
                        if(o_selectInterests != selectInterests) {
                            String[] interests = getResources().getStringArray(R.array.Interests); // app/res/values/strings.xml의 <string-array name="Interests">
                            edtInterests.setText(interests[selectInterests]); // "관심분야" EditText value 변경
                            edtDetailInterests.setText(""); // "세부항목" EditText value 초기화
                            edtNumPeople.setText(""); // "인원" EditText value 초기화
                            o_selectInterests = selectInterests; // 이전 index와 현재 index 일치
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        // "세부항목" EditText onClick
        edtDetailInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                if(TextUtils.isEmpty(edtInterests.getText())) { // "관심분야"를 선택하지 않았을때 메세지 출력
                    builder.setTitle("알림");
                    //builder.setIcon(R.drawable.ic_menu_gallery);
                    builder.setMessage("관심분야를 먼저 선택해주세요.");
                } else { // "관심분야"를 선택한 경우에는 각 "관심분야"에 맞는 항목 출력
                    builder.setTitle("세부항목을 선택하세요.");
                    //builder.setIcon(R.drawable.ic_menu_gallery);
                    if(selectInterests == 0) { // "관심분야"의 "게임" 선택
                        builder.setMultiChoiceItems(R.array.DetailInterests_Game, selectDetailInterestsGame, new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selectDetailInterestsGame[which] = isChecked;
                            }
                        });
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String[] game = getResources().getStringArray(R.array.DetailInterests_Game);
                                String result = ""; // EditText에 넣어주기 위한 String
                                int numCheckedValue = 0; // 체크되어 있는 항목의 개수

                                // 체크되어 있는 항목이 몇개인지 확인
                                for(int i=0; i<selectDetailInterestsGame.length; i++) {
                                    if(selectDetailInterestsGame[i]) {
                                        numCheckedValue ++;
                                    }
                                }

                                // 체크되어 있는 항목의 개수 (-1)개 만큼 쉼표(,)를 붙여준다.
                                for(int i=0; i<selectDetailInterestsGame.length; i++) {
                                    if(selectDetailInterestsGame[i]) {
                                        result += game[i];
                                        if(numCheckedValue != 1) {
                                            result += ", ";
                                            numCheckedValue --;
                                        }
                                    }
                                }

                                edtDetailInterests.setText(result); // "세부항목" EditText value 변경
                                edtNumPeople.setText(""); // "인원" EditText value 초기화
                            }
                        });
                    } else if(selectInterests == 1) { // "관심분야"의 "식사"

                    }
                    builder.setNegativeButton("취소", null);
                }
                builder.show();
            }
        });

        // "세부항목" Button onClick
        btnDetailInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                if(TextUtils.isEmpty(edtInterests.getText())) { // "관심분야"를 선택하지 않았을때 메세지 출력
                    builder.setTitle("알림");
                    //builder.setIcon(R.drawable.ic_menu_gallery);
                    builder.setMessage("관심분야를 먼저 선택해주세요.");
                } else { // "관심분야"를 선택한 경우에는 각 "관심분야"에 맞는 항목 출력
                    builder.setTitle("세부항목을 선택하세요.");
                    //builder.setIcon(R.drawable.ic_menu_gallery);
                    if (selectInterests == 0) { // "관심분야"의 "게임" 선택
                        builder.setMultiChoiceItems(R.array.DetailInterests_Game, selectDetailInterestsGame, new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selectDetailInterestsGame[which] = isChecked;
                            }
                        });
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String[] game = getResources().getStringArray(R.array.DetailInterests_Game);
                                String result = ""; // EditText에 넣어주기 위한 String
                                int numCheckedValue = 0; // 체크되어 있는 항목의 개수

                                // 체크되어 있는 항목이 몇개인지 확인
                                for (int i = 0; i < selectDetailInterestsGame.length; i++) {
                                    if (selectDetailInterestsGame[i]) {
                                        numCheckedValue++;
                                    }
                                }

                                // 체크되어 있는 항목의 개수 (-1)개 만큼 쉼표(,)를 붙여준다.
                                for (int i = 0; i < selectDetailInterestsGame.length; i++) {
                                    if (selectDetailInterestsGame[i]) {
                                        result += game[i];
                                        if (numCheckedValue != 1) {
                                            result += ", ";
                                            numCheckedValue--;
                                        }
                                    }
                                }

                                edtDetailInterests.setText(result); // "세부항목" EditText value 변경
                                edtNumPeople.setText(""); // "인원" EditText value 초기화
                            }
                        });
                    } else if (selectInterests == 1) { // "관심분야"의 "식사"

                    }
                    builder.setNegativeButton("취소", null);
                }
                builder.show();
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

    public void UpdateSearch(String strInterests, String strNumPeople) {
        class UpdateSearchTask extends AsyncTask<String, Void, String> {

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


            }protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

        }

        UpdateSearchTask updateSearchTask = new UpdateSearchTask();
        updateSearchTask.execute(strInterests, strNumPeople);
    }

    // set "관심분야"
    public void setstrInterests(String strInterests) {
        m_strInterests = strInterests;
    }

    // set "세부항목"
    public void setstrDetailInterests(String strDetailInterests) {
        m_strDetailInterests = strDetailInterests;
    }

    // set "인원"
    public void setstrNumPeople(String strNumPeople) {
        m_strNumPeople = strNumPeople;
    }

    // get "관심분야"
    public String getstrInterests() {
        return m_strInterests;
    }

    // get "세부항목"
    public String getstrDetailInterests() {
        return m_strDetailInterests;
    }

    // get "인원"
    public String getstrNumPeople() {
        return m_strNumPeople;
    }
}
<<<<<<< HEAD
*/
=======
*/
>>>>>>> 73600f2419ee5a7ccc7613bc260e789427508c9b
