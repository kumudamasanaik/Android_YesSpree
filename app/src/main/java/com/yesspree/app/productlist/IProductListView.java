package com.yesspree.app.productlist;


import com.yesspree.app.modelapi.ProductMainListingRespModel;
import com.yesspree.app.screens.common.IBaseView;

public interface IProductListView extends IBaseView {


    void getProductList();

    void setProductListRes(ProductMainListingRespModel res);

    void showViewState(int state);
}
