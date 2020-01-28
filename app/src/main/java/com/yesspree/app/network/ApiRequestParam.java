package com.yesspree.app.network;

import android.content.Context;

import com.facebook.stetho.common.LogUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yesspree.app.AppController;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.screens.addaddress.AdressModelToValidation;
import com.yesspree.app.screens.landing.SocialSignInputModel;
import com.yesspree.app.screens.signup.SignUpModelToValidation;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.TimeUtils;
import com.yesspree.app.utility.Validation;

import static com.yesspree.app.constatnts.Constants.ANDROID;
import static com.yesspree.app.constatnts.Constants.DEVICE_TYPE;

public class ApiRequestParam {

    static ApiRequestParam apiRequestParam = null;
    public static String TAG = "ApiRequestParam";

    public static ApiRequestParam getInstance() {
        if (apiRequestParam == null)
            return new ApiRequestParam();
        else
            return apiRequestParam;
    }

    private JsonObject respParamObj;
    private JsonArray respParamArrey;


    private String checkValue(String value) {
        return value != null ? value : "";
    }

    private void addCommonParameters(JsonObject jsonObject) {
        jsonObject.addProperty(Constants.PINCODE, AppController.getInstance().getPinCode());
    }

    private void addSession(JsonObject jsonObject) {
        jsonObject.addProperty(Constants.SESSION, AppController.getInstance().getSession());
    }

    private void addWhPincode(JsonObject jsonObject) {
        jsonObject.addProperty(Constants.WH_PINCODE, AppController.getInstance().getPinCode());
    }

    private void addAuthKey(JsonObject jsonObject) {
        jsonObject.addProperty(Constants.AUTHORIZATION, AppController.getInstance().getSession());
    }


    private void addUserId(JsonObject jsonObject) {
        jsonObject.addProperty(Constants._ID, AppController.getInstance().getCustomerId());
    }

    public JsonObject signUp(SignUpModelToValidation model) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants.FIRST_NAME, model.first_name);
            respParamObj.addProperty(Constants.LAST_NAME, model.last_name);
            respParamObj.addProperty(Constants.EMAIL, model.email);
            respParamObj.addProperty(Constants.MOBILE, model.mobile);
            respParamObj.addProperty(Constants.PASSWORD, model.password);
            respParamObj.addProperty(Constants.PERSON_PREFIX, model.person_prefix);
            respParamObj.addProperty(Constants.REFERRED_CODE, model.referred_code);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject socialsignUpParams(SocialSignInputModel model) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants.FIRST_NAME, Validation.isValidString(model.getFirst_name()) ? model.getFirst_name() : "");
            respParamObj.addProperty(Constants.LAST_NAME, Validation.isValidString(model.getLast_name()) ? model.getLast_name() : "");
            respParamObj.addProperty(Constants.EMAIL, Validation.isValidString(model.getEmail()) ? model.getEmail() : "");
            respParamObj.addProperty(Constants.MOBILE, Validation.isValidString(model.getMobile()) ? model.getMobile() : "");
            if (Validation.isValidString(model.getSocialType())) {
                if (model.getSocialType().equals(Constants.SOCIAL_TYPE_GOOGLE))
                    respParamObj.addProperty(Constants.GOOGLE_ID, Validation.isValidString(model.getSocId()) ? model.getSocId() : "");
                else
                    respParamObj.addProperty(Constants.FACEBOOK_ID, Validation.isValidString(model.getSocId()) ? model.getSocId() : "");
            }
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject verifyOtp(String otp,String EmailMblNmbr) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants.OTP, otp);
            respParamObj.addProperty(Constants.EMAIL, EmailMblNmbr);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject resendOtp(String id) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants._ID, id);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject login(String emailOrMoble, String password) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants.EMAIL, emailOrMoble);
            respParamObj.addProperty(Constants.PASSWORD, password);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject forgotPass(String emailOrMoble, boolean isemail) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(isemail ? Constants.EMAIL : Constants.MOBILE, emailOrMoble);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject resetPass(String pass, String id) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants._ID, id);
            respParamObj.addProperty(Constants.PASSWORD, pass);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject getDashboardData(Context context) {
        try {
            respParamObj = new JsonObject();
            if (CommonUtils.isLoggedIn(context) && CommonUtils.getCustomerId(context) != null) {
                respParamObj.addProperty(Constants._ID, CommonUtils.getCustomerId(context));
            }
            respParamObj.addProperty(DEVICE_TYPE, ANDROID);
            addSession(respParamObj);
            addCommonParameters(respParamObj);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }


    public JsonObject getSubCategoryData(String catID) {
        try {
            respParamObj = new JsonObject();
            addSession(respParamObj);
            addUserId(respParamObj);
            respParamObj.addProperty(Constants.CATEGOPRY_ID, catID);
            addCommonParameters(respParamObj);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }


    public JsonObject getSubSubCategoryData(String mCatId) {
        try {
            respParamObj = new JsonObject();
            addSession(respParamObj);
            addUserId(respParamObj);
            respParamObj.addProperty(Constants.CATEGOPRY_ID, mCatId);
            addCommonParameters(respParamObj);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }


    public JsonObject getMultiCategoryData(String mCatId) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants.CATEGOPRY_ID, mCatId);
            addCommonParameters(respParamObj);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }


    public JsonObject getProductDetailPairs(String catID) {
        try {
            respParamObj = new JsonObject();
            addSession(respParamObj);
            respParamObj.addProperty(Constants.PRODUCT_DETAIL_ID, catID);
            respParamObj.addProperty(Constants.WH_PINCODE, AppController.getInstance().getPinCode());
            addUserId(respParamObj);

        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }


    public JsonObject getMainProductListData(String catId, int type) {
        respParamObj = new JsonObject();
        addUserId(respParamObj);
        addSession(respParamObj);
        try {
            switch (type) {
                case 1:
                    respParamObj.addProperty(Constants.WH_PINCODE, AppController.getInstance().getPinCode());
                    respParamObj.addProperty(Constants.ID_SUBCATEGORY, catId);
                    break;
                case 2:
                    respParamObj.addProperty(Constants.ID_SUBCATEGORY, catId);
                    respParamObj.addProperty(Constants.WH_PINCODE, AppController.getInstance().getPinCode());
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respParamObj;

    }


    public JsonObject getCategory(String catId) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants.CATEGOPRY_ID, catId);
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
            return null;
        }
        return respParamObj;
    }

    // WishList
    public JsonObject modifyWishlist(String type, String productId, String customerId, String session) {
        respParamObj = new JsonObject();
        respParamObj.addProperty(Constants.SESSION, session);
        respParamObj.addProperty(Constants._ID, customerId != null ? customerId : "0");
        respParamObj.addProperty(Constants.ID_PRODUCT, productId);
        respParamObj.addProperty(Constants.OP, type);
        return respParamObj;
    }

    public JsonObject getWishlist(String type, String customerId) {
        respParamObj = new JsonObject();
        respParamObj.addProperty(Constants._ID, customerId != null ? customerId : "0");
        respParamObj.addProperty(Constants.OP, type);
        addSession(respParamObj);
        return respParamObj;
    }


    public JsonObject fetchCart(String customerId, String session) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants._ID, customerId != null ? customerId : "0");
        jsonObject.addProperty(Constants.OP, Constants.CART_GET);
        jsonObject.addProperty(Constants.SESSION, session);
        addCommonParameters(jsonObject);
        return jsonObject;
    }

    public JsonObject modifyCart(String customerId, String productId, String skuId, String quantity, String session) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants._ID, customerId != null ? customerId : "0");
        jsonObject.addProperty(Constants.OP, Constants.CART_MODIFY);
        jsonObject.addProperty(Constants.ID_PRODUCT, productId);
        jsonObject.addProperty(Constants.ID_SKU, skuId);
        jsonObject.addProperty(Constants.QUANTITY, quantity);
        jsonObject.addProperty(Constants.SESSION, session);
        addCommonParameters(jsonObject);
        return jsonObject;
    }


    public JsonObject getAddressData(String operation, AdressModelToValidation model, String AddressId) {
        try {
            respParamObj = new JsonObject();
            switch (operation) {
                case Constants.CREATE:
                    try {
                        getCommonAddressData(respParamObj, model);
                    } catch (Exception exp) {
                        LogUtil.e(TAG, exp.getMessage());
                    }
                    break;

                case Constants.GET:
                    respParamObj.addProperty(Constants.OP, operation);
                    addCommonParameters(respParamObj);
                    break;

                case Constants.DELETE:
                    respParamObj.addProperty(Constants.OP, operation);
                    respParamObj.addProperty(Constants.ID_ADDRESS, AddressId);
                    addCommonParameters(respParamObj);
                    break;
                case Constants.UPDATE:
                    getCommonAddressData(respParamObj, model);
                    respParamObj.addProperty(Constants.ID_ADDRESS, AddressId);
                    addCommonParameters(respParamObj);
                    break;

                case Constants.SELECT_ADDRESS:
                    respParamObj.addProperty(Constants.OP, "update");
                    respParamObj.addProperty(Constants.ID_ADDRESS, AddressId);
                    respParamObj.addProperty(Constants.SELECTED, "1");
                    addCommonParameters(respParamObj);
                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respParamObj;

    }

    private void getCommonAddressData(JsonObject respParamObj, AdressModelToValidation model) {
        respParamObj.addProperty(Constants.OP, model.op);
        respParamObj.addProperty(Constants.ID_CUSTOMER_, model.customer_id);
        respParamObj.addProperty(Constants.NAME, model.name);
        respParamObj.addProperty(Constants.PHONE, model.phone);
        respParamObj.addProperty(Constants.ADDRESS1, model.flatHouseNum);
        respParamObj.addProperty(Constants.ADDRESS2, model.street);
        respParamObj.addProperty(Constants.CITY, model.city);
        respParamObj.addProperty(Constants.STATE, model.state);
        respParamObj.addProperty(Constants.COUNTRY, model.country);
        respParamObj.addProperty(Constants.PERSON_PREFIX, model.person_prefix);
        respParamObj.addProperty(Constants.TALUK, model.taluq);
        respParamObj.addProperty(Constants.DISTRICT, model.dist);
        respParamObj.addProperty(Constants.PINCODE, model.pincode);
        respParamObj.addProperty(Constants.LAT, model.lat);
        respParamObj.addProperty(Constants.LON, model.log);
        respParamObj.addProperty(Constants.LANDMARK, model.landmark);
        respParamObj.addProperty(Constants.SELECTED, model.selected);
        respParamObj.addProperty(Constants.TYPE, model.addresType);

    }

    public JsonObject getMyAccountDetails(String authKey) {
        respParamObj = new JsonObject();
        respParamObj.addProperty(Constants.AUTHORIZATION, authKey);
        return respParamObj;
    }


    public JsonObject getOrderSummery(String session, String pincode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.WH_PINCODE, pincode);
        jsonObject.addProperty(Constants.SESSION, session);
        return jsonObject;
    }

    public JsonObject checkoutApi(String customerId, JsonArray jsonArray) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants._ID, customerId != null ? customerId : "0");
        jsonObject.add(Constants.ORDERS, jsonArray);
        addCommonParameters(jsonObject);
        return jsonObject;
    }


    public JsonObject getMyOrdersList(String type) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.TYPE, type);
        return jsonObject;
    }

    public JsonObject getMyOrdersStatus(String orderNo) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.ORDER_NUMBER, orderNo);
        addCommonParameters(jsonObject);
        addUserId(jsonObject);
        return jsonObject;
    }


    public JsonObject getFirebaseToken(Context mContext) {
        try {
            respParamObj = new JsonObject();
            respParamObj.addProperty(Constants._ID, CommonUtils.getCustomerId(mContext));
            respParamObj.addProperty(Constants.FCM_TOKEN, CommonUtils.getFireBaseTokenID(mContext));
            respParamObj.addProperty(Constants.DEVICE_ID, CommonUtils.getDeviceID(mContext));
            respParamObj.addProperty(Constants.DEVICE, "android");
            respParamObj.addProperty(Constants.APP, "customer");
        } catch (Exception exp) {
            LogUtil.e(TAG, exp.getMessage());
        }
        return respParamObj;
    }

    public JsonObject cancelOrder(String orderNumber) {
        respParamObj = new JsonObject();
        addUserId(respParamObj);
        respParamObj.addProperty(Constants.ORDER_NUMBER, orderNumber);
        respParamObj.addProperty(Constants.CANCELLED_ON, checkValue(TimeUtils.getCurrentDateTimeInString()));
        respParamObj.addProperty(Constants.ORDER_STATUS, Constants.ORDER_STATUS_CANCELLED);
        respParamObj.addProperty(Constants.WH_PINCODE, AppController.getInstance().getPinCode());
        return respParamObj;
    }

    public JsonObject getNotificationData(String CustId) {
        respParamObj = new JsonObject();
        respParamObj.addProperty(Constants.ID_CUSTOMER_, CustId);
        return respParamObj;
    }

    public JsonObject getFilterType(String catId) {
        respParamObj = new JsonObject();
        addUserId(respParamObj);
        addSession(respParamObj);
        addWhPincode(respParamObj);
        respParamObj.addProperty(Constants.CATEGOPRY_ID, checkValue(catId));
        return respParamObj;
    }

    public JsonObject getUpdateMyAccount(SignUpModelToValidation signUpModelToValidation) {
        respParamObj = new JsonObject();
        addUserId(respParamObj);
        respParamObj.addProperty(Constants.FIRST_NAME, signUpModelToValidation.first_name);
        respParamObj.addProperty(Constants.LAST_NAME, signUpModelToValidation.last_name);
        respParamObj.addProperty(Constants.EMAIL, signUpModelToValidation.email);
        respParamObj.addProperty(Constants.FB_BIRTH_DAY, signUpModelToValidation.dateOfBrth);
        respParamObj.addProperty(Constants.MOBILE, signUpModelToValidation.mobile);
        respParamObj.addProperty(Constants.PIC_DATA, signUpModelToValidation.profilPic);
        return respParamObj;
    }


    public JsonObject getFilterRes(String catId, String filterType, String filterValue) {
        respParamObj = new JsonObject();
        addUserId(respParamObj);
        addSession(respParamObj);
        addWhPincode(respParamObj);
        respParamObj.addProperty(Constants.CATEGOPRY_ID, checkValue(catId));
        respParamObj.addProperty(filterType.toLowerCase(), filterValue);
        return respParamObj;
    }

    // WishList
    public JsonObject getExploreViewData(String exploreId) {
        respParamObj = new JsonObject();
        respParamObj.addProperty(Constants._ID, exploreId);
        return respParamObj;
    }

    public JsonObject getSubsscription(String type) {
        respParamObj = new JsonObject();
        respParamObj.addProperty(Constants.TYPE, type);
        return respParamObj;
    }


    public JsonObject cancelSubscr(String orderNumber) {
        respParamObj = new JsonObject();
        addUserId(respParamObj);
        respParamObj.addProperty(Constants.ORDER_NUMBER, orderNumber);
        respParamObj.addProperty(Constants.ORDER_STATUS, Constants.ORDER_STATUS_CANCELLED);
        respParamObj.addProperty(Constants.CANCELLED_ON, checkValue(TimeUtils.getCurrentDateTimeInString()));
        return respParamObj;
    }

    public JsonObject applyCoupon(String couponCode, String orderId) {
        respParamObj = new JsonObject();
        addSession(respParamObj);
        respParamObj.addProperty(Constants.COUPON_CODE, couponCode);
        respParamObj.addProperty(Constants.ID_ORDER, orderId);
        return respParamObj;
    }

    public JsonObject getOrderSummery(String couponCode) {
        JsonObject jsonObject = new JsonObject();
        addSession(jsonObject);
        addWhPincode(jsonObject);
        addSession(jsonObject);
        if (Validation.isValidString(couponCode))
            jsonObject.addProperty(Constants.COUPON_CODE, couponCode);
        return jsonObject;
    }


}
