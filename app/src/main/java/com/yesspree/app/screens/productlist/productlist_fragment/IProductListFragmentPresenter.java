package com.yesspree.app.screens.productlist.productlist_fragment;

import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.ProductListInput;

/**
 * Created by FuGenX-14 on 08-05-2018.
 */

public interface IProductListFragmentPresenter {
    void callProductList(ProductListInput input,boolean forPagination);

    void modifyCart(CartModifyParam modifyParam);

    void modifyWishList(String mWishLIstType,String ProductId,String customerId,String Session);

}
