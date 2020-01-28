package com.yesspree.app.modelapi;

import android.graphics.drawable.Drawable;

public class NavigationDrawerModel {


    public String title;
    public int id;
    public Drawable icon;

    public NavigationDrawerModel() {
    }

    public NavigationDrawerModel(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "NavigationDrawerModel{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }
}