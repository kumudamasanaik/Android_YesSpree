package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderedProduct {

    @Expose
    @SerializedName("product_size")
    private String product_size;
    @Expose
    @SerializedName("brand_en")
    private String brand_en;
    @Expose
    @SerializedName("updated")
    private String updated;
    @Expose
    @SerializedName("added")
    private String added;
    @Expose
    @SerializedName("sold")
    private String sold;
    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("return_quantity")
    private String return_quantity;
    @Expose
    @SerializedName("dispatch_quantity")
    private String dispatch_quantity;
    @Expose
    @SerializedName("express")
    private String express;
    @Expose
    @SerializedName("cessperpc")
    private String cessperpc;
    @Expose
    @SerializedName("cessperc")
    private String cessperc;
    @Expose
    @SerializedName("stock")
    private String stock;
    @Expose
    @SerializedName("ooos")
    private String ooos;
    @Expose
    @SerializedName("max_quantity")
    private String max_quantity;
    @Expose
    @SerializedName("mbq")
    private String mbq;
    @Expose
    @SerializedName("min_quantity")
    private String min_quantity;
    @Expose
    @SerializedName("taxtype")
    private String taxtype;
    @Expose
    @SerializedName("igst")
    private String igst;
    @Expose
    @SerializedName("sgst")
    private String sgst;
    @Expose
    @SerializedName("cgst")
    private String cgst;
    @Expose
    @SerializedName("gst")
    private String gst;
    @Expose
    @SerializedName("tax")
    private String tax;
    @Expose
    @SerializedName("selling_price")
    private String selling_price;
    @Expose
    @SerializedName("realization")
    private String realization;
    @Expose
    @SerializedName("mrp")
    private String mrp;
    @Expose
    @SerializedName("fill_rate")
    private String fill_rate;
    @Expose
    @SerializedName("icm_name")
    private String icm_name;
    @Expose
    @SerializedName("rack_location")
    private String rack_location;
    @Expose
    @SerializedName("variable_weight")
    private String variable_weight;
    @Expose
    @SerializedName("weight_in_gms")
    private String weight_in_gms;
    @Expose
    @SerializedName("order_part_type")
    private String order_part_type;
    @Expose
    @SerializedName("pack_size")
    private String pack_size;
    @Expose
    @SerializedName("supplier_price")
    private String supplier_price;
    @Expose
    @SerializedName("check_expired")
    private String check_expired;
    @Expose
    @SerializedName("case_quantity")
    private String case_quantity;
    @Expose
    @SerializedName("threshold_margin_value")
    private String threshold_margin_value;
    @Expose
    @SerializedName("hsn_codes")
    private String hsn_codes;
    @Expose
    @SerializedName("ean_codes")
    private String ean_codes;
    @Expose
    @SerializedName("sub_type")
    private String sub_type;
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("picked_quantity")
    private String picked_quantity;
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("sku")
    private String sku;
    @Expose
    @SerializedName("id_sku")
    private String id_sku;
    @Expose
    @SerializedName("product_pic")
    private String product_pic;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("id_product")
    private String id_product;
    @Expose
    @SerializedName("id_order")
    private String id_order;
    @Expose
    @SerializedName("_id")
    private String _id;

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public String getBrand_en() {
        return brand_en;
    }

    public void setBrand_en(String brand_en) {
        this.brand_en = brand_en;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getReturn_quantity() {
        return return_quantity;
    }

    public void setReturn_quantity(String return_quantity) {
        this.return_quantity = return_quantity;
    }

    public String getDispatch_quantity() {
        return dispatch_quantity;
    }

    public void setDispatch_quantity(String dispatch_quantity) {
        this.dispatch_quantity = dispatch_quantity;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getCessperpc() {
        return cessperpc;
    }

    public void setCessperpc(String cessperpc) {
        this.cessperpc = cessperpc;
    }

    public String getCessperc() {
        return cessperc;
    }

    public void setCessperc(String cessperc) {
        this.cessperc = cessperc;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOoos() {
        return ooos;
    }

    public void setOoos(String ooos) {
        this.ooos = ooos;
    }

    public String getMax_quantity() {
        return max_quantity;
    }

    public void setMax_quantity(String max_quantity) {
        this.max_quantity = max_quantity;
    }

    public String getMbq() {
        return mbq;
    }

    public void setMbq(String mbq) {
        this.mbq = mbq;
    }

    public String getMin_quantity() {
        return min_quantity;
    }

    public void setMin_quantity(String min_quantity) {
        this.min_quantity = min_quantity;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getRealization() {
        return realization;
    }

    public void setRealization(String realization) {
        this.realization = realization;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getFill_rate() {
        return fill_rate;
    }

    public void setFill_rate(String fill_rate) {
        this.fill_rate = fill_rate;
    }

    public String getIcm_name() {
        return icm_name;
    }

    public void setIcm_name(String icm_name) {
        this.icm_name = icm_name;
    }

    public String getRack_location() {
        return rack_location;
    }

    public void setRack_location(String rack_location) {
        this.rack_location = rack_location;
    }

    public String getVariable_weight() {
        return variable_weight;
    }

    public void setVariable_weight(String variable_weight) {
        this.variable_weight = variable_weight;
    }

    public String getWeight_in_gms() {
        return weight_in_gms;
    }

    public void setWeight_in_gms(String weight_in_gms) {
        this.weight_in_gms = weight_in_gms;
    }

    public String getOrder_part_type() {
        return order_part_type;
    }

    public void setOrder_part_type(String order_part_type) {
        this.order_part_type = order_part_type;
    }

    public String getPack_size() {
        return pack_size;
    }

    public void setPack_size(String pack_size) {
        this.pack_size = pack_size;
    }

    public String getSupplier_price() {
        return supplier_price;
    }

    public void setSupplier_price(String supplier_price) {
        this.supplier_price = supplier_price;
    }

    public String getCheck_expired() {
        return check_expired;
    }

    public void setCheck_expired(String check_expired) {
        this.check_expired = check_expired;
    }

    public String getCase_quantity() {
        return case_quantity;
    }

    public void setCase_quantity(String case_quantity) {
        this.case_quantity = case_quantity;
    }

    public String getThreshold_margin_value() {
        return threshold_margin_value;
    }

    public void setThreshold_margin_value(String threshold_margin_value) {
        this.threshold_margin_value = threshold_margin_value;
    }

    public String getHsn_codes() {
        return hsn_codes;
    }

    public void setHsn_codes(String hsn_codes) {
        this.hsn_codes = hsn_codes;
    }

    public String getEan_codes() {
        return ean_codes;
    }

    public void setEan_codes(String ean_codes) {
        this.ean_codes = ean_codes;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPicked_quantity() {
        return picked_quantity;
    }

    public void setPicked_quantity(String picked_quantity) {
        this.picked_quantity = picked_quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getId_sku() {
        return id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
