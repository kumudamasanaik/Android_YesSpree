package com.yesspree.app.utility;

import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.ResponseModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validation {
    private static String USER_PATTERN;
    private static String EMAIL_PATTERN;
    private static String MOBILE_PATTERN;


    public static boolean isValidStatus(ResponseModel object) {
        if (object != null && object.getStatus() != null && object.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
            if (isValidString(object.getSession()))
                CommonUtils.setSession(null, object.getSession());
            if (isValidString(object.getKey()))
                CommonUtils.setAuthorizationKey(null, object.getKey());
            return true;
        } else
            return false;
    }

    public static boolean isFailureStatus(ResponseModel object) {
        return object != null && object.getStatus() != null && object.getStatus().equalsIgnoreCase(Constants.FAILURE);
    }

    public static boolean isValidStrMsg(ResponseModel object) {
        return object != null && Validation.isValidString(object.getMessage());
    }

    public static boolean isValidThrowableMsg(ResponseModel object) {
        return object != null && Validation.isValidObject(object.getThrowable());
    }

    public static boolean isRejectedStatus(ResponseModel object) {
        return object != null && object.getStatus() != null && object.getStatus().equalsIgnoreCase(Constants.REJECTED);
    }

    public static boolean isNotVerifiedStatus(ResponseModel object) {
        return object != null && object.getStatus() != null && object.getStatus().equalsIgnoreCase(Constants.NOT_VERIFIED);
    }

    public static boolean isValidString(String string) {
        return string != null && string.trim().length() > 0;
    }

    public static boolean isValidStrWithLength(String string,int length) {
        return string != null && string.trim().length() > length;
    }

    public static boolean isValidDigit(float integer) {
        return (integer > 0 ? true : false);
    }

    public static boolean isValidDoubl(Double data) {
        return (data > 0 ? true : false);
    }

    public static boolean isValidObject(Object object) {
        return (object != null) ? true : false;
    }


    public static boolean isValidList(List list) {
        return list != null && list.size() > 0;
    }

    public static String getSecureEmail(String email) {
        try {
            if (email != null && email.trim().length() > 0 && isValidEmail(email))
                return email.replaceAll("(?<=.{3}).(?=.*@)", "*");
            else
                return "-";
        } catch (Exception ex) {
            return "-";
        }
    }


    public static boolean isValidPersonName(String name) {
        USER_PATTERN = "^[a-zA-Z ]{3,30}$";
        Pattern pattern = Pattern.compile(USER_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidUser(String user) {
        USER_PATTERN = "^[a-zA-Z ]{3,20}$";
        Pattern pattern = Pattern.compile(USER_PATTERN);
        Matcher matcher = pattern.matcher(user);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidMobile(String phone) {
        MOBILE_PATTERN = "[0-9]{10}";
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isValidPassword(String pass) {
        return pass != null && pass.length() >= 6;
    }

    public static boolean isValidPinCode(String pinCode) {
        return pinCode != null && pinCode.length() == 6;
    }

    public static boolean isValidOtp(String string) {
        return string != null && string.trim().length() == 4;
    }

    public static boolean isStrIsInt(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


}
