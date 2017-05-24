package com.hardway.gnits.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hardway.gnits.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by karth on 18-09-2016.
 */

public class Login_Popup extends DialogFragment {

    EditText password_email_forgot,input_dob_forgot,password_forgot_update,password_forgot_update_1;
    Button btn_forgot_request,save,ok;
    LinearLayout linear_forgot,linear_forgot_1;
    String email,dob,password,repassword;
    Calendar myCalendar = Calendar.getInstance();
    ProgressDialog progressDialog;
    int timesCalled = 1;
    AlertDialog.Builder alertDialog;
    ImageView tick;
    TextView forgot_text_1,forgot_text_2,change_password;
    private ProgressBar spinner;
    Animation animFadein;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password, container);
        // This shows the title, replace My Dialog Title. You can use strings too.
        //  getDialog().setTitle("My Dialog Title");
        // If you want no title, use this code
        getDialog().setTitle("Reset Password");

        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);


        password_email_forgot = (EditText) view.findViewById(R.id.password_email_forgot);
        input_dob_forgot = (EditText) view.findViewById(R.id.input_dob_forgot);
        password_forgot_update = (EditText) view.findViewById(R.id.password_forgot_update);
        password_forgot_update_1 = (EditText) view.findViewById(R.id.password_forgot_update_1);

        btn_forgot_request = (Button) view.findViewById(R.id.btn_forgot_request);
        save = (Button) view.findViewById(R.id.save);

        ok = (Button) view.findViewById(R.id.ok);

        linear_forgot = (LinearLayout) view.findViewById(R.id.linear_forgot);
        linear_forgot_1 = (LinearLayout) view.findViewById(R.id.linear_forgot_1);

        change_password = (TextView) view.findViewById(R.id.change_password);

        forgot_text_1 = (TextView) view.findViewById(R.id.forgot_text_1);

        forgot_text_2 = (TextView) view.findViewById(R.id.forgot_text_2);

        tick = (ImageView) view.findViewById(R.id.tick);


        spinner=(ProgressBar)view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        linear_forgot_1.setVisibility(View.GONE);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });



        btn_forgot_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_forgot.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);

                email = password_email_forgot.getText().toString().trim();

                if(email.equals(""))
                {
                    //Toast.makeText(getActivity(),"The Email field cannot be null",Toast.LENGTH_SHORT).show();
                    linear_forgot.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    forgot_text_1.setText("The Email field cannot be null");
                    return;
                }

                dob = input_dob_forgot.getText().toString().trim();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            linear_forgot.setVisibility(View.GONE);
                            spinner.setVisibility(View.VISIBLE);

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            {


                                spinner.setVisibility(View.GONE);
                                linear_forgot.setVisibility(View.GONE);
                                linear_forgot_1.setVisibility(View.VISIBLE);
                            }
                            else {
                                linear_forgot.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);

                                forgot_text_1.setText("Details does not match");
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                };

                Get_Forget_mail_Request registerRequest = new Get_Forget_mail_Request(email,dob,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(registerRequest);

            }
        });


        input_dob_forgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickerDialog();
                }
                return false;
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_forgot_1.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);

                password = password_forgot_update.getText().toString().trim();

                if(password.equals(""))
                {
                //    Toast.makeText(getActivity(),"The Password field cannot be null",Toast.LENGTH_SHORT).show();

                    linear_forgot_1.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    forgot_text_2.setText("The Password field cannot be null");

                    password_forgot_update.requestFocus();
                    return;
                }

                repassword = password_forgot_update_1.getText().toString().trim();

                if(repassword.equals(""))
                {
                   // Toast.makeText(getActivity(),"The Password Again field cannot be null",Toast.LENGTH_SHORT).show();
                    linear_forgot_1.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    forgot_text_2.setText("The Repassword field cannot be null");

                    password_forgot_update_1.requestFocus();
                    return;
                }else if(!repassword.equals(password))
                {

                    linear_forgot_1.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    forgot_text_2.setText("The Passwords do not match");

                    //Toast.makeText(getActivity(),"The Passwords do not match",Toast.LENGTH_SHORT).show();
                    password_forgot_update_1.requestFocus();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            linear_forgot_1.setVisibility(View.GONE);
                            spinner.setVisibility(View.VISIBLE);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            {

                                linear_forgot_1.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                                change_password.setVisibility(View.GONE);

                                tick.setVisibility(View.VISIBLE);

                                ok.setVisibility(View.VISIBLE);

                                password_forgot_update.setVisibility(View.GONE);
                                password_forgot_update_1.setVisibility(View.GONE);
                                save.setVisibility(View.GONE);

                                forgot_text_2.setVisibility(View.GONE);

                               // forgot_text_2.setText("The Password is changed successfully");
                               // forgot_text_2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                            }
                            else {
                                linear_forgot_1.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                                forgot_text_2.setText("The Password is not changed. Please try again later.");

                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                };

                Set_Forget_mail_Request registerRequest = new Set_Forget_mail_Request(email,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(registerRequest);

            }
        });

        return view;

    }


    public void showDatePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            timesCalled += 1;
            if ((timesCalled % 2) == 0) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                input_dob_forgot.setText(new StringBuilder().append(year).append("-")
                        .append(monthOfYear + 1).append("-").append(dayOfMonth)); // or whatever you want
            }
        }

    };





}
