package com.hardway.gnits.dn;

/**
 * Created by karth on 07-08-2016.
 */
public class Parts {

    private int id;
    private String heading;
    private String about;
    private String request;
    private String thumbnail;

    public Parts()
    {

    }

    public Parts(int id, String heading, String about, String request, String thumbnail)
    {
        this.id = id;
        this.heading = heading;
        this.about = about;
        this.request = request;
        this.thumbnail = thumbnail;
    }

        public int getId()
      {
       return id;
       }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getHeading()
    {
        return heading;
    }

    public void setHeading(String heading)
    {
        this.heading = heading;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public String getRequest()
    {
        return request;
    }

    public void setRequest(String request)
    {
        this.request = request;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
