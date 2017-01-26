package com.example.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TimeTableAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public TimeTableAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.timetable_layout, null);
            //ViewGroup.LayoutParams mParams = convertView.getLayoutParams();
            //System.out.print(gridView.getWidth() + " " + gridView.getHeight());

            gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_TextView);
            textView.setText(mobileValues[position]);

            String mobile = mobileValues[position];

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    /*private int getCellWidthDP() {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    private int getCellHeightDP(){
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    } */

    @Override
    public int getCount() {

        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }
}