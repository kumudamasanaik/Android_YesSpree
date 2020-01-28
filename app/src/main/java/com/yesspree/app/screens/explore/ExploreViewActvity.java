package com.yesspree.app.screens.explore;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExploreViewActvity extends SubBaseActivity implements IExploreView {
    private String source;
    private String TAG = "ExploreViewActvity";
    private String exploreUrl = "";
    private Context mContext;
    @BindView(R.id.web_view)
    WebView web_view;


    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.app_name));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_explore_view_actvity, fragmentLayout);
        ButterKnife.bind(this);
        mContext=this;
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            exploreUrl = getIntent().getStringExtra(Constants.EXTRA_EXPLORE_URL);
            MyLogUtils.d(TAG, source);
        }
        setSpecificScreenData();
        init();
    }

    @Override
    public void init() {
        setupWebView();
    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(mContext, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), false);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    private void setupWebView() {
        web_view.setWebViewClient(new MyWebViewClient());
        web_view.clearCache(true);
        web_view.clearHistory();
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        web_view.getSettings().setSupportMultipleWindows(false);
        web_view.getSettings().setSupportZoom(false);
        web_view.getSettings().setDomStorageEnabled(true);
        web_view.setVerticalScrollBarEnabled(false);
        web_view.setHorizontalScrollBarEnabled(false);
        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress >= 95)
                    hideLoader();
                else
                    showLoader(null);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //tvToolbarTitle.setText(title);
            }
        });

        web_view.loadUrl(exploreUrl);
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView webView, WebResourceRequest reques, WebResourceError error) {
            super.onReceivedError(webView, reques, error);
            hideLoader();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoader(null);
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);
            hideLoader();
        }
    }


}
