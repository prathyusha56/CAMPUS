package com.hardway.gnits.forum;

/**
 * Created by karth on 4/19/2017.
 */

public class Dept_Details {
    private String title;
    int id;
    public Dept_Details() {
    }

    public Dept_Details(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}