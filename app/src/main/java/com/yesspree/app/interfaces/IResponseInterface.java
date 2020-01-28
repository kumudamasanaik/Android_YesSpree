package com.yesspree.app.interfaces;

import retrofit2.Call;
import retrofit2.Response;

public interface IResponseInterface {

    void onResponseSuccess(Call call,Response response, String reqType);

    void onResponseFailure(Call call,Throwable responseError,String reqType);

}
