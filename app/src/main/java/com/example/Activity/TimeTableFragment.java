package com.example.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//import com.example.kjw90.ucam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment {
    GridView gridView;
    int viewHeight = 1;
    TimeTableAdapter timeTableAdapter;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.gridview_timetable, container, false);


        gridView = (GridView)view.findViewById(R.id.grid_timetable);
        viewHeight = gridView.getHeight();
        timeTableAdapter = new TimeTableAdapter(view.getContext(), viewHeight);
        gridView.setAdapter(timeTableAdapter);
        Toast.makeText(getContext(), "   "+gridView.getHeight() + " " + viewHeight, Toast.LENGTH_SHORT).show();
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*EditMemInfoFragment editMemInfoFragment = new EditMemInfoFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_in, editMemInfoFragment).addToBackStack(null).commit();*/
                //Toast.makeText(getActivity(), ((TextView)view.findViewById(R.id.grid_TextView)).getText(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "   "+gridView.getHeight() + " " + viewHeight, Toast.LENGTH_SHORT).show();
                timeTableAdapter.setSelectedPosition(position);
                timeTableAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

}
