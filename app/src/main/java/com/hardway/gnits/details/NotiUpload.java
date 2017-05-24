package com.hardway.gnits.details;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hardway.gnits.JSONParser;
import com.hardway.gnits.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotiUpload extends AppCompatActivity {
 RadioGroup radioGroup;
    int count =1;
    EditText title_upload,desc_upload;
    String title,desc;

    SharedPreferences pref = getSharedPreferences("Downloaded_Values", MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();

    String name = pref.getString("name_DOWNLOAD","");

    private Button bLogin;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://www.highskidevelopers.com/CAMPUS/php/loads.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";



 Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_upload);

        title_upload = (EditText) findViewById(R.id.title_upload);
        desc_upload = (EditText) findViewById(R.id.title_desc);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        bt = (Button) findViewById(R.id.button3);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                title = title_upload.getText().toString().trim();
                desc = desc_upload.getText().toString().trim();



            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.rb1:
                        count =1;
                        break;
                    case R.id.rb2:
                        count =2;
                        break;
                    case R.id.rb3:
                        count=3;
                        break;
                    case R.id.rb4:
                        count = 4;
                        break;
                }

            }
        });

    }

    class AttemptLogin extends AsyncTask<String, String, String>
    {
        /** * Before starting background thread Show Progress Dialog * */

    boolean failure = false;
        @Override protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(NotiUpload.this);
            pDialog.setMessage("Please wait few moments...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args)
        { // TODO Auto-generated method stub
            // here Check for success tag
            int success;

            long millis = System.currentTimeMillis();

            try { List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("title", name));
                params.add(new BasicNameValuePair("description", desc));
                params.add(new BasicNameValuePair("ts",millis+""));
                params.add(new BasicNameValuePair("type",count+""));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest( LOGIN_URL, "GET", params);
                // checking log for json response
                Log.d("Login attempt", json.toString());
                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {

                    return json.getString(TAG_MESSAGE);
                }else{ return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        } /** * Once the background process is done we need to Dismiss the progress dialog asap * **/
    protected void onPostExecute(String message)
    {
        pDialog.dismiss();
        if (message != null)
        { Toast.makeText(NotiUpload.this, message, Toast.LENGTH_LONG).show();
        }
    }
    }

}
