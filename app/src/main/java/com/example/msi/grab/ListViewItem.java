package com.example.msi.grab;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by MSI on 2017-04-01.
 */

// 아이템에 출력될 데이터를 위한 클래스

public class ListViewItem {

    private Uri image;
    private Bitmap resized;
    private String title; // 제목을 저장하기 위한 private 멤버변수
    private String subtitle; // 부제목을 저장하기 위한 private 멤버변수
    private String subject;


    public ListViewItem() {

        super();
    }

    public ListViewItem(String title, String subtitle) {

        this.title=title;
        this.subtitle=subtitle;

    }

    public ListViewItem(String title, String subtitle, String subject) {

        super();
        this.title=title;
        this.subtitle=subtitle;
        this.subject=subject;

    }

//    public ListViewItem(String title, String subtitle, String subject, Uri image) {
//
//        super();
//        this.image=image;
//        this.title=title;
//        this.subtitle=subtitle;
//        this.subject=subject;
//
//    }
//
    public ListViewItem(String title, String subtitle, String subject, Bitmap resized) {

        super();
        this.resized=resized;
        this.title=title;
        this.subtitle=subtitle;
        this.subject=subject;

    }

//    public Uri getImageUri() {
//        return this.image;
//    }

    public Bitmap getImageBitmap() {
        return this.resized;
    }

    public Uri getImageUri() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitle() {
        return this.subtitle;
    }

    public String getSubject() {
        return this.subject;
    }


    public void setTitle(String titlestr) {
        title=titlestr;
    }

    public void setSubtitle(String subtitlestr) {
        subtitle=subtitlestr;
    }

    public void setSubject(String subjectstr) {
        subject=subjectstr;
    }

}



/*
//     public set() 메소드를 이용해서 외부로 부터 입력받은 값을 담는 역할
//     외부로 부터 받은 값을 가공하는 역할
//    public void setImageName(String imagename) {
//
//    }
//
//    public void setTitle(String titleStr) {
//        title=titleStr;
//    }
//
//    public void setSubTitle(String subtitleStr) {
//        subtitle=subtitleStr;
//    }

    // publc get() 메소드를 이용해서 내부의 값을 가공해 내보내는 역할을 한다

//    public Uri getImageUri() {
//        return imageUri;
//    }

 */
