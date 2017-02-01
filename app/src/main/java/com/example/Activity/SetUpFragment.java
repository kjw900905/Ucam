package com.example.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.Activity.R;


public class SetUpFragment extends Fragment {

    public SetUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_up, container, false);

        RadioGroup rg = (RadioGroup)view.findViewById(R.id.select_Color);

        int radioCheck = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) container.findViewById(radioCheck);

        TextView testColor = (TextView)view.findViewById(R.id.color_Test);

        testColor.setBackgroundColor(rb.getDrawingCacheBackgroundColor());

        return view;
    }

}
