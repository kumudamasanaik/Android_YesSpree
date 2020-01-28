package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FuGenX-14 on 05-06-2018.
 */

public class AddressData implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("id_customer")
    @Expose
    private String idCustomer;

    @SerializedName("person_prefix")
    @Expose
    private String personPrefix;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address1")
    @Expose
    private String address1;

    @SerializedName("address2")
    @Expose
    private String address2;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("taluk")
    @Expose
    private String taluk;

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("pincode")
    @Expose
    private String pincode;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lon")
    @Expose
    private String lon;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("landmark")
    @Expose
    private String landmark;

    @SerializedName("type")
    @Expose
    private String address_type;

    @SerializedName("selected")
    @Expose
    private int selected;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getPersonPrefix() {
        return personPrefix;
    }

    public void setPersonPrefix(String personPrefix) {
        this.personPrefix = personPrefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.idCustomer);
        dest.writeString(this.personPrefix);
        dest.writeString(this.name);
        dest.writeString(this.address1);
        dest.writeString(this.address2);
        dest.writeString(this.city);
        dest.writeString(this.taluk);
        dest.writeString(this.district);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeString(this.pincode);
        dest.writeString(this.lat);
        dest.writeString(this.lon);
        dest.writeString(this.phone);
        dest.writeString(this.alias);
        dest.writeString(this.company);
        dest.writeString(this.landmark);
        dest.writeString(this.address_type);
        dest.writeInt((this.selected));
    }

    public AddressData() {
    }

    protected AddressData(Parcel in) {
        this.id = in.readString();
        this.idCustomer = in.readString();
        this.personPrefix = in.readString();
        this.name = in.readString();
        this.address1 = in.readString();
        this.address2 = in.readString();
        this.city = in.readString();
        this.taluk = in.readString();
        this.district = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.pincode = in.readString();
        this.lat = in.readString();
        this.lon = in.readString();
        this.phone = in.readString();
        this.alias = in.readString();
        this.company = in.readString();
        this.landmark = in.readString();
        this.address_type = in.readString();
        this.selected = in.readInt();
    }

    public static final Parcelable.Creator<AddressData> CREATOR = new Parcelable.Creator<AddressData>() {
        @Override
        public AddressData createFromParcel(Parcel source) {
            return new AddressData(source);
        }

        @Override
        public AddressData[] newArray(int size) {
            return new AddressData[size];
        }
    };

    @Override
    public String toString() {
        return "AddressData{" +
                "address1='" + address1 + '\'' +
                '}';
    }
}
