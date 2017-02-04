package com.example.Activity;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Beans.TimeTableDetail;

import java.util.ArrayList;

public class TimeTableAdapter extends BaseAdapter {

    private Context context;
    private int rootViewHeight;
    private int dayViewHeight;
    private int selectedPosition = -1;
    private ArrayList<TimeTableDetail> arrayTimeTableDetail = new ArrayList<TimeTableDetail>();
    private TimeTableDetail timeTableDetail;

    public TimeTableAdapter(Context context, int rootViewHeight, int dayViewHeight) {
        this.context = context;
        this.rootViewHeight = rootViewHeight;
        this.dayViewHeight = dayViewHeight;
        //notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        Toast.makeText(convertView.getContext(), ""+position, Toast.LENGTH_SHORT).show();

        View gridView;


        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.timetable_layout, null);

            /*if(position==2){
                //gridView.setBackgroundColor(Color.BLUE);
                //Toast.makeText(gridView.getContext(), "sds"+ position, Toast.LENGTH_SHORT).show();
                //TextView textView = (TextView)gridView.findViewById(R.id.grid_TextView);
                //textView.setBackgroundColor(Color.BLUE);
            }*/

            for(int i=0; i<arrayTimeTableDetail.size(); i++){
                if(arrayTimeTableDetail.get(i).getfield().equals("Y")){
                    Log.d(arrayTimeTableDetail.get(i).getposition(), "실행");
                    Log.w(arrayTimeTableDetail.get(i).getposition(), "실행");
                    Log.i(arrayTimeTableDetail.get(i).getposition(), "실행");
                    Log.e(arrayTimeTableDetail.get(i).getposition(), "실행");
                    //Toast.makeText(gridView.getContext(), arrayTimeTableDetail.get(i).getposition(), Toast.LENGTH_SHORT).show();
                    TextView textView = (TextView)gridView.findViewById(R.id.grid_TextView);
                    textView.setBackgroundColor(Color.BLUE);
                }
            }


            int cellHeight = (rootViewHeight - dayViewHeight) / 12 - dpToPx(1);
            gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cellHeight));
            //set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_TextView);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getCount() {
        return 60;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedPosition(int position) {

        selectedPosition = position;
    }

    public void setArrayTimeTableDetail(ArrayList<TimeTableDetail> arrayTimeTableDetail){
        this.arrayTimeTableDetail = arrayTimeTableDetail;
    }
}