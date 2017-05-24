package com.hardway.gnits.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12-07-2016.
 */
public class Set_Forget_mail_Request extends StringRequest {

    private static final String Question_REGISTER_REQUEST_URL = "http://highskidevelopers.com/cflash/setforget.php";
    private Map<String, String> params;

    public Set_Forget_mail_Request(String mail, String password, Response.Listener<String> listener)
    {
    super(Method.POST, Question_REGISTER_REQUEST_URL , listener, null);
    params = new HashMap<>();
        params.put("email",mail);
        params.put("password",password);

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
