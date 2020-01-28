package com.yesspree.app.screens.sub_sub_category;

import com.yesspree.app.modelapi.CartModifyParam;

/**
 * Created by FuGenX-14 on 10-05-2018.
 */

public interface ISub_SubCategoryPresenter {
    void callSubSubCategory(String mCatId);
    void modifyCart(CartModifyParam modifyParam);

    void modifyWishList(String mWishLIstType,String ProductId,String customerId,String Session);


}
