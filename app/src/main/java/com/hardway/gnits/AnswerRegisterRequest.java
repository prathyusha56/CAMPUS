package com.hardway.gnits;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12-07-2016.
 */
public class AnswerRegisterRequest extends StringRequest {

    private static final String Answer_REGISTER_REQUEST_URL = "http://highskidevelopers.com/CAMPUS/php/forum/AnswerRequest.php";
    private Map<String, String> params;

    public AnswerRegisterRequest(int person_id_answered , int question_id, String answer, long timestamp, Response.Listener<String> listener)
    {
    super(Method.POST, Answer_REGISTER_REQUEST_URL , listener, null);
    params = new HashMap<>();
        params.put("question_id",question_id+"");
        params.put("person_id_answered",person_id_answered+"");
        params.put("answer",answer);
        params.put("timestamp",timestamp+"");
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
