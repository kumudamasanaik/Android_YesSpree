package com.yesspree.app.screens.otp;

public interface IOtpPresenter {

    void verifyOtp(String header, String otp);

    void resenOtp(String header);
}
