package com.yesspree.app.interfaces;
import retrofit2.Call;

public interface IRequestInterface {

    void callApi(Call call, final String reqType);
}
