package com.hardway.gnits.login;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hardway.gnits.MainActivity;
import com.hardway.gnits.R;
import com.hardway.gnits.databaseoffline.Login_DONE;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
Button bt1;
    private static final String TAG = Login.class.getSimpleName();
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail,inputLayoutPassword;
    private ProgressBar spinner;
    Login_DONE login_done;
    LinearLayout linearLayout;

    TextView forgot_password,continue_without_password;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        login_done = new Login_DONE(getActivity());

        forgot_password = (TextView) rootView.findViewById(R.id.forgot_password);

        forgot_password.setPaintFlags(forgot_password.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        continue_without_password = (TextView) rootView.findViewById(R.id.continue_without_login);

        continue_without_password.setPaintFlags(continue_without_password.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        bt1 = (Button) rootView.findViewById(R.id.btnLogin);
        inputEmail = (EditText) rootView.findViewById(R.id.input_email);
        inputPassword = (EditText) rootView.findViewById(R.id.input_password);
        inputLayoutEmail = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_login);
        spinner = (ProgressBar) rootView.findViewById(R.id.progressBar_Login);

        spinner.setVisibility(View.GONE);

        checkAndRequestPermissions();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();

            }
        });

        continue_without_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_done.setLoggedIn(true);

                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Login_Popup myDialogFragment = new Login_Popup();
                myDialogFragment.show(fm, "dialog_fragment");

            }
        });


        return rootView;
    }

    private void submitForm() {

        linearLayout.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        if (!validateEmail()) {
            linearLayout.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            return;
        }

        if (!validatePassword()) {
            linearLayout.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            return;
        }

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();


        Log.d("email and password",email+"\n"+password);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
try {
    JSONObject jsonResponse = new JSONObject(response);
    boolean success = jsonResponse.getBoolean("success");

    if(success)
    {

        int id = jsonResponse.getInt("id");
        String name = jsonResponse.getString("name");
        String dob = jsonResponse.getString("dob");
        String mail = jsonResponse.getString("mail");
        String year = jsonResponse.getString("year");
        String password = jsonResponse.getString("password");
        String latitude = jsonResponse.getString("latitude");
        String longitude = jsonResponse.getString("longitude");
        String pass = jsonResponse.getString("pass");
        String created = jsonResponse.getString("created");
        String updated = jsonResponse.getString("updated");
        int questions_asked = jsonResponse.getInt("questions_asked");
        int questions_answered = jsonResponse.getInt("questions_answered");
        int questions_accepted_form = jsonResponse.getInt("questions_accepted");
        String aadhar = jsonResponse.getString("adhar");
        int course = jsonResponse.getInt("course");
        int group = jsonResponse.getInt("group");
        String cid = jsonResponse.getString("cid");


        Log.d("questions_asked",questions_asked+"");

        SharedPreferences pref = getActivity().getSharedPreferences("Downloaded_Values", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id_DOWNLOAD",id);
        editor.putString("name_DOWNLOAD",name);
        editor.putString("dob_DOWNLOAD",dob);
        editor.putString("mail_DOWNLOAD",mail);
        editor.putString("year_DOWNLOAD",year);
        editor.putString("password_DOWNLOAD",password);
        editor.putFloat("latitude_DOWNLOAD", Float.parseFloat(latitude));
        editor.putFloat("longitude_DOWNLOAD", Float.parseFloat(longitude));
        editor.putInt("pass_DOWNLOAD", Integer.parseInt(pass));
        editor.putString("created_DOWNLOAD",created);
        editor.putString("updated_DOWNLOAD",updated);
        editor.putInt("questions_asked_DOWNLOAD",questions_asked);
        editor.putInt("questions_answered_DOWNLOAD",questions_answered);
        editor.putInt("questions_accepted_DOWNLOAD",questions_accepted_form);
        editor.putInt("course_DOWNLOAD",course);
        editor.putInt("group_DOWNLOAD",group);
        editor.putString("aadhar_DOWNLOAD",aadhar);
        editor.putString("cid_DOWNLOAD",cid);
        editor.commit();
        login_done.setLoggedIn(true);
        spinner.setVisibility(View.GONE);
        Intent i = new Intent(getActivity(),MainActivity.class);
        startActivity(i);
        getActivity().finish();


    }
    else {
        linearLayout.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Invalid Email or Password")
                .setNegativeButton("Retry",null)
                .create().show();

    }
}
     catch (JSONException e)
     {
    e.printStackTrace();
    }
            }
        };


        LoginRequest loginRequest = new LoginRequest(email,password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(loginRequest);
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
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }


    private  void checkAndRequestPermissions() {
        String [] permissions=new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(),permission )!= PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }



}
