package com.yesspree.app.screens.sub_sub_category;

        import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
        import com.yesspree.app.modelapi.CartModifyParam;
        import com.yesspree.app.modelapi.FetchCartResponse;
        import com.yesspree.app.modelapi.ModifyWishlistResponse;
        import com.yesspree.app.modelapi.SubSubCatResponseModel;
import com.yesspree.app.network.ApiRequestParam;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 10-05-2018.
 */

public class Sub_SubCategoryPresenter implements ISub_SubCategoryPresenter, IResponseInterface {

    private ISub_SubCategoryView iCategoryView;
    private IRequestInterface requestInterface;

    public Sub_SubCategoryPresenter(ISub_SubCategoryView categoryView) {
        this.iCategoryView = categoryView;
        this.requestInterface = new ApiResponsePresenter(this);
    }

    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {

        iCategoryView.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.SUB_SUB_CATEGORY: {
                    iCategoryView.setCategoryApiRes((SubSubCatResponseModel) response.body());
                }
                break;
                case ApiRequestTypes.MODIFY_CART:
                    iCategoryView.setCartRes((FetchCartResponse) response.body());
                    break;

                case ApiRequestTypes.MODIFY_WISH_LIST:
                    iCategoryView.setModifyWishListRes((ModifyWishlistResponse) response.body());
                    break;

            }
        } else
            iCategoryView.showViewState(MultiStateView.VIEW_STATE_EMPTY);

    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {

        iCategoryView.hideLoader();
        switch (reqType) {
            case ApiRequestTypes.DASHBOARD:
                iCategoryView.showViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
            case ApiRequestTypes.MODIFY_CART:
                iCategoryView.setCartRes(null);
        }

    }

    @Override
    public void callSubSubCategory(String mCatId) {

        requestInterface.callApi(AppController.getInstance().service.getSubSubCategoryData(ApiRequestParam.getInstance().getSubSubCategoryData(mCatId)), ApiRequestTypes.SUB_SUB_CATEGORY);


    }
    @Override
    public void modifyCart(CartModifyParam modifyParam) {
        requestInterface.callApi(AppController.getInstance().service.modifyCart(modifyParam), ApiRequestTypes.MODIFY_CART);
    }

    @Override
    public void modifyWishList(String WishListType, String productId,String customerId,String Session) {
        requestInterface.callApi(AppController.getInstance().service.getModifyWishlist(ApiRequestParam.getInstance().modifyWishlist(WishListType, productId, customerId, Session)), ApiRequestTypes.MODIFY_WISH_LIST);
    }


}
