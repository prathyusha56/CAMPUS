package com.hardway.gnits.login;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hardway.gnits.R;
import com.hardway.gnits.databaseoffline.Login_DONE;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {
    private static final String TAG = Register.class.getSimpleName();
    private EditText inputName, inputDob, inputEmail, inputCollege, inputPassword, inputRepassword,collegeid,adhar;
    private TextInputLayout inputLayoutName,inputLayoutDob, inputLayoutEmail, inputLayoutCollege ,inputLayoutPassword ,inputLayoutRepassword;
Button register;
    private ProgressDialog pDialog;
    AlertDialog.Builder alertDialog;
    private ProgressBar spinner;
    Calendar myCalendar = Calendar.getInstance();
    ScrollView scrollView;
    Login_DONE login_done;
    int course,group;

    int timesCalled = 1;
    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        login_done = new Login_DONE(getActivity());

        inputLayoutName = (TextInputLayout) rootView.findViewById(R.id.input_layout_name);
        inputLayoutDob = (TextInputLayout) rootView.findViewById(R.id.input_layout_dob);
        inputLayoutEmail = (TextInputLayout) rootView.findViewById(R.id.input_layout_email1);
        inputLayoutCollege = (TextInputLayout) rootView.findViewById(R.id.input_layout_college);
        inputLayoutPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_password1);
        inputLayoutRepassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_repassword);


        inputName = (EditText) rootView.findViewById(R.id.input_name);
        inputDob = (EditText) rootView.findViewById(R.id.input_dob);
        inputEmail = (EditText) rootView.findViewById(R.id.input_email1);
        inputCollege = (EditText) rootView.findViewById(R.id.input_college);
        inputPassword = (EditText) rootView.findViewById(R.id.input_password1);
        inputRepassword = (EditText) rootView.findViewById(R.id.input_repassword);
        collegeid = (EditText) rootView.findViewById(R.id.input_rollid);
        adhar = (EditText) rootView.findViewById(R.id.input_aadhar);


        scrollView = (ScrollView) rootView.findViewById(R.id.scroll_Register);


        register = (Button) rootView.findViewById(R.id.btnRegister);

        spinner=(ProgressBar)rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        // Spinner element
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);

        // Spinner click listener
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Group");
        categories.add("CIVIL");
        categories.add("EEE");
        categories.add("MECHANICAL");
        categories.add("ECE");
        categories.add("CSE");
        categories.add("BIO TECHNOLOGY");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                group = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Spinner element
        Spinner spinner_Course = (Spinner) rootView.findViewById(R.id.spinner_course);

        // Spinner click listener
        // Spinner Drop down elements
        List<String> categories_course = new ArrayList<String>();
        categories_course.add("Course");
        categories_course.add("B.Tech");
        categories_course.add("M.TEch");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_c = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories_course);

        // Drop down layout style - list view with radio button
        dataAdapter_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_Course.setAdapter(dataAdapter_c);

        spinner_Course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                course = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

// Session manager


        inputDob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickerDialog();
                }
                return false;
            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();
            }
        });

        return rootView;
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
                inputDob.setText(new StringBuilder().append(year).append("-")
                        .append(monthOfYear + 1).append("-").append(dayOfMonth)); // or whatever you want
            }
            }

    };





    private void submitForm() {

        scrollView.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        if (!validateName()) {
            spinner.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            return;
        }

        if (!validateDob()) {
            spinner.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            return;
        }

        if (!validateEmail()) {
            spinner.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            return;
        }

        if (!validateCollege()) {
            scrollView.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            return;
        }

        if (!validatePassword()) {
            scrollView.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            return;
        }

        if (!validateRePassword()) {
            scrollView.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            return;
        }

        final String name = inputName.getText().toString().trim();
        final String dob = inputDob.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();
        final String college = inputCollege.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        final String id = collegeid.getText().toString().trim();
        final String aadhar = adhar.getText().toString().trim();

        int pass = 1234;
        float latitude = 123;
        float longitude = 124;


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success)
                    {
                        spinner.setVisibility(View.GONE);
                        login_done.setLoggedIn(false);
                        alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setMessage("Your mail is registered. Please click OK button to go to login page");

                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(),LoginRegister.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                        alertDialog.create().show();

                    }
                    else {
                        scrollView.setVisibility(View.VISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        alertDialog.setCancelable(true);
                         builder.setMessage("Email already registered")
                                 .setNegativeButton("OK",null)
                                 .create().show();

                        inputEmail.requestFocus();

                    }
                }
                catch (JSONException e)
                {
                  e.printStackTrace();
                }

            }
        };

        RegisterRequest registerRequest = new RegisterRequest(name,id,dob,email,college,password,latitude,longitude,pass,course,group,aadhar,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(registerRequest);
    }


    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Please enter the name");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCollege() {
        if (inputCollege.getText().toString().trim().isEmpty()) {
            inputLayoutCollege.setError("Please enter the College name");
            requestFocus(inputCollege);
            return false;
        } else {
            inputLayoutCollege.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Please enter the correct mail");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError("Please enter the Password");
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDob() {
        if (inputDob.getText().toString().trim().isEmpty()) {
            inputLayoutDob.setError("Please enter the Date of Birth");
            requestFocus(inputDob);
            return false;
        } else {
            inputLayoutDob.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateRePassword() {

        String r1 =inputRepassword.getText().toString().trim();
        String r2 =inputPassword.getText().toString().trim();
        if (!((r1).equals(r2))) {
            inputLayoutRepassword.setError("Passwords do not match");
            requestFocus(inputRepassword);
            return false;
        } else {
            inputLayoutRepassword.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_dob:
                    validateDob();
                    break;
                case R.id.input_email1:
                    validateEmail();
                    break;
                case R.id.input_college:
                    validateCollege();
                    break;
                case R.id.input_password1:
                    validatePassword();
                    break;
                case R.id.input_repassword:
                    validateName();
                    break;
            }
        }
    }



}
