package com.yesspree.app.apipresenter;


import com.google.gson.JsonObject;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.AboutusRespModel;
import com.yesspree.app.modelapi.AddSubcrInput;
import com.yesspree.app.modelapi.ApplyCouponRes;
import com.yesspree.app.modelapi.CancelOrder;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.CheckoutArrayResponse;
import com.yesspree.app.modelapi.DashboardResModel;
import com.yesspree.app.modelapi.FAQRespModel;
import com.yesspree.app.modelapi.FetchAddressResponse;
import com.yesspree.app.modelapi.FetchCartResponse;
import com.yesspree.app.modelapi.FetchCartSummaryResponse;
import com.yesspree.app.modelapi.FilterTypeRes;
import com.yesspree.app.modelapi.LastLevelCatRes;
import com.yesspree.app.modelapi.ModifyWishlistResponse;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.modelapi.MyAccountRespModel;
import com.yesspree.app.modelapi.MyOrderStatusRes;
import com.yesspree.app.modelapi.MyOrdersRespModel;
import com.yesspree.app.modelapi.NotificationRespModel;
import com.yesspree.app.modelapi.OffersResModel;
import com.yesspree.app.modelapi.ProductDetailRespModel;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.modelapi.RateUsInputModel;
import com.yesspree.app.modelapi.RateUsRes;
import com.yesspree.app.modelapi.ResponseModel;
import com.yesspree.app.modelapi.SearchProdRes;
import com.yesspree.app.modelapi.SignupRes;
import com.yesspree.app.modelapi.SubCategoryResModel;
import com.yesspree.app.modelapi.SubSubCatResponseModel;
import com.yesspree.app.modelapi.SubscribeProdRes;
import com.yesspree.app.modelapi.UserData;
import com.yesspree.app.modelapi.ViewAllBrandsRespModel;
import com.yesspree.app.network.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestApi {

    @POST(ApiConstants.SIGN_UP)
    Call<SignupRes> signUp(@Body JsonObject jsonObject);

    @POST(ApiConstants.SOCIAL_SIGN_UP)
    Call<UserData> socialsignUp(@Body JsonObject jsonObject);

   /* @POST(ApiConstants.OTP_VERIFY)
    Call<UserData> verifyOtp(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);*/

    @POST(ApiConstants.OTP_VERIFY)
    Call<UserData> verifyOtp( @Body JsonObject jsonObject);

    @POST(ApiConstants.OTP_RESEND)
    Call<ResponseModel> resendOtp(@Header(Constants.AUTHORIZATION) String header);

    @POST(ApiConstants.LOGIN)
    Call<UserData> doLogin(@Body JsonObject jsonObject);


    @POST(ApiConstants.FORGOT_PASSWORD)
    Call<UserData> forgotPasss(@Body JsonObject jsonObject);

    @POST(ApiConstants.UPDATE_USER_DATA)
    Call<UserData> resetPass(@Body JsonObject jsonObject);

    @POST(ApiConstants.DASHBOARD)
    Call<DashboardResModel> getDashboard(@Body JsonObject jsonObject);


    @POST(ApiConstants.SUB_CATEGORY)
    Call<SubCategoryResModel> getSubCategoryData(@Body JsonObject jsonObject);

    @POST(ApiConstants.SUB_SUB_CATEGORY)
    Call<SubSubCatResponseModel> getSubSubCategoryData(@Body JsonObject jsonObject);


    @POST(ApiConstants.MULTI_CATEGORY)
    Call<MultipleCatRespModel> getMultiplecategoryData(@Body JsonObject jsonObject);

    @POST(ApiConstants.LAST_LEVEL_CAT)
    Call<LastLevelCatRes> getLastLevelcat(@Body JsonObject jsonObject);

    @POST(ApiConstants.PRODUCT_LISTING_CATEGORY)
    Call<ProductMainListingRespModel> getProductMainListCat(@Body JsonObject jsonObject);


    @POST(ApiConstants.PRODUCT_LIST)
    Call<ProductMainListingRespModel> getProductList(@Body JsonObject jsonObject);


    @POST(ApiConstants.PRODUCT_DETAIL)
    Call<ProductDetailRespModel> getProductDetail(@Body JsonObject jsonObject);

    @POST(ApiConstants.MODIFY_CART_URL)
    Call<FetchCartResponse> modifyCart(@Body CartModifyParam jsonObject);


    @POST(ApiConstants.REMOVE_PRODUCT_FROM_CART_URL)
    Call<FetchCartResponse> removeFromcart(@Body CartModifyParam jsonObject);

    @POST(ApiConstants.FETCH_CART_URL)
    Call<FetchCartResponse> fetchCart(@Body JsonObject jsonObject);

    @POST(ApiConstants.MODIFY_WISH_LIST)
    Call<ModifyWishlistResponse> getModifyWishlist(@Body JsonObject jsonObject);

    @POST(ApiConstants.ADD_ADDRESS)
    Call<FetchAddressResponse> addAddressData(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);

    @POST(ApiConstants.EDIT_ADDRESS)
    Call<FetchAddressResponse> editAddressData(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);

    @POST(ApiConstants.GET_ADDRESS)
    Call<FetchAddressResponse> getAddressData(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);

    @POST(ApiConstants.DELETE_ADDRESS)
    Call<FetchAddressResponse> deleteAddress(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);


    @POST(ApiConstants.SELECTING_ADDRESS)
    Call<FetchAddressResponse> selectingAddressApi(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);


    @POST(ApiConstants.ORDER_SUMMERY)
    Call<FetchCartSummaryResponse> getOrderSummery(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);


    @POST(ApiConstants.ORDERS)
    Call<MyOrdersRespModel> getMyOrderList(@Header(Constants.AUTHORIZATION) String header, @Body JsonObject jsonObject);

    @POST(ApiConstants.CANCEL_ORDERS)
    Call<CancelOrder> cancelOrder(@Body JsonObject jsonObject);

    @POST(ApiConstants.MY_ORDERS_STATUS)
    Call<MyOrderStatusRes> getMyOrderStatus(@Body JsonObject jsonObject);

    @POST(ApiConstants.CHECK_OUT)
    Call<CheckoutArrayResponse> checkoutApi(@Body JsonObject jsonObject);


    @GET(ApiConstants.ABOUT_US)
    Call<AboutusRespModel> getAboutus();

    @GET(ApiConstants.FAQ)
    Call<FAQRespModel> getFAQData();

    @POST(ApiConstants.RATE_US)
    Call<RateUsRes> submitRatings(@Body RateUsInputModel rateUsInputModel);

    @POST(ApiConstants.REG_FIREBASE_TOKEN)
    Call<ResponseModel> getFireBaseTokenResp(@Body JsonObject jsonObject);

    @POST(ApiConstants.NOTIFICATION_LIST)
    Call<NotificationRespModel> getNotificationList(@Body JsonObject jsonObject);

    @POST(ApiConstants.FILTER_TYPE)
    Call<FilterTypeRes> getFilterType(@Body JsonObject jsonObject);

    @POST(ApiConstants.MY_ACCOUNT)
    Call<MyAccountRespModel> getMyAccountDetails(@Header(Constants.AUTHORIZATION) String authKey);


    @POST(ApiConstants.UPDATE_MY_ACCOUNT)
    Call<MyAccountRespModel> saveMyAccountDetails(@Body JsonObject jsonObject);

    @GET(ApiConstants.OFFERS_LIST)
    Call<OffersResModel> getOffersLIst();

    @POST(ApiConstants.FILTER_RESULT)
    Call<ProductMainListingRespModel> getFilterResult(@Body ProductListInput input);

    @POST(ApiConstants.SKU_RESULTS)
    Call<ProductMainListingRespModel> getSkuResults(@Body ProductListInput input);

    @POST(ApiConstants.OFFERS_SEARCH_PRODUCTS)
    Call<ProductMainListingRespModel> getOffersSearchResults(@Body ProductListInput input);

    @POST(ApiConstants.SEARCH_PRODUCTS)
    Call<SearchProdRes> getSearchResult(@Body ProductListInput input);

    @POST(ApiConstants.EXPLORE_VIEW)
    Call<ResponseModel> exploreViewClicked(@Body JsonObject jsonObject);

    @POST(ApiConstants.SUBSCRIBE)
    Call<SubscribeProdRes> subscribe(@Header(Constants.AUTHORIZATION)String header,@Body AddSubcrInput input);

    @POST(ApiConstants.VIEW_ALL)
    Call<ProductMainListingRespModel> getViewAllProducts(@Body ProductListInput input);

    @POST(ApiConstants.MY_SUBSCRIPTIONS)
    Call<MyOrdersRespModel> mySubscribtion(@Header(Constants.AUTHORIZATION)String header,@Body  JsonObject jsonObject);

    @POST(ApiConstants.CANCEL_SUBSCRIPTIONS)
    Call<CancelOrder> cancelSubscr(@Body JsonObject jsonObject);

    @POST(ApiConstants.APPLY_COUPON)
    Call<ApplyCouponRes> applyCoupon(@Header(Constants.AUTHORIZATION)String header,@Body  JsonObject jsonObject);

    @POST(ApiConstants.VIEW_ALL)
    Call<ViewAllBrandsRespModel> getViewAllBrands(@Body ProductListInput input);

}
