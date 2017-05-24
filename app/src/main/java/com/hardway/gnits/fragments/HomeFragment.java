package com.hardway.gnits.fragments;


import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hardway.gnits.AppController;
import com.hardway.gnits.QuestionRegisterRequest;
import com.hardway.gnits.R;
import com.hardway.gnits.connection_check.ConnectivityReceiver;
import com.hardway.gnits.connection_check.MyApplication;
import com.hardway.gnits.databaseoffline.CreateDatabase;
import com.hardway.gnits.login.LoginRegister;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Activity implements ConnectivityReceiver.ConnectivityReceiverListener{

    EditText home_question_head,home_question;
    private TextInputLayout input_home_question_head,input_home_question,input_home_tag;
    Button home_question_button;
    SharedPreferences KEY_PREF;

    String keyword;
    Cursor cursor = null;
    int id;
    Cursor cursorget_data = null,cursorget_quiz = null,crs= null;
    CardView cardView;
    SharedPreferences pref;
    private String URL_FEED = "http://www.highskidevelopers.com/cflash/getdata.php";
    //private String URL_FEED_QUIZ = "http://www.highskidevelopers.com/cflash/getdata_quiz.php";
    private static final String TAG = HomeFragment.class.getSimpleName();
    ProgressDialog progressDialog,progressDialog1;
    Context context;
    int person_id;
    int group;
    Button register_home;
     String[] keywords = {"something"};
    Boolean connection;
    AutoCompleteTextView home_tag;
    LinearLayout with_login,without_login;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);


        group = 0;

        cardView = (CardView)  findViewById(R.id.cardview_question_ask);

        home_question_head = (EditText)  findViewById(R.id.home_question_head);
        home_question = (EditText)  findViewById(R.id.home_question);
        home_tag = (AutoCompleteTextView)  findViewById(R.id.home_tag);

        input_home_question_head = (TextInputLayout)  findViewById(R.id.input_home_question_head);
        input_home_question = (TextInputLayout)  findViewById(R.id.input_home_question);
        input_home_tag = (TextInputLayout)  findViewById(R.id.input_home_tag);

        home_question_button = (Button)  findViewById(R.id.home_question_button);

        with_login = (LinearLayout)  findViewById(R.id.with_login);

        without_login = (LinearLayout)  findViewById(R.id.without_login);


        register_home = (Button)  findViewById(R.id.register_home);


        pref =  HomeFragment.this.getSharedPreferences("Downloaded_Values",  HomeFragment.this.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        person_id = pref.getInt("id_DOWNLOAD", 0);

        if(person_id == 0)
        {
            with_login.setVisibility(View.GONE);
            without_login.setVisibility(View.VISIBLE);
        }else {
            with_login.setVisibility(View.VISIBLE);
            without_login.setVisibility(View.GONE);
        }

        register_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( HomeFragment.this, LoginRegister.class);
                startActivity(i);



            }
        });


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner_home);

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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(HomeFragment.this, android.R.layout.simple_spinner_item, categories);

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





        home_question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean get_connection = checkConnection();

                if(get_connection) {

                  if(group == 0) {
                      Toast.makeText(getApplicationContext(), "Please select a branch", Toast.LENGTH_LONG).show();
                      return;
                  }
                    else {
                      submitForm();
                  }
                  }
                else
                {
                    getAlert();
                }

            }
        });



    }



    private void submitForm() {


        if (!validateHead()) {

            return;
        }

        if (!validateQuestion()) {

            return;
        }

        if (!validateTag()) {

            return;
        }


        String question_head_string = home_question_head.getText().toString().trim();
        String question_string = home_question.getText().toString().trim();
        String question_tag_string = home_tag.getText().toString().trim();

        long timestamp = System.currentTimeMillis();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            int colorFrom = getResources().getColor(R.color.colorPrimaryDark);
                            int colorTo = getResources().getColor(R.color.back);
                            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                            colorAnimation.setDuration(1000); // milliseconds
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    cardView.setBackgroundColor((int) animator.getAnimatedValue());
                                }

                            });
                            colorAnimation.start();

                            Toast.makeText( HomeFragment.this, "Question is posted successfully", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

        QuestionRegisterRequest registerRequest = new QuestionRegisterRequest(person_id, question_head_string, question_string, question_tag_string, timestamp, group,responseListener);

        RequestQueue queue = Volley.newRequestQueue( HomeFragment.this);
        queue.add(registerRequest);

    }


    private boolean validateHead() {
        if (home_question_head.getText().toString().trim().isEmpty()) {
            input_home_question_head.setError("Please enter the Question Title");
            requestFocus(home_question_head);
            return false;
        } else {
            input_home_question_head.setErrorEnabled(false);
        }

        return true;
    }




private boolean validateQuestion() {
        if (home_question.getText().toString().trim().isEmpty()) {
            input_home_question.setError("Please enter the Question");
            requestFocus(home_question);
            return false;
        } else {
            input_home_question.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTag() {
        if (home_tag.getText().toString().trim().isEmpty()) {
            input_home_tag.setError("Please enter the Tag");
            requestFocus(home_tag);
            return false;
        } else {
            input_home_tag.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
             HomeFragment.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.home_question_head:
                    validateHead();
                    break;
                case R.id.home_question:
                    validateQuestion();
                    break;
                case R.id.home_tag:
                    validateQuestion();
                    break;
            }
        }
    }





    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

   return isConnected;
    }


    @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
      MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
      public void checkAlways()
    {
        connection = checkConnection();
        if(connection) {


        }
        else
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder( HomeFragment.this);

            // Setting Dialog Title
            alertDialog.setTitle("NO INTERNET CONNECTION");

            // Setting Dialog Message
            alertDialog.setMessage("Do you want to switch on the Internet?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_import_export_white_48dp);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                       checkAlways();
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();


        }
    }


    public void checkAlways_Quiz()
    {
        connection = checkConnection();
        if(connection) {

  //          getQuiz();

        }
        else
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder( HomeFragment.this);

            // Setting Dialog Title
            alertDialog.setTitle("NO INTERNET CONNECTION");

            // Setting Dialog Message
            alertDialog.setMessage("Do you want to switch on the Internet?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_import_export_white_48dp);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    checkAlways();
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();


        }
    }


    public void getAlert()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder( HomeFragment.this);

        // Setting Dialog Title
        alertDialog.setTitle("NO INTERNET CONNECTION");

        // Setting Dialog Message
        alertDialog.setMessage("Please Switch on Internet and try again.\nDo you want to switch on the Internet?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_import_export_black_48dp);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                getAlert();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

        // alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);

    }

}
