package com.hardway.gnits.forum;

/**
 * Created by DELL on 14-07-2016.
 */
public class ForumList {
    int question_id,person_id,count;
    String name,Question,timestamp,tag,Question_head;

    public ForumList(){

    }

    public ForumList(int question_id, int person_id, String Question_head, String Question, String tag, String timestamp, String name, int count)
    {
        this.question_id = question_id;
        this.person_id = person_id;
        this.Question = Question;
        this.Question_head = Question_head;
        this.tag = tag;
        this.timestamp = timestamp;
        this.name=name;
        this.count = count;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getQuestion_head() {
        return Question_head;
    }

    public void setQuestion_head(String Question_head) {
        this.Question_head = Question_head;
    }



    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
