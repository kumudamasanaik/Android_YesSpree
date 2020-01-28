package com.yesspree.app.screens.aboutus;

import com.yesspree.app.modelapi.AboutusRespModel;
import com.yesspree.app.screens.common.IBaseView;

/**
 * Created by FuGenX-14 on 01-05-2018.
 */

public interface IAboutusView extends IBaseView{

    void callAboutusApi();

    void setAboutusApiResp(AboutusRespModel aboutusApiResp);

    void showViewState(int state);
}
