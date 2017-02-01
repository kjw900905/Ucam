package com.example.Activity;


import android.graphics.drawable.StateListDrawable;
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

//import com.example.kjw90.ucam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {
    GridView gridView;
    int viewHeight = 1;
    TimeTableAdapter timeTableAdapter;
    TextView t1;

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

        gridView = (GridView) view.findViewById(R.id.grid_timetable);
        gridView.setSelector(new StateListDrawable());
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "   "+ position, Toast.LENGTH_SHORT).show();
                timeTableAdapter.setSelectedPosition(position);
                timeTableAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}