package com.hardway.gnits.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12-07-2016.
 */
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://highskidevelopers.com/CAMPUS/php/forum/register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String id, String dob, String mail, String college, String password, float latitude, float longitude, int pass, int course, int group, String aadhar,Response.Listener<String> listener)
    {
    super(Method.POST, REGISTER_REQUEST_URL , listener, null);
    params = new HashMap<>();
        params.put("name",name);
        params.put("id",id);
        params.put("dob",dob);
        params.put("mail",mail);
        params.put("year",college);
        params.put("password",password);
        params.put("latitude",latitude+"");
        params.put("longitude",longitude+"");
        params.put("pass",pass+"");
        params.put("course",course+"");
        params.put("group",group+"");
        params.put("aadhar",aadhar);
        params.put("st","0");
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
