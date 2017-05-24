package com.hardway.gnits;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12-07-2016.
 */
public class QuestionRegisterRequest extends StringRequest {

    private static final String Question_REGISTER_REQUEST_URL = "http://highskidevelopers.com/CAMPUS/php/forum/QuestionRequest.php";
    private Map<String, String> params;

    public QuestionRegisterRequest(int id, String question_head, String Question, String Tag, long timestamp, int group,Response.Listener<String> listener)
    {
    super(Method.POST, Question_REGISTER_REQUEST_URL , listener, null);
    params = new HashMap<>();
        params.put("person_id",id+"");
        params.put("question_head",question_head);
        params.put("Question",Question);
        params.put("Tag",Tag);
        params.put("timestamp",timestamp+"");
        params.put("gro",group+"");
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
