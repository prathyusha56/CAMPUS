package com.hardway.gnits.others;

/**
 * Created by karth on 4/18/2017.
 */

public class Details {
    private String title;
    private int starting;
    private int ending;
    int id;
    public Details() {
    }

    public Details(int id,String title) {
        this.id = id;
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
