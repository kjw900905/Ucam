package com.example.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

import static com.example.Activity.R.id.listView;

public class SearchSchoolDialog extends Activity {
    String myJSON;
    String school;
    String schoolName;

    // php 주소 구성 : "http://(서버 주소)/(php 파일명 + 확장자명)"
    // php 주소 형식 : "http://xxx.xxx.xxx.xxx/xxxxx.php"
    // php 주소 예시 : "http://221.148.86.18/SelectAll.php"
    private static final String SERVER_URL = "http://192.168.0.13/";
    private static final String SELECT_ONE_PHP = "SelectOne_search_school.php";

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_school_dialog);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 90%
        int height = (int) (display.getHeight() * 0.7);//Display 사이즈의 70%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
        this.setFinishOnTouchOutside(false);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickSelectOne(View v) {
        list = (ListView) findViewById(listView);
        personList = new ArrayList<HashMap<String, String>>();

        EditText edtId = (EditText) findViewById(R.id.edtId);

        String strId = edtId.getText().toString();

        SelectOne(strId);
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

                    URL url = new URL(SERVER_URL + SELECT_ONE_PHP);
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
                showList();
            }
        }

        SelectOneTask selectOneTask = new SelectOneTask();
        selectOneTask.execute(strId);
    }

    public void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    SearchSchoolDialog.this, personList, R.layout.list_item,
                    new String[]{TAG_ID},
                    new int[]{R.id.id}
            );

            list.setAdapter(adapter);
            list.setOnItemClickListener(itemClickListenerOfSchoolList);
            //personList.clear();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
    AdapterView.OnItemClickListener itemClickListenerOfSchoolList = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long id){

            school = adapterView.getAdapter().getItem(pos).toString();
            schoolName =  school.substring(4, school.length()-1);
            //Toast.makeText(getApplicationContext(), schoolName, Toast.LENGTH_SHORT).show();
        }
    };

    public void setUniversity(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        intent.putExtra("name", schoolName);
        this.setResult(RESULT_OK, intent);
        finish();
    }
}