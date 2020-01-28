package com.yesspree.app.screens.search;

import com.yesspree.app.modelapi.SearchProdRes;
import com.yesspree.app.screens.common.IBaseView;

public interface ISearchView extends IBaseView {

    void searchProducts();

    void setSearchProductRes(SearchProdRes res);


}
