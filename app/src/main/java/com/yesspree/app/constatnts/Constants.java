package com.yesspree.app.constatnts;

import com.yesspree.app.modelapi.FetchCartSummaryResponse;

public class Constants {

    // API Response
    public static final String STATUS = "status";
    public static final String STATUS_MSG = "STATUS_MSG";
    public static final String TYPE = "type";
    public static final String RESULT = "result";
    public static final String PAGE_START = "start";
    public static final String PAGE_COUNT = "count";
    public static final String TARGET = "target";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String EMPTY = "empty";
    public static final String REJECTED = "rejected";
    public static final String NOT_VERIFIED = "not_verified";

    public static final String TAG = "Yesspress";
    public static final String INPUT_TAG = "Input";
    public static final String RESPONSE_TAG = "Response";
    public static final String MESSAGE = "message";
    public static final String OFFERS = "offer";
    public static final String OFFERS_DETAIL = "Offers detail";
    public static final String HOME_FRAGMENT = "home fragment";


    //Broadcast
    public static final String OTP_BROADCAST_INTENT = "OTP_BROADCAST_INTENT";
    //Start Activity Intents
    public static final String MODEL = "model";
    public static final String SOURCE = "SOURCE";
    public static final String SOURCE_DASH_BOARD = "SOURCE_DASH_BOARD";
    public static final String SOURCE_SIGHUP = "SOURCE_SIGHUP";
    public static final String SOURCE_OTP = "SOURCE_OTP";
    public static final String SOURCE_SPLASH = "SOURCE_SPLASH";
    public static final String SOURCE_LANDING = "SOURCE_LANDING";
    public static final String SOURCE_SIGN = "SOURCE_SIGN";
    public static final String SOURCE_RESET_PASS = "SOURCE_RESET_PASS";
    public static final String SOURCE_MY_ORDER = "SOURCE_MY_ORDER";
    public static final String SOURCE_CART = "SOURCE_CART";
    public static final String SOURCE_CURRENT_LOC = "SOURCE_CURRENT_LOC";
    public static final String SOURCE_ADDRESS_SELECTION = "SOURCE_ADDRESS_SELECTION";
    public static final String SOURCE_ADD_ADDRESS = "SOURCE_ADD_ADDRESS";
    public static String SOURCE_CONFIRM_ORDER = "SOURCE_CONFIRM_ORDER";
    public static String SOURCE_EDIT_ADDRESS_MY_ACCOUNT = "MY ACCOUNT";
    public static final String SOURCE_FILTER = "SOURCE_FILTER";
    public static int CONFIRM_ORDER_CODE = 100;
    public static int MY_ACCOUNT_CODE = 1;
    public static int ACCOUNT_EDIT_PSSWRDCODE = 3;
    public static String SOURCE_EDIT_PSSWRD_MY_ACCOUNT = "MY ACCOUNT PSSWRD";

    public static final int RESPONSE_SUCCESS = 1; //200
    public static final int RESPONSE_FAILURE = 0; //201
    public static final int RESPONSE_TIMEOUT_ERR = 408;
    public static final int RESPONSE_SERVER_ERR = 503;
    public static final int RESPONSE_DUMMY = 4545454;


    //signUp
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String REFERRED_CODE = "referred_code";
    public static final String PERSON_PREFIX = "person_prefix";
    public static final String OTP = "otp";
    // Login
    public static final String AUTHORIZATION_KEY = "AUTHORIZATION_KEY";
    public static final String AUTHORIZATION = "Authorization";
    public static final String KEY = "key";
    public static final String OP = "op";


    //facebook
    public static final String FB_ID = "id";
    public static final String FB_AGE_RANGE = "age_range";
    public static final String FB_CONTEXT = "context";
    public static final String FB_COVER = "cover";
    public static final String FB_CURRENCY = "currency";
    public static final String FB_DEVICES = "devices";
    public static final String FB_FIRST_NAME = "first_name";
    public static final String FB_GENDER = "gender";
    public static final String FB_LAST_NAME = "last_name";
    public static final String FB_EMAIL = "email";
    public static final String FB_BIRTH_DAY = "birthday";
    public static final String FB_LINK = "link";
    public static final String FB_LOCALE = "locale";
    public static final String FB_NAME = "name";
    public static final String FB_PICTURE = "picture";
    public static final String FB_TIME_ZONE = "timezone";
    public static final String FB_UPDATED_TIME = "updated_time";
    public static final String FB_VERIFIED = "verified";


    public static final String ID_CART = "id_cart";
    public static final String ID = "id";
    public static final String _ID = "_id";
    public static final String CUSTOMER = "customer";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String ID_CUSTOMER_ = "id_customer";
    public static final String FACEBOOK_ID = "facebook_id";
    public static final String SOCIALTYPE = "SOCIALTYPE";
    public static final String GOOGLE_ID = "google_id";
    public static final String VERSION_CODE = "version";
    public static final String PLATFORM = "platform";
    public static final String NAME = "name";
    public static final String EMAIL_VERIFIED = "EMAIL_VERIFIED";
    public static final String TUTORIAL_STATUS = "TUTORIAL_STATUS";
    public static final String SESSION = "_session";
    public static final String SESSION_TIME = "_session_time";

    /*category Api*/
    public static final String CATEGOPRY_ID = "id_category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String ID_SUBCATEGORY = "id_subcategory";
    public static final String TOP_PRODUCTS = "top_products";
    public static final String ALL_PRODCUTS = "all_products";
    public static final String BANNER_1 = "banner1";
    public static final String BANNER_2 = "banner2";
    public static final String BANNER_3 = "banner3";
    public static final String BANNER_ = "banner";


    //Dashboard
    public static final String PHONE = "phone";
    public static final String PIC = "pic";
    public static final String GENDER = "gender";
    public static final String ADDRESS = "address";
    public static final String TRACK = "track";
    public static final String ADDRESS1 = "address1";
    public static final String ADDRESS2 = "address2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String PINCODE = "pincode";
    public static final String WH_PINCODE = "wh_pincode";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String LANDMARK = "landmark";
    public static final String SELECTED = "selected";
    public static final String CUSTOMER_TYPE = "customer_type";
    public static final String SELECTED_ADDRESS = "selected_address";
    public static final String PIC_DATA = "picdata";
    public static final String DEVICE_TYPE = "device_type";
    public static final String ANDROID = "android";
    public static final String REFER = "reffer";


    //Referral
    public static final String REFERRED = "referred";
    public static final String REFERRAL_CODE = "REFERRAL_CODE";
    public static final String REFERRAL_CODE_SMALL = "referral_code";
    public static final String REFERRAL_USED = "REFERRAL_USED";
    public static final String REFERRED_BY = "REFERRED_BY";
    public static final String KEY_USER_LEARNED_DRAWER = "KEY_USER_LEARNED_DRAWER";
    public static final String SEL_POS = "SEL_POS";
    public static final String EXTRA_REFFERAL_DATA = "REFFERAL DATA";
    public static final String EXTRA_OFFERS_DATA = "OFFERS DATA";
    public static final String EXTRA_CUSTOMER_CARE_DATA = "CUSTOMER DATA";

    public static String SOCIAL_TYPE_FACEBOOK = "SOCIAL_TYPE_FACEBOOK";
    public static String SOCIAL_TYPE_GOOGLE = "SOCIAL_TYPE_GOOGLE";
    public static final int RC_SIGN_IN = 9001;
    public static String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String IS_TEMP_LOGGED_IN = "IS_TEMP_LOGGED_IN";

    /*filter tabs names*/
    public static final String REFINE_BY = "Refine By";
    public static final String SORTED_BY = "Sort By";

    /*My subsciption tabs names*/
    public static final String ACTIVE = "Active";
    public static final String CANCELLED = "Cancelled";
    public static final String MY_SUBSCRIPTION = "my subscription";
    /*Aboutus  tabs names*/
    public static final String ABOUTUS = " About Us ";
    public static final String PRIVACY_POLICY = " Privacy Policy ";
    public static final String TERMS_AND_CONDITION = " Terms & Conditions ";


    /*B*/
    public static final String CATEGORY = "category";
    public static final String BANNNER = "banner";
    public static final String SAVINGS = "saving";
    public static final String BRANDS = "brands";
    public static final String POULAR = "popular";
    public static final String SPECIFIC_PRODUCT = "specific_product";
    public static final String SPECIFIC_PRODUCT1 = "specific_product1";
    public static final String SPECIFIC_PRODUCT2 = "specific_product2";
    public static final String SIMILAR_PRODUCT = "similar_product";
    public static final String WISHLIST = "WISHLIST";
    public static final String CART_PRODUCTS = "CART_PRODUCTS";
    public static final String FACEBOOK_SHARE = "com.facebook";
    public static final String APP_SHARE_MESSAGE = "APP_SHARE_MESSAGE";
    public static final String WHATSUP_SHARE = "com.whatsapp";
    public static final String MSG_SHARE = "com.google.android.apps.messaging";
    public static final String CAT_DISCOUNT = "cat_discount";
    public static final String TITLE = "title";
    public static final String PRODUCTS = "product";
    public static final String GRAND_TOTALL = "grand_total";
    public static final String DELIVERY_CHARGE = "delivery_charge";
    public static final String SELLING_PRICE = "selling_price";
    public static final String MRP = "mrp";
    public static final String REALIZATION = "realization";
    public static final String CART_COUNT = "cart_count";
    public static final String SUMMARY = "summary";
    public static final String PARENT_CAT = "parent_cat";
    public static final String CONTACT_US = "contactus";

    public static final String EXTRAS_CATEGORY_LIST = "categoryArrayList";
    public static final String EXTRAS_CATEGORY_ID = "Category_id";
    public static final String EXTRAS_CATEGORY_NAME = "Category_name";
    public static final String EXTRAS_SUB_CATEGORY_ID = "Category_id";
    public static final String EXTRAS_SUB_CATEGORY_NAME = "Category_name";
    public static final String EXTRAS_MULTI_FRAG_CATEGORY_ID = "Category_id";
    public static final String EXTRAS_MULTI_FRAG_CATEGORY_NAME = "Category_name";
    public static final String EXTRAS_MULTIPLE_SUB_CATEGORY_ID = "Category_id";
    public static final String PRODUCT_DETAIL_ID = "products";
    public static final String TOTAL_SKU = "total_sku";
    public static final String SIMILER_DATA = "similar_data";
    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String USER_MAY_ALSO_LIKE_ADAPTER = "USER_MAY_ALSO_LIKE_ADAPTER";

    /*product list*/
    public static final String PRODUCT_DETAILS = "PRODUCT_DETAILS";
    public static final String PRODUCT_CART_MODIFY1 = "PRODUCT_CART_MODIFY1";
    public static final String PRODUCT_CART_MODIFY2 = "PRODUCT_CART_MODIFY2";
    public static final String CART_REMOVE = "CART_REMOVE";
    public static final String ADD_TO_WISHLIST = "ADD_TO_WISHLIST";
    public static final String SKU_CHAGE = "SKU_CHAGE";
    public static final String CART = "cart";
    public static final String ORDER = "order";
    public static final String CART_DATA_PREF = "CART_DATA_PREF";
    public static final String CART_MODIFY = "modify";
    public static String CART_GET = "get";
    public static final String ID_SKU = "id_sku";
    public static final String QUANTITY = "quantity";

    public static final String WISHLIST_CREATE = "create";
    public static final String WISHLIST_DELETE = "delete";
    public static final String WISHLIST_DATA = "get";
    public static final String ID_PRODUCT = "id_product";

    public static final String PRODUCT_WISH_LIST_CHANGED = "PRODUCT_WISH_LIST";

    /*current location*/
    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME = "com.yesspree.app.screens.location";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String RESULT_CURRENT_LOC = PACKAGE_NAME + ".RESULT_CURRENT_LOC";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String WISH_LIST_FRAGMENT = "Wish List";
    //Address Type
    public static final String ADDRESS_TYPE_HOME = "Home";
    public static final String ADDRESS_TYPE_OFFICE = "Office";
    public static final int REQUEST_CODE_AUTOCOMPLETE = 19995;
    public static final int REQUEST_LOCATION_CODE = 1;
    public static final String TALUK = "taluk";
    public static final String DISTRICT = "district";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String GET = "get";
    public static final String DELETE = "delete";
    public static final String EDIT = "edit";
    public static final String SELECT_ADDRESS = "select Address";
    public static final String LIST_ITEM = "List item";
    public static final String EXTRAS_ADDRESS_DATA = "address data";
    public static final String DELETE_CONFIRMATION = "delete confirmation";
    public static final String ID_ADDRESS = "id_address";


    /*summery*/
    public static final String PAY_OPTIONS = "pay_options";
    public static final String ORDERS = "orders";
    public static final String DELIVERYSLOT = "deliveryslot";

    /*address */
    public static final String ADDRESS_SELECTED = "addressSelected";
    public static final String ADDRESS_LOADED = "addressLoaded";
    public static final String ADDRESS_DELETED = "addressDeleted";
    public static final String SAVE_ADDRESS = "SAVE_ADDRESS";

    /*checkout*/
    public static FetchCartSummaryResponse fetchCartSummaryResponse = null;
    public static final String TOTAL_PAID = "total_paid";
    public static final String DELIVERY_SLOT = "delivery_slot";
    public static final String DELIVERY_SLOT_2 = "delivery_slot_2";
    public static final String ORDER_ID = "order_id";
    public static final String ID_ORDER = "id_order";
    public static final String ORDER_NUMBER = "order_no";
    public static final String CANCELLED_ON = "cancelled_on";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_STATUS_CANCELLED = "Cancelled";
    public static final String COUPON_CODE = "coupon_code";


    // Pay Types
    public static final String PAY_OPTION = "pay_option";
    public static final String PAY_TYPE = "pay_type";
    public static final String PAY_TYPE_COD = "cod";
    public static final String PAY_TYPE_ONLINE = "online";
    public static final String PAY_TYPE_COD_TEXT = "Cash On Delivery";
    public static final String PAY_TYPE_ONLINE_TEXT = "Paid Online";
    public static final String PAY_TYPE_Paytm = "Paytm";
    public static String WALLET_AMOUNT = "wallet_amount";
    public static String APPLIED_GIFT_ID = "0";
    public static final String ID_GIFT = "id_giftcard";
    public static final String EXPRESS = "express";
    /*My orders */
    public static final String DELIVERED = "Delivered";
    public static final String PRESENT = "Present";

    public static final String SIMILIER_PRODUCTS = "similiar products";
    public static final String ALL_PRODUCTS = "ALL products";
    public static final String ORDER_DETAILS = "ORDER_DETAILS";

    public static int PRODUCT_LIST_TYPE = 1;
    public static int PRODUCT_LIST_TAB_SELECTION_TYPE = 2;

    /*about us data*/
    public static final String ABOUT_US = "aboutus";
    public static final String TERMS_CONDITION = "terms_condition";
    public static final String PRIVACY_POLICY_JSON = "privacy_policy";

    /*Firebase */

    public static final String FIREBASE_TOKEN = "Firebase toke id";
    public static final String FIREBASE_BODY = "message";
    public static final String FIREBASE_TITLE = "title";
    public static final String FIREBASE_MSG = "m";
    public static final String FIREBASE_IMAGE = "image";
    public static final String FIREBASE_DISCRIPTION = "discr";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String DEVICE_ID = "deviceid";
    public static final String DEVICE = "device";
    public static final String APP = "app";
    public static final String PROMO_CODE = "Promocode : ";

    /*filter*/
    public static final String REFINE = "refine";
    public static final String SORT = "sort";
    public static final String OFFER = "Offer";
    public static final String BRAND = "Brand";
    public static final String PRICE = "Price";
    public static final String FILTER_OBJ = "filterObj";
    public static final String SOURCE_PRODUCT_LIST_ACT = "source_product_list_act";
    public static final String SOURCE_PRODUCT_MAIN_LIST_FRAG = "source_product_main_list_frag";
    public static final String SOURCE_PRODUCT_LIST_FRAG = "source_product_list_frag";
    public static final String SOURCE_PRODUCT_DETAILS = "source_product_details";
    public static final String SOURCE_BRANDS_LIST_FRAG = "source_brands_list_frag";


    ///product list input types
    public static final String SOURCE_OFFERS = "SOURCE_OFFERS";
    public static final String TYPE_DETAIL = "details";
    public static final String TYPE_SEARCH = "search";
    public static final String TYPE_CATEGOTY = "category";
    public static final String TYPE_SKU = "sku";
    public static final String TYPE_BRAND = "brand";
    public static final String EXPLORE = "explore";

    /*home page banner adapter navaigation*/
    public static final String TYPE_MAIN_CATEGORY = "Main category Banner";
    public static final String TYPE_RV_BANNER_ADPTER = "rvBanner1Adapter";
    public static final String TYPE_RV_BANNER_2ADPTER = "rvBanner1Adapter2";
    public static final String TYPE_RV_BANNER_3ADPTER = "rvBanner3Adapter";
    public static final String TYPE_RV_BANNER_6ADPTER = "rvBanner6Adapter";
    public static final String TYPE_RV_BANNER_7 = "rvBanner7Adapter";
    public static final String TYPE_RV_BANNER_8 = "rvBanner8Adapter";
    public static final String BANNER_TYPE_VP = "vpBannerAdapter ";
    public static final String TYPE_BANNER_DETAIL = "Banner detail";
    public static final String TYPE_BANNER_MAIN_CAT_ADAPTER = "main cat adapter";
    public static final String TYPE_ADDRESS_LIST_ADAPTER = "mAddressListAdapter";
    public static final String TYPE_BANNER_ADPTER_10 = "rvBanner10Adapter";
    public static final String TYPE_TRENDING_ADAPTER = "Trending Adapter";
    public static final String TYPE_BEST_PRICES_ADAPTER = "Best Prices Adapter";

    public static final String TOP_EXPLORE = "TOP_EXPLORE";
    public static final String BOTOM_EXPLORE = "BOTOM_EXPLORE";


    /*Exploreview */
    public static final String ADVERTISEMENT = "Advertisement";
    public static final String EXTRA_EXPLORE_URL = "Explore Url";


    /*subscribe*/
    public static final String SUBSCRIBE_DIALOG_FRAGMENT = "subscribe_dialog_fragment";

    ///View ALl input types
    public static final String TYPE_SPECIFIC_PRODUCT_1 = "specific_product1";
    public static final String TYPE_SPECIFIC_PRODUCT_2 = "specific_product2";
    public static final String TYPE_SIMILAR_PRODUCTS = "similar products";
    public static final String TYPE_BRANDS = "brands";
    public static final String TYPE_TOP_PRODUCTS = "top_products";
    public static final String TYPE_ALL_PRODUCTS = "all_products";
    public static final String EXTRA_PRODUCT_LIST_TITLE = "title";
    /*TIMERS HANDLER BANNERS*/

    public static final int BANNER_ROTATION_TIMER_7SECOND = 7000;
    public static final int BANNER_ROTATION_TIMER_15SECOND = 15000;



}
