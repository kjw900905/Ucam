package com.example.Activity;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTableAdapter extends BaseAdapter {
    private Context context;
    private int height;
    private int selectedPosition = -1;

    public TimeTableAdapter(Context context, int height) {
        this.context = context;
        this.height = height;
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
            //gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 14));


            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_TextView);
            //Toast.makeText(gridView.getContext(), "   "+gridView.getHeight() + " " + height, Toast.LENGTH_SHORT).show();

            //Toast.makeText(gridView.getContext(), ""+context.getResources().getDisplayMetrics().heightPixels, Toast.LENGTH_SHORT).show();

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

    private int getCellHeightDP(){
        int cellheight2 = (context.getResources().getDisplayMetrics().heightPixels - 345) / 11;
        //int height3 = (pxToDp(height2)- height) / 12;
        //int cellHeight = (context.getResources().getDisplayMetrics().heightPixels- height);
        return cellheight2;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }



    @Override
    public int getCount() {
        return 55;
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
