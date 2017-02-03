package com.example.Activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Beans.TimeTableDetail;
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

//import com.example.kjw90.ucam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {
    GridView gridView;
    int viewHeight = 1;
    TimeTableAdapter timeTableAdapter;
    TextView t1;
    ArrayList<TimeTableDetail> arrayTimeTableDetail = new ArrayList<TimeTableDetail>();
    String myJSON;
    JSONArray details = null;


    public TimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.gridview_timetable, container, false);


        t1 = (TextView) view.findViewById(R.id.tuesday);
        t1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                timeTableAdapter = new TimeTableAdapter(getContext(), view.getHeight(), t1.getHeight());
                gridView.setAdapter(timeTableAdapter);

                t1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });


        for(int i = 0; i < Variable.m_TIME.length; i++) {
            for(int j = 0; j < Variable.m_DAY.length; j++) {
                TimeTableDetail timeTableDetail = new TimeTableDetail();
                timeTableDetail.setday(Variable.m_DAY[j]);
                timeTableDetail.settime(Variable.m_TIME[i]);
                timeTableDetail.setfield("F");
                arrayTimeTableDetail.add(timeTableDetail);
            }
        }

        // 임시 데이터 넣는 부분입니다.
        //arrayTimeTableDetail.get(0).setfield("Y");
        //arrayTimeTableDetail.get(6).setfield("Y");
        //arrayTimeTableDetail.get(12).setfield("Y");

        gridView = (GridView) view.findViewById(R.id.grid_timetable);

        //View v = gridView.getChildAt(2);
        //Toast.makeText(getActivity(), ""+v, Toast.LENGTH_SHORT).show();
        //TextView a = (TextView)gridView.findViewById(R.id.time1);
        //a.setBackgroundColor(Color.BLUE);
        //v.setBackgroundColor(Color.BLUE);

        gridView.setSelector(new StateListDrawable());
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "   "+gridView.getHeight(), Toast.LENGTH_SHORT).show();
                timeTableAdapter.setSelectedPosition(position);
                timeTableAdapter.notifyDataSetChanged();


                TextView selectedView = (TextView)view.findViewById(R.id.grid_TextView);
                //change_Color(view);
                ColorDrawable colorCode = (ColorDrawable)selectedView.getBackground();
                int color = colorCode.getColor();
                //Toast.makeText(getActivity(),""+color,Toast.LENGTH_SHORT).show();
                if(color == -1){
                    selectedView.setBackgroundColor(Color.BLUE);
                }else{
                    selectedView.setBackgroundColor(Color.WHITE);
                }


                TimeTableDetail timeTableDetail = new TimeTableDetail();
                timeTableDetail = arrayTimeTableDetail.get(position);
            }
        });

        // 여기서부터는 Insert 부분입니다.

        // 여기서부터는 SelectAll 부분입니다.
        SelectAllTimeTableDetail("test");

        return view;
    }
    /*
    public void change_Color(View view){
        TextView test = (TextView)view.findViewById(R.id.grid_TextView);
        ColorDrawable colorCode = (ColorDrawable)test.getBackground();
        int color = colorCode.getColor();
        if(color == 0xFF000000){
            test.setBackgroundColor(Color.BLUE);
        }else{
            test.setBackgroundColor(Color.WHITE);
        }
    }
    */

    public void SelectAllTimeTableDetail(String strid) {
        class SelectAllTimeTableDetailTask extends AsyncTask<String, Void, String> {
            protected String doInBackground(String[] params) {
                String id = (String) params[0];

                try {
                    String data = "";
                    data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    URL url = new URL(Variable.m_SERVER_URL + Variable.m_PHP_SELECTALL_TIME_TABLE_DETAIL);
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
                getData();
            }
        }

        SelectAllTimeTableDetailTask selectAllTimeTableDetailTask = new SelectAllTimeTableDetailTask();
        selectAllTimeTableDetailTask.execute(strid);
    }

    public void getData() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            details = jsonObj.getJSONArray("result");

            //Toast.makeText(getActivity(), "테스트", Toast.LENGTH_SHORT).show();

            for(int i=0; i<details.length(); i++) {
                JSONObject c = details.getJSONObject(i);

                String id = c.getString("id");
                String day = c.getString("day");
                String time = c.getString("time");

                //Toast.makeText(getActivity(), "id: " + id + ", day: " + day + ", time: " + time, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "day: " + arrayTimeTableDetail.get(0).gettime(), Toast.LENGTH_SHORT).show();

                for(int j=0; j<arrayTimeTableDetail.size(); j++) {
                    if(arrayTimeTableDetail.get(j).getday().equals(day) && arrayTimeTableDetail.get(j).gettime().equals(time)) {
                        arrayTimeTableDetail.get(j).setfield("T");
                        Toast.makeText(getActivity(), "요일: " + arrayTimeTableDetail.get(j).getday() +
                                ", 시간: " + arrayTimeTableDetail.get(j).gettime() +
                                ", 필드: " + arrayTimeTableDetail.get(j).getfield(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}