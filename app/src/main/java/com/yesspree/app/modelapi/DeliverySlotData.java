package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeliverySlotData {

    @Expose
    @SerializedName("date")
    private String date;

    @SerializedName("times")
    private ArrayList<Times> times;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Times> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Times> times) {
        this.times = times;
    }

    public class Times {

        @Expose
        @SerializedName("slot_id")
        private String slot_id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("time")
        private String time;

        public String getSlot_id() {
            return slot_id;
        }

        public void setSlot_id(String slot_id) {
            this.slot_id = slot_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
