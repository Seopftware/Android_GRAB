package com.example.msi.grab;

/**
 * Created by MSI on 2017-04-19.
 */

public class MonthItem {

    private int dayValue;
    private int monthValue;

    public MonthItem() {

    }

    public MonthItem(int day) {
        dayValue = day;
    }

    public int getDay() {
        return dayValue;
    }

    public int getMonth() {
        return monthValue;
    }

    public void setDay(int day) {
        this.dayValue=day;
    }
}
