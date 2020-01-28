package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ProductData implements Parcelable {


    @SerializedName("id_product")
    private String productId;
    @Expose
    @SerializedName("selling_price")
    private int selling_price;
    @Expose
    @SerializedName("mrp")
    private int mrp;
    @Expose
    @SerializedName("actual_price")
    private float actual_price;
    @Expose
    @SerializedName("sku")
    private ArrayList<Sku> sku;
    @Expose
    @SerializedName("spec")
    private ArrayList<Spec> spec;
    @Expose
    @SerializedName("pic")
    private ArrayList<Pic> pic;
    @Expose
    @SerializedName("wishlist")
    private int wishlist;
    @Expose
    @SerializedName("id_category")
    private String id_category;
    @Expose
    @SerializedName("brand")
    private String brand;
    @Expose
    @SerializedName("tags")
    private String tags;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("article_no")
    private String article_no;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("image")
    String image;

    @SerializedName("is_subscribe")
    String is_subscribe = "0";

    @SerializedName("subscription_slot")
    String subscription_slot ;


    public Sku selSku;
    private int selectedSkuPosition;

    private int tempWishlistStatus = -1;


    public String getProductId() {
        return productId != null && !productId.equalsIgnoreCase("0") ? productId : _id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Sku getSelSku() {
        return selSku;
    }

    public void setSelSku(Sku selSku) {
        this.selSku = selSku;
    }

    public int getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(int selling_price) {
        this.selling_price = selling_price;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public float getActual_price() {
        return actual_price;
    }

    public void setActual_price(float actual_price) {
        this.actual_price = actual_price;
    }

    public ArrayList<Sku> getSkuData() {
        return sku;
    }

    public void setSkuData(ArrayList<Sku> sku) {
        this.sku = sku;
    }

    public ArrayList<Spec> getSpec() {
        return spec;
    }

    public void setSpec(ArrayList<Spec> spec) {
        this.spec = spec;
    }

    public ArrayList<Pic> getPic() {
        return pic;
    }

    public void setPic(ArrayList<Pic> pic) {
        this.pic = pic;
    }

    public int getWishlist() {
        return wishlist;
    }

    public void setWishlist(int wishlist) {
        this.wishlist = wishlist;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticle_no() {
        return article_no;
    }

    public void setArticle_no(String article_no) {
        this.article_no = article_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getSelectedSkuPosition() {
        return selectedSkuPosition;
    }

    public void setSelectedSkuPosition(int selectedSkuPosition) {
        this.selectedSkuPosition = selectedSkuPosition;
    }

    public int getTempWishlistStatus() {
        return tempWishlistStatus;
    }

    public void setTempWishlistStatus(int tempWishlistStatus) {
        this.tempWishlistStatus = tempWishlistStatus;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getSubscription_slot() {
        return subscription_slot;
    }

    public void setSubscription_slot(String subscription_slot) {
        this.subscription_slot = subscription_slot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeInt(this.selling_price);
        dest.writeInt(this.mrp);
        dest.writeFloat(this.actual_price);
        dest.writeList(this.sku);
        dest.writeList(this.spec);
        dest.writeList(this.pic);
        dest.writeInt(this.wishlist);
        dest.writeString(this.id_category);
        dest.writeString(this.brand);
        dest.writeString(this.tags);
        dest.writeString(this.description);
        dest.writeString(this.article_no);
        dest.writeString(this.name);
        dest.writeString(this._id);
        dest.writeString(this.image);
        dest.writeString(this.is_subscribe);
        dest.writeParcelable(this.selSku, flags);
        dest.writeInt(this.selectedSkuPosition);
        dest.writeInt(this.tempWishlistStatus);
        dest.writeString(this.subscription_slot);
    }

    public ProductData() {
    }

    protected ProductData(Parcel in) {
        this.productId = in.readString();
        this.selling_price = in.readInt();
        this.mrp = in.readInt();
        this.actual_price = in.readFloat();
        this.sku = new ArrayList<Sku>();
        in.readList(this.sku, Sku.class.getClassLoader());
        this.spec = new ArrayList<Spec>();
        in.readList(this.spec, Spec.class.getClassLoader());
        this.pic = new ArrayList<Pic>();
        in.readList(this.pic, Pic.class.getClassLoader());
        this.wishlist = in.readInt();
        this.id_category = in.readString();
        this.brand = in.readString();
        this.tags = in.readString();
        this.description = in.readString();
        this.article_no = in.readString();
        this.name = in.readString();
        this._id = in.readString();
        this.image = in.readString();
        this.is_subscribe = in.readString();
        this.selSku = in.readParcelable(Sku.class.getClassLoader());
        this.selectedSkuPosition = in.readInt();
        this.tempWishlistStatus = in.readInt();
        this.subscription_slot = in.readString();
    }

    public static final Parcelable.Creator<ProductData> CREATOR = new Parcelable.Creator<ProductData>() {
        @Override
        public ProductData createFromParcel(Parcel source) {
            return new ProductData(source);
        }

        @Override
        public ProductData[] newArray(int size) {
            return new ProductData[size];
        }
    };
}
