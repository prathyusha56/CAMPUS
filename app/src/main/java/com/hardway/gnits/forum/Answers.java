package com.hardway.gnits.forum;

/**
 * Created by DELL on 18-07-2016.
 */
public class Answers {

    private String answer,answered_by,timestamp;
    int answered_by_id,answer_id;

    public Answers()
    {

    }

    public Answers(int answer_id, int answered_by_id, String answer, String answered_by, String timestamp){
        this.answer_id = answer_id;
        this.answered_by_id = answered_by_id;
        this.answer=answer;
        this.answered_by = answered_by;
        this.timestamp = timestamp;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public int getAnswered_by_id() {
        return answered_by_id;
    }

    public void setAnswered_by_id(int answered_by_id) {
        this.answered_by_id = answered_by_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerd_by() {
        return answered_by;
    }

    public void setAnswerd_by(String answered_by) {
        this.answered_by = answered_by;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timestamp) {
        this.timestamp = timestamp;
    }

}