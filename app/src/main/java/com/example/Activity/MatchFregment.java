package com.example.Activity;

import android.support.v4.app.Fragment;

/*public class MatchFregment extends Fragment {
    // php 주소 구성 : "http://(서버 주소)/(php 파일명 + 확장자명)"
    // php 주소 형식 : "http://xxx.xxx.xxx.xxx/xxxxx.php"
    // php 주소 예시 : "http://221.148.86.18/SelectAll.php"
    /*private static final String SERVER_URL = "http://117.17.158.173/";
    private static final String UPDATE_PHP = "UpdateSearch.php";

    public void MatchFragment() {
        // null
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        return view;
    }

    // "관심분야"의 "선택" 버튼 클릭
    public void btnInterestsClick(View view) {

    }

    // "세부항목"의 "선택" 버튼 클릭
    public void btnDetailInterestsClick(View view) {

    }

    // "인원 수"의 "선택" 버튼 클릭
    public void btnNumPeopleClick(View view) {

    }

    // "찾기" 버튼 클릭
    public void btnSearchClick(View view) {
        EditText edtInterests = (EditText)findViewById(R.id.edtInterests);
        EditText edtNumPeople = (EditText)findViewById(R.id.edtNumPeople);

        String strInterests = edtInterests.getText().toString();
        String strNumPeople = edtNumPeople.getText().toString();

        UpdateSearch(strInterests, strNumPeople);
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
                    URL url = new URL(SERVER_URL + UPDATE_PHP);
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
<<<<<<< HEAD
    }

}*/
=======
    }*/
}
>>>>>>> c2ecd09f882427815a09efc376c042f41383ae8d
