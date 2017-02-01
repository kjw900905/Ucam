package com.example.Activity;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class TimeTableAdapter extends BaseAdapter {

    private Context context;
    private int rootViewHeight;
    private int dayViewHeight;
    private int selectedPosition = -1;

    public TimeTableAdapter(Context context, int rootViewHeight, int dayViewHeight) {
        this.context = context;
        this.rootViewHeight = rootViewHeight;
        this.dayViewHeight = dayViewHeight;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.timetable_layout, null);
            int cellHeight = (rootViewHeight - dayViewHeight) / 12 - dpToPx(1);
            gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cellHeight));

            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_TextView);

            if (position == selectedPosition) {
                gridView.setBackgroundColor(Color.BLACK);
            } else {
                gridView.setBackgroundColor(Color.TRANSPARENT);
            }
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
}