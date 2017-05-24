package com.hardway.gnits.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12-07-2016.
 */
public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://highskidevelopers.com/CAMPUS/php/forum/login.php";
    private Map<String, String> params;

    public LoginRequest(String mail, String password, Response.Listener<String> listener)
    {
    super(Method.POST, LOGIN_REQUEST_URL , listener, null);
    params = new HashMap<>();
        params.put("mail",mail);
        params.put("password",password);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
