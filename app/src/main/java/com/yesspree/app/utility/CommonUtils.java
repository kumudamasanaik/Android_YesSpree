package com.yesspree.app.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yesspree.app.AppController;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.IDialogClickListener;
import com.yesspree.app.interfaces.IPaymentSelection;
import com.yesspree.app.interfaces.IProductListClickListener;
import com.yesspree.app.interfaces.ISelectedDateListener;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.modelapi.CartModifyParam;
import com.yesspree.app.modelapi.CartSummaryData;
import com.yesspree.app.modelapi.CustomerData;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.modelapi.Sku;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.productlist.ProductListActivity;
import com.yesspree.app.screens.banner_offersdetail.BannerBannerOffersDetailsActivity;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.explore.ExploreViewActvity;
import com.yesspree.app.screens.login.LoginActivity;
import com.yesspree.app.screens.viewall.ViewAllActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CommonUtils {
    private static ProgressDialog pDialog;
    private static String TAG = "CommonUtils";

    public static String generateSession(Context context) {
        try {
            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 16; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            String randomString = sb.toString() + "_" + new SimpleDateFormat("ddMMyyyyhhmmssSSS").format(new java.util.Date());
            setSession(context, randomString);
            return randomString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getSession(Context context) {
        String session = (String) SharedPreferenceManger.getPrefVal(context, Constants.SESSION, null, SharedPreferenceManger.VALUE_TYPE.STRING);
        if (session == null) {
            MyLogUtils.e("SESSION", "SESSION IS NULL");
            return generateSession(context);
        }
        return session;
    }

    public static void showLoading(Context mContext, String message, boolean cancelable) {
        try {
            CommonUtils.hideLoading();
            pDialog = new ProgressDialog(mContext, R.style.AppTheme_Loading_Dialog);
            pDialog.setMessage(message);
            pDialog.setCancelable(cancelable);
            pDialog.setOnCancelListener(dialog -> {
                //AppController.getInstance().getRequestQueue().cancelAll(tag);
                dialog.dismiss();
            });
            pDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void hideLoading() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
        pDialog = null;
    }

    public static void showToastMsg(Context context, String msg, int length) {
        if (context != null && msg != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.custome_toast, null);
            TextView text = layout.findViewById(R.id.text);
            text.setText(msg);
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(length);
            toast.setView(layout);
            toast.show();
        }

        /*if (context != null && msg != null)
            Toast.makeText(context, msg, length).show();*/
    }

    public static void blockUserInteraction(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void unBlockUserInteraction(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void setIsLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferenceManger.setPrefVal(context, Constants.IS_LOGGED_IN, isLoggedIn, SharedPreferenceManger.VALUE_TYPE.BOOLEAN);
    }


    public static void setLogout(Context context) {
        try {
            String pincode = CommonUtils.getPincode(context);
            AddressData addressData = SharedPreferenceManger.getAddressData(context);
            SharedPreferenceManger.clearPreferences(context);
            CommonUtils.setPincode(context, pincode);
            SharedPreferenceManger.saveAddress(context, new Gson().toJson(addressData));
            Intent intent = new Intent(context, DashBoardActivity.class);
            intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish();
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    public static void doLogin(Context context, String source) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.SOURCE, source);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
    }

    public static boolean isLoggedIn(Context context) {
        if (context == null)
            context = AppController.getInstance().getApplicationContext();
        return (boolean) SharedPreferenceManger.getPrefVal(context, Constants.IS_LOGGED_IN, false, SharedPreferenceManger.VALUE_TYPE.BOOLEAN);
    }

    public static boolean checkLoginStatus(Context context, String msg) {
        if (context == null)
            context = AppController.getInstance().getApplicationContext();
        if (isLoggedIn(context))
            return true;
        else {
            //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra(Constants.SOURCE, Constants.SOURCE_DASH_BOARD);
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /*public static void logout(Context context) {
        try {
            SharedPreferenceManger.clearPreferences(context);
            Intent intent = new Intent(context, DashBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    public static void setIsTempLoggedIn(Context context, boolean isTempLoggedIn) {
        SharedPreferenceManger.setPrefVal(context, Constants.IS_TEMP_LOGGED_IN, isTempLoggedIn, SharedPreferenceManger.VALUE_TYPE.BOOLEAN);
    }

    public static boolean isTempLoggedIn(Context context) {
        return (boolean) SharedPreferenceManger.getPrefVal(context, Constants.IS_TEMP_LOGGED_IN, false, SharedPreferenceManger.VALUE_TYPE.BOOLEAN);
    }

    public static void setSession(Context context, String session) {
        if (context == null)
            context = AppController.getInstance().getApplicationContext();
        MyLogUtils.i("SESSION", "SetSession " + session);
        SharedPreferenceManger.setPrefVal(context, Constants.SESSION, session, SharedPreferenceManger.VALUE_TYPE.STRING);
        SharedPreferenceManger.setPrefVal(context, Constants.SESSION_TIME, System.currentTimeMillis(), SharedPreferenceManger.VALUE_TYPE.LONG);
    }

    public static void setAuthorizationKey(Context context, String authorizationKey) {
        if (context == null)
            context = AppController.getInstance().getApplicationContext();
        MyLogUtils.i("AuthorizationKey", "AuthorizationKey " + authorizationKey);
        SharedPreferenceManger.setPrefVal(context, Constants.AUTHORIZATION_KEY, authorizationKey, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getAuthorizationKey(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.AUTHORIZATION_KEY, "", SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getCustomerId(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.CUSTOMER_ID, "0", SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getCustomerName(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.NAME, null, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getCustomerPic(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.PIC, null, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getCustomerMobileNmbr(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.MOBILE, null, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static void setCustomerData(Context context, CustomerData customerData) {
        try {
            if (validateValue(customerData.getId())) {
                SharedPreferenceManger.setPrefVal(context, Constants.CUSTOMER_ID, customerData.getId(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.NAME, customerData.getFirstName(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.FIRST_NAME, customerData.getFirstName(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.LAST_NAME, customerData.getLastName(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.EMAIL, customerData.getEmail(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.MOBILE, customerData.getMobile(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.FACEBOOK_ID, customerData.getFacebookId(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.GOOGLE_ID, customerData.getGoogleId(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.CUSTOMER_TYPE, customerData.getCustomerType(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.GENDER, customerData.getGender(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.PIC, customerData.getPic(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.STATUS, customerData.getStatus(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.EMAIL_VERIFIED, customerData.getEmailVerified(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.REFERRAL_CODE, customerData.getReferralCode(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.REFERRED_BY, customerData.getReferredBy(), SharedPreferenceManger.VALUE_TYPE.STRING);
                SharedPreferenceManger.setPrefVal(context, Constants.REFERRAL_USED, customerData.getReferredUsed(), SharedPreferenceManger.VALUE_TYPE.STRING);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean validateValue(String value) {
        return (value != null && value.length() > 0);
    }

    public static void setPincode(Context context, String pincode) {
        SharedPreferenceManger.setPrefVal(context, Constants.PINCODE, pincode, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getPincode(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.PINCODE, null, SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void setFireBaseTokenID(Context context, String token) {
        if (context == null)
            context = AppController.getInstance().getApplicationContext();
        MyLogUtils.i(TAG, "FIRE BASE TOKEN SAVED  " + token);
        SharedPreferenceManger.setPrefVal(context, Constants.FIREBASE_TOKEN, token, SharedPreferenceManger.VALUE_TYPE.STRING);
    }


    public static String getFireBaseTokenID(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.FIREBASE_TOKEN, null, SharedPreferenceManger.VALUE_TYPE.STRING);
    }


    public static void setViewState(Context context, MultiStateView multiStateView, int viewState, TextView tvView, ImageView ivView, String msg) {
        try {
            switch (viewState) {
                case MultiStateView.VIEW_STATE_CONTENT:
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    break;
                case MultiStateView.VIEW_STATE_LOADING:
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    break;
                case MultiStateView.VIEW_STATE_EMPTY:
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    if (msg != null)
                        tvView.setText(msg);
                    else
                        tvView.setText(context.getString(R.string.error_something_wrong));
                    //	ivView.setImageResource(R.drawable.icn_empty);
                    break;
                case MultiStateView.VIEW_STATE_ERROR:
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                    if (msg != null)
                        tvView.setText(msg);
                    else
                        tvView.setText(context.getString(R.string.error_no_internet));
                    //	ivView.setImageResource(R.drawable.icn_no_internet);
                    break;
                case MultiStateView.VIEW_STATE_WRONG:
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                    if (msg != null)
                        tvView.setText(msg);
                    else
                        tvView.setText(context.getString(R.string.error_something_wrong));
                    //	ivView.setImageResource(R.drawable.icn_wrong);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getStrWithRsSym(Context context, float price) {
        return context.getString(R.string.Rs) + String.valueOf(price);
    }

    public static void strikeThrText(TextView textView) {
        if (textView != null)
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


    public static String getReferralCode(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.REFERRAL_CODE, "", SharedPreferenceManger.VALUE_TYPE.STRING);
    }

    public static String getAppShareMessage(Context context) {
        return (String) SharedPreferenceManger.getPrefVal(context, Constants.APP_SHARE_MESSAGE, null, SharedPreferenceManger.VALUE_TYPE.STRING);
    }


    public static void shareApplication(Context context, String ShareMsg) {
        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            // String shareBody = CommonUtils.getAppShareMessage(context).replaceAll("REFERRAL_CODE", CommonUtils.getReferralCode(context));
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Yesspree");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ShareMsg);
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void SharingToSocialMedia(Context mContext, String application, String ShareMsg) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        // String shareBody = CommonUtils.getAppShareMessage(context).replaceAll("REFERRAL_CODE", CommonUtils.getReferralCode(context));
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Yesspree");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ShareMsg);


        boolean installed = checkAppInstall(mContext, application);
        if (installed) {
            sharingIntent.setPackage(application);
            mContext.startActivity(sharingIntent);
        } else {
            Toast.makeText(mContext.getApplicationContext(),
                    "Installed application first", Toast.LENGTH_LONG).show();
        }

    }


    private static boolean checkAppInstall(Context mContext, String uri) {
        PackageManager pm = mContext.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    public static void initShareIntent(Context context, String type, String shareMsg) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    share.putExtra(Intent.EXTRA_SUBJECT, "YESPREE");
                    share.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Toast.makeText(context, "Please install  App", Toast.LENGTH_SHORT).show();
                return;
            }

            context.startActivity(Intent.createChooser(share, "Select"));
        }
    }

    public static void addFragment(Fragment selectedFragment, FragmentActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, selectedFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showSkuDialog(Context context, ProductData data, IDialogClickListener dialogClickListener) {
        {
            if (context != null && data != null && Validation.isValidList(data.getSkuData()) && dialogClickListener != null) {
                ArrayList<Sku> skuArrayList = data.getSkuData();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dailogview = inflater.inflate(R.layout.partial_popup_choose_quantity, null);
                LinearLayout dynamicLayout = dailogview.findViewById(R.id.dynamic_layout);
                TextView productName = dailogview.findViewById(R.id.product_name);
                if (Validation.isValidString(data.getName()))
                    productName.setText(data.getName());
                builder.setView(dailogview);
                final AlertDialog dialog = builder.create();
                dynamicLayout.removeAllViews();
                for (int i = 0; i < skuArrayList.size(); i++) {
                    Sku skuData = skuArrayList.get(i);
                    View childLayout = LayoutInflater.from(context).inflate(R.layout.partial_popup_choose_quantity_list_item, null);
                    TextView title = childLayout.findViewById(R.id.tv_quantity_name);
                    AppCompatRadioButton checkBx = childLayout.findViewById(R.id.cbSelected);
                    if (Validation.isValidObject(data.getSelSku()) && Validation.isValidString(data.getSelSku().get_id())) {
                        if (Validation.isValidString(skuData.get_id()) && skuData.get_id().equals(data.getSelSku().get_id()))
                            checkBx.setChecked(true);
                        else
                            checkBx.setChecked(false);
                    }
                    if (Validation.isValidString(skuData.getSize()))
                        title.setText(skuData.getSize());
                    final int finalI = i;
                    childLayout.setOnClickListener(view -> {
                        data.setSelSku(skuData);
                        data.setSelectedSkuPosition(finalI);
                        dialogClickListener.selectedItem(data);
                        checkBx.setChecked(true);
                        MyLogUtils.e(TAG, "selecte Sku :" + data.getSelSku().toString());
                        dialog.cancel();
                    });
                    dynamicLayout.addView(childLayout);
                }
                dialog.show();
            }
        }
    }

    public static boolean validateValues(EditText editText, String msg) {
        if (editText.getText() != null && editText.getText().toString().trim().length() > 0) {
            editText.setError(null);
            return true;
        } else {
            editText.setError(msg);
            return false;
        }
    }

    public static void showConfirmationDialog(Context context, Object listener, String header, String body, Object data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dailogview = inflater.inflate(R.layout.partail_popup_confirmation_dialog, null);
        TextView dialog_header = dailogview.findViewById(R.id.dialog_header);
        TextView dialog_body = dailogview.findViewById(R.id.dialog_body);

        TextView ok = dailogview.findViewById(R.id.btn_ok);
        TextView cancel = dailogview.findViewById(R.id.btn_cancel);

        dialog_header.setText(header);
        dialog_body.setText(body);
        builder.setView(dailogview);

        final AlertDialog dialog = builder.create();
        ok.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener instanceof IAdapterClickListener) {
                IAdapterClickListener perform = (IAdapterClickListener) listener;
                perform.onAdapterClick(data, 0, null, Constants.DELETE_CONFIRMATION);
            }
            if (listener instanceof IProductListClickListener) {
                if (data != null && data instanceof CartModifyParam) {
                    IProductListClickListener perform = (IProductListClickListener) listener;
                    perform.onClickProduct(data, 0, Constants.CART_REMOVE, Constants.DELETE_CONFIRMATION);
                }
            }
        });


        cancel.setOnClickListener(v -> {
            dialog.dismiss();

        });
        dialog.show();


    }

    public static void goToDashBoard(Context context) {
        try {
            Intent intent = new Intent(context, DashBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static LinearLayoutManager getRecyclerLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

    }

    public static GridLayoutManager getRecyclerGridLayoutManager(Context context, int count) {
        return new GridLayoutManager(context, count, GridLayoutManager.VERTICAL, false);

    }


    public static Bitmap getBitMapURL(String imageURL) {
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

    public static void startActivity(Context mContext, Class activity, Bundle bundle) {
        Intent move = new Intent(mContext, activity);
        move.putExtras(bundle);
        mContext.startActivity(move);
    }


    public static void goToDialPad(Context context, String mobileNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mobileNumber));
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void goToEmailApp(Context context, String emailId, String subject) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailId, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ProductListInput getProductListInputCommonParameter(Context context) {
        ProductListInput productListInput = new ProductListInput();
        productListInput.set_id(CommonUtils.getCustomerId(context));
        productListInput.set_session(CommonUtils.getSession(context));
        productListInput.setWh_pincode(CommonUtils.getPincode(context));
        return productListInput;
    }

    public static void startProductListActvity(Context context, ProductListInput productListInput) {
        Intent move = new Intent(context, ProductListActivity.class);
        move.putExtra(Constants.SOURCE, Constants.SOURCE_OFFERS);
        move.putExtra(Constants.FILTER_OBJ, productListInput);
        context.startActivity(move);

    }

    public static void navigateBannerData(Context context, String TargetType, String Target) {
        Bundle bundle = null;
        if (Validation.isValidString(TargetType)) {

            switch (TargetType) {
                case Constants.TYPE_DETAIL:
                    bundle = new Bundle();
                    bundle.putString(Constants.SOURCE, Constants.OFFERS_DETAIL);
                    //  bundle.putParcelable(Constants.EXTRA_OFFERS_DATA, (Parcelable) offers); // TO DO need to pass respectiv object //
                    CommonUtils.startActivity(context, BannerBannerOffersDetailsActivity.class, bundle);
                    break;
                case Constants.TYPE_SEARCH:
                    if (Validation.isValidString(Target)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setSearch(Target);
                        startProductListActvity(context, productListInput);
                    }
                    break;
                case Constants.TYPE_CATEGOTY:
                    if (Validation.isValidString(Target)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setCategory(Target);
                        startProductListActvity(context, productListInput);
                    }
                    break;
                case Constants.TYPE_SKU:
                    if (Validation.isValidString(Target)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setSku(Target);
                        CommonUtils.startProductListActvity(context, productListInput);
                    }
                    break;
                case Constants.TYPE_BRAND:
                    if (Validation.isValidString(Target)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setBrand(Target);
                        startProductListActvity(context, productListInput);
                    }
                    break;

                case Constants.EXPLORE:
                    if (Validation.isValidString(Target)) {
                        bundle = new Bundle();
                        bundle.putString(Constants.SOURCE, Constants.OFFERS_DETAIL);
                        bundle.putString(Constants.EXTRA_EXPLORE_URL, Target);
                        CommonUtils.startActivity(context, ExploreViewActvity.class, bundle);
                    }
                    break;
                default:
                    MyLogUtils.d(TAG, " Banner data TargetType  : " + TargetType + " Banner data Target : " + Target);
            }
        }

    }

    public static void showCalenderPickerForReports(Context context, ISelectedDateListener listener, long min, long max) {
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.MyDatePickerDialogTheme,
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar selCal = Calendar.getInstance();
                    selCal.setTimeInMillis(0);
                    selCal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    listener.setSelectedDate(String.format("%1$ta, %1$td %1$tb  %1$tY", selCal));
                }, mYear, mMonth, mDay);
        if (min >= 0)
            datePickerDialog.getDatePicker().setMinDate(min);
        if (max >= 0)
            datePickerDialog.getDatePicker().setMaxDate(max);
        else
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.setTitle("");
        datePickerDialog.show();
    }

    public static void leftRotateDoorBellIcon(AppCompatImageView myImg) {
        ObjectAnimator objectanimator = ObjectAnimator.ofFloat(myImg, "rotation", 45);
        objectanimator.setDuration(100);
        objectanimator.start();
        objectanimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startRotateDoorBellIcon(myImg);
            }
        });

    }

    public static void startRotateDoorBellIcon(AppCompatImageView myImg) {
        ObjectAnimator objectanimator = ObjectAnimator.ofFloat(myImg, "rotation", 0);
        objectanimator.setDuration(100);
        objectanimator.start();

    }

    public static void playDoorBellMuzic(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.doorbell);
        mp.start();
    }

    public static void showPaymentDialog(Context context, IPaymentSelection listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dailogview = inflater.inflate(R.layout.partail_popup_confirmation_dialog, null);
        TextView dialog_header = dailogview.findViewById(R.id.dialog_header);
        TextView dialog_body = dailogview.findViewById(R.id.dialog_body);

        TextView cod = dailogview.findViewById(R.id.btn_ok);
        TextView online = dailogview.findViewById(R.id.btn_cancel);
        cod.setText(context.getString(R.string.cod));
        online.setText(context.getString(R.string.online));

        dialog_header.setText(context.getString(R.string.payment_type));
        dialog_body.setText(context.getString(R.string.select_payment_type));
        builder.setView(dailogview);

        final AlertDialog dialog = builder.create();
        cod.setOnClickListener(v -> {
            listener.setPaymentType(cod.getText().toString());
            dialog.dismiss();
        });


        online.setOnClickListener(v -> {
            listener.setPaymentType(online.getText().toString());
            dialog.dismiss();
        });
        dialog.show();


    }

    @SuppressLint("StringFormatInvalid")
    public static void setCartSummery(Context context, CartSummaryData cartSummaryData, TextView tvTotallMrp, TextView tvTotallSavings, TextView tvTotallPay) {
        if (!Validation.isStrIsInt(cartSummaryData.getMrp()) || !Validation.isStrIsInt(cartSummaryData.getGrand_total()))
            return;
        double saved = 0;
        double mrp = Double.parseDouble(cartSummaryData.getMrp());
        double sellingPrice = Double.parseDouble(cartSummaryData.getGrand_total());

        tvTotallMrp.setText(context.getString(R.string.rupees_amt, String.valueOf(roundDoubleDec(mrp, 2))));
        if (mrp > 0)
            saved = (((mrp - sellingPrice) / mrp) * 100);
        tvTotallSavings.setText(context.getString(R.string.rupees_amt_percent, String.valueOf(roundDoubleDec(mrp - sellingPrice, 2)), String.valueOf(roundDoubleDec(saved, 2))));
        tvTotallPay.setText(Validation.isValidString(cartSummaryData.getGrand_total())
                ? context.getString(R.string.rupees_amt, cartSummaryData.getGrand_total()) : "0");
    }

    public static double roundDoubleDec(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static RecyclerView.ItemDecoration getItemDecoration(Context context, LinearLayoutManager linearLayoutManager) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.divider_2dp, null));
        return dividerItemDecoration;
    }


    public static void navigateViewAll(Context context, String TargetType, String toolBarTittle, String subCatId) {
        if (Validation.isValidString(TargetType)) {
            switch (TargetType) {
                case Constants.TYPE_SPECIFIC_PRODUCT_1:
                case Constants.TYPE_SPECIFIC_PRODUCT_2:
                    if (Validation.isValidString(TargetType)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setType(TargetType);
                        Intent move = new Intent(context, ProductListActivity.class);
                        move.putExtra(Constants.SOURCE, Constants.SOURCE_OFFERS);
                        move.putExtra(Constants.FILTER_OBJ, productListInput);
                        move.putExtra(Constants.EXTRA_PRODUCT_LIST_TITLE, toolBarTittle);
                        context.startActivity(move);
                    }
                    break;
                case Constants.TYPE_ALL_PRODUCTS:
                case Constants.TYPE_TOP_PRODUCTS:
                    if (Validation.isValidString(TargetType)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setType(TargetType);
                        if (Validation.isValidString(subCatId))
                            productListInput.setId_subcategory(subCatId);

                        Intent move = new Intent(context, ProductListActivity.class);
                        move.putExtra(Constants.SOURCE, Constants.SOURCE_OFFERS);
                        move.putExtra(Constants.FILTER_OBJ, productListInput);
                        move.putExtra(Constants.EXTRA_PRODUCT_LIST_TITLE, toolBarTittle);
                        context.startActivity(move);
                    }
                    break;


                case Constants.TYPE_SIMILAR_PRODUCTS:
                    if (Validation.isValidString(TargetType)) {
                        ProductListInput productListInput = CommonUtils.getProductListInputCommonParameter(context);
                        productListInput.setType(TargetType);
                        if (Validation.isValidString(subCatId))
                            productListInput.setId_subcategory(subCatId);
                        Intent move = new Intent(context, ProductListActivity.class);
                        move.putExtra(Constants.SOURCE, Constants.SOURCE_OFFERS);
                        move.putExtra(Constants.FILTER_OBJ, productListInput);
                        move.putExtra(Constants.EXTRA_PRODUCT_LIST_TITLE, toolBarTittle);
                        context.startActivity(move);
                    }
                    break;

                case Constants.TYPE_BRANDS:
                    if (Validation.isValidString(TargetType)) {
                        ProductListInput input = new ProductListInput();
                        input.setType(Constants.TYPE_BRANDS);
                        input.set_id(CommonUtils.getCustomerId(context));
                        input.set_session(CommonUtils.getSession(context));
                        input.setWh_pincode(CommonUtils.getPincode(context));
                        Intent intent = new Intent(context, ViewAllActivity.class);
                        intent.putExtra(Constants.SOURCE, Constants.SOURCE_FILTER);
                        intent.putExtra(Constants.FILTER_OBJ, input);
                        context.startActivity(intent);

                    }
                    break;
                default:
                    MyLogUtils.d(TAG, " View All   : " + TargetType);
            }
        }

    }


    public  static  Spannable getSpan(Context mContext,String mainString, String subString) {
        Spannable spanText = null;
        for (int i = -1; (i = mainString.indexOf(subString, i + 1)) != -1; i++) {
            spanText = new SpannableString(mainString);
            spanText.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor
                            (mContext.getResources(), R.color.colordc0000, null)),
                    i, (i + subString.length()),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanText;
    }

}
