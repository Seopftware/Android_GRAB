package com.example.msi.grab;

/**
 * Created by MSI on 2017-04-24.
 */

public class Calender_Item {

    private String title;
    private int iamge;
    private String location;
    private String notes;

    public void setCalenderIamge(int calenderiamge) {
        iamge = calenderiamge;
    }

    public void setCalenderLocation(String calenderlocation) {
        location = calenderlocation;
    }

    public void setCalenderTitle(String calendertitle) {
        title = calendertitle;
    }

    public void setCalenderNotes(String calendernote) { notes=calendernote; }

    public String getCalenderTitle() {
        return this.title;
    }

    public int getCalenderImage() {
        return this.iamge;
    }

    public String getCalenderLocation() {
        return this.location;
    }

    public String getCalenderNotes() {
        return this.notes;
    }



}




