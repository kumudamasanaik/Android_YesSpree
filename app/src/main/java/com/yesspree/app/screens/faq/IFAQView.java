package com.yesspree.app.screens.faq;

import com.yesspree.app.modelapi.FAQRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 03-05-2018.
 */

public interface IFAQView extends IBaseView {
    void callFAQApi();
    void  FaqApiResp(FAQRespModel faqRespModel);
    void showViewState(int state);

}
