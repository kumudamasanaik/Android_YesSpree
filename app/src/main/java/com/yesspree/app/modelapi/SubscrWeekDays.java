package com.yesspree.app.modelapi;

public class SubscrWeekDays {

    String day;
    boolean isSelected;

    public SubscrWeekDays(String day, boolean isSelected) {
        this.day = day;
        this.isSelected = isSelected;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
