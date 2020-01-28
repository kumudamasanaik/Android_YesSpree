package com.yesspree.app.screens.faq;

import com.yesspree.app.AppController;
import com.yesspree.app.apipresenter.ApiResponsePresenter;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;
import com.yesspree.app.modelapi.FAQRespModel;
import com.yesspree.app.network.ApiRequestTypes;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by FuGenX-14 on 03-05-2018.
 */

public class FAQPresenter implements IResponseInterface, IFAQPresenter {

    private IFAQView faqview;
    private IRequestInterface requestInterface;
    public String TAG = "FAQPresenter";

    public FAQPresenter(IFAQView faqview) {
        this.faqview = faqview;
        this.requestInterface = new ApiResponsePresenter(this);
    }


    @Override
    public void onResponseSuccess(Call call, Response response, String reqType) {
        faqview.hideLoader();
        if (response.isSuccessful() && response.body() != null) {
            switch (reqType) {
                case ApiRequestTypes.FAQ: {
                    faqview.FaqApiResp((FAQRespModel) response.body());
                }
                break;
            }
        } else
            faqview.showViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onResponseFailure(Call call, Throwable responseError, String reqType) {
        faqview.hideLoader();
        faqview.showViewState(MultiStateView.VIEW_STATE_ERROR);


    }

    @Override
    public void callFAQApiCall() {
        requestInterface.callApi(AppController.getInstance().service.getFAQData(), ApiRequestTypes.FAQ);

    }
}
