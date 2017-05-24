package com.hardway.gnits.dl;

/**
 * Created by karth on 1/9/2017.
 */

public class Album {
    private String name;
    private String numOfSongs;
    private int thumbnail;
    String author,descr;

    public Album() {
    }

    public Album(String name, int thumbnail,String author,String descr) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.author = author;
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }


}