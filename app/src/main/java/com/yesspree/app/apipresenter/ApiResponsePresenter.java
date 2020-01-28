package com.yesspree.app.apipresenter;

import com.yesspree.app.interfaces.IRequestInterface;
import com.yesspree.app.interfaces.IResponseInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiResponsePresenter implements IRequestInterface {

    String TAG = "ApiResponsePresenter";
    public IResponseInterface iResponseInterface;

    public ApiResponsePresenter(IResponseInterface iResponseInterface) {
        this.iResponseInterface = iResponseInterface;
    }

    @Override
    public void callApi(final Call call, final String reqType) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful())
                    iResponseInterface.onResponseSuccess(call, response, reqType);
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                iResponseInterface.onResponseFailure(call, throwable, reqType);
            }
        });
    }
}
