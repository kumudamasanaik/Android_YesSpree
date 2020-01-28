package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;

public class FilterValue {

    @SerializedName("value")
    private String value;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    boolean isChecked;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
