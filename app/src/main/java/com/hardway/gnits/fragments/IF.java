package com.hardway.gnits.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hardway.gnits.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IF extends Fragment implements View.OnClickListener{

    TextView plus_1,plus_2,plus_3,plus_4,minus_1,minus_2,minus_3,minus_4,zero_1,zero_2,zero_3,zero_4;
    EditText n,time;
    Button submit_food;

    int cc ,cf, bn, c;

    public IF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_i, container, false);

        plus_1 = (TextView) v.findViewById(R.id.plus_1);
        plus_2 = (TextView) v.findViewById(R.id.plus_2);
        plus_3 = (TextView) v.findViewById(R.id.plus_3);
        plus_4 = (TextView) v.findViewById(R.id.plus_4);
        minus_1 = (TextView) v.findViewById(R.id.minus_1);
        minus_2 = (TextView) v.findViewById(R.id.minus_2);
        minus_3 = (TextView) v.findViewById(R.id.minus_3);
        minus_4 = (TextView) v.findViewById(R.id.minus_4);
        zero_1 = (TextView) v.findViewById(R.id.zero_1);
        zero_2 = (TextView) v.findViewById(R.id.zero_2);
        zero_3 = (TextView) v.findViewById(R.id.zero_3);
        zero_4 = (TextView) v.findViewById(R.id.zero_4);

        cc=0;
        cf=0;
        bn=0;
        c=0;

        submit_food = (Button) v.findViewById(R.id.submit_food);
        n = (EditText) v.findViewById(R.id.input_no);
        time = (EditText) v.findViewById(R.id.input_time);

        plus_1.setOnClickListener(this);
        plus_2.setOnClickListener(this);
        plus_3.setOnClickListener(this);
        plus_4.setOnClickListener(this);
        minus_1.setOnClickListener(this);
        minus_2.setOnClickListener(this);
        minus_3.setOnClickListener(this);
        minus_4.setOnClickListener(this);
        submit_food.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.plus_1:

                cc++;
                zero_1.setText(cc+"");

                break;

            case R.id.plus_2:

                cf++;
                zero_2.setText(cf+"");

                break;

            case R.id.plus_3:

                bn++;
                zero_3.setText(bn+"");

                break;

            case R.id.plus_4:

                c++;
                zero_4.setText(c+"");

                break;
            case R.id.minus_1:

                cc--;
                zero_1.setText(cc+"");

                break;

            case R.id.minus_2:

                cf--;
                zero_2.setText(cf+"");

                break;

            case R.id.minus_3:

                bn--;
                zero_3.setText(bn+"");

                break;

            case R.id.minus_4:

                c--;
                zero_4.setText(c+"");

                break;

            case R.id.submit_food:

                zero_1.setText(0+"");
                zero_2.setText(0+"");
                zero_3.setText(0+"");
                zero_4.setText(0+"");
                n.setText("");
                time.setText("");


                break;



        }
    }
}
