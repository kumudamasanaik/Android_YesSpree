package com.yesspree.app.screens.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.yesspree.app.BuildConfig;
import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.modelapi.UserData;
import com.yesspree.app.network.NetworkStatus;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.location.CurrentLocActivity;
import com.yesspree.app.screens.login.LoginActivity;
import com.yesspree.app.screens.signup.SignupActivity;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingActivity extends AppCompatActivity implements ILandingView, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.iv_logo_icon)
    AppCompatImageView ivLogoIcon;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.tv_gmail_sigh_up)
    TextView tvGmailUp;
    @BindView(R.id.tv_fb_sigh_up)
    TextView tvFbUp;
    @BindView(R.id.tv_skip)
    TextView tvSkip;

    //variable
    Context context;
    LandingPresenter presenter;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private String socialType;
    private String source;
    private String TAG = "LandingActivityView";
    SocialSignInputModel socialSignInputModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        context = LandingActivity.this;
        mCallbackManager = CallbackManager.Factory.create();
        init();
        googleSignIn();
        facebookSignIn();
        doSpecificNeededSocialLogin();
    }


    private void googleSignIn() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void facebookSignIn() {
        //setAuthListener();
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                MyLogUtils.d(Constants.TAG, "facebook:onSuccess:" + loginResult);
                //handleFacebookAccessToken(loginResult.getAccessToken(), loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                MyLogUtils.d(Constants.TAG, "facebook:onCancel");
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                MyLogUtils.d(Constants.TAG, "facebook:onError:" + error.getMessage());
                updateUI(null);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(String socialType) {
        try {
            if (socialType.equalsIgnoreCase(Constants.SOCIAL_TYPE_GOOGLE)) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                CommonUtils.showLoading(context, getString(R.string.please_wait), true);
                startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
            } else {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        socialType = Constants.SOCIAL_TYPE_GOOGLE;
        MyLogUtils.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            MyLogUtils.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            MyLogUtils.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        CommonUtils.hideLoading();
        if (user != null) {
            socialSignInputModel = new SocialSignInputModel();
            socialSignInputModel.setSocialType(socialType);
            socialSignInputModel.setFirst_name(user.getDisplayName());
            socialSignInputModel.setEmail(user.getEmail());
            socialSignInputModel.setMobile(user.getPhoneNumber());
            socialSignInputModel.setPhotoUrl(user.getPhotoUrl());
            socialSignInputModel.setEmailVerified(user.isEmailVerified());
            socialSignInputModel.setSocId(user.getUid());
            signOut(socialType);
            callSocialSignInApi();
        } else {
            CommonUtils.showToastMsg(context, getString(R.string.err_social_login), 1);
        }
    }

    @Override
    public void callSocialSignInApi() {
        if (NetworkStatus.getInstance().isOnline2(context)) {
            if (socialSignInputModel != null) {
                showLoader(null);
                presenter.doSocialsignUp(socialSignInputModel);
            }
        } else
            Toast.makeText(context, getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void socailLoginRes(UserData res) {
        if (Validation.isValidStatus(res)) {
            if (Validation.isValidList(res.getCustomerList())) {
                CommonUtils.setCustomerData(context, res.getCustomerList().get(0));
                CommonUtils.setIsLoggedIn(context, true);
                navigateToHomeScreen();
            } else
                showMsg((Validation.isValidStrMsg(res)) ? res.getMessage() : null);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        socialType = Constants.SOCIAL_TYPE_FACEBOOK;
        MyLogUtils.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            MyLogUtils.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            MyLogUtils.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }


    private void signOut(String socialType) {
        try {
            // Firebase sign out
            mAuth.signOut();
            if (socialType.equalsIgnoreCase(Constants.SOCIAL_TYPE_GOOGLE)) {
                // Google sign out
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                MyLogUtils.d("ok", "SignOut  Done");
                            }
                        });

            } else {
                LoginManager.getInstance().logOut();
            }
        } catch (Exception exp) {
            MyLogUtils.e(TAG, socialType + ":social logout error ");
        }
    }

    @Override
    public void init() {
        tvGmailUp.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_google_plus), null, null, null);
        tvFbUp.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_facebook), null, null, null);
        presenter = new LandingPresenter(this);

    }


    void doSpecificNeededSocialLogin() {
        if (getIntent() != null) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            if (Validation.isValidString(source) && source.equals(Constants.SOURCE_SIGN)) {
                socialType = getIntent().getStringExtra(Constants.SOCIALTYPE);
                if (Validation.isValidString(socialType) && socialType.equals(Constants.SOCIAL_TYPE_FACEBOOK) || socialType.equals(Constants.SOCIAL_TYPE_GOOGLE)) {
                    signIn(socialType);
                }
            }
        }
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 1);
    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(context, (msg != null) ? msg : getString(R.string.please_wait), false);
    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }

    @Override
    public void navigateToHomeScreen() {

        navigateToHome();

    }

    private void navigateToLocScreen() {


        Intent intent = new Intent(context, CurrentLocActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH);
        startActivity(intent);
        finish();

    }

    private void navigateToHome() {
        Intent intent = new Intent(context, DashBoardActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_LANDING);
        startActivity(intent);
        finish();

    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_LANDING);
        startActivity(intent);
    }

    @Override
    public void navigateSignUpScreen() {
        Intent intent = new Intent(context, SignupActivity.class);
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_LANDING);
        startActivity(intent);
    }

    @OnClick(R.id.tv_sign_up)
    void clickSighup() {
        navigateSignUpScreen();
    }

    @OnClick(R.id.tv_gmail_sigh_up)
    void clickGmailSighup() {
        signIn(Constants.SOCIAL_TYPE_GOOGLE);
    }

    @OnClick(R.id.tv_fb_sigh_up)
    void clickFbSighup() {
        signIn(Constants.SOCIAL_TYPE_FACEBOOK);
    }

    @OnClick(R.id.signIn)
    void clickedSignIn() {

        navigateToLoginScreen();
    }

    @OnClick(R.id.tv_skip)
    void clickSkip() {

        navigateToHome();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                CommonUtils.hideLoading();
                MyLogUtils.w(TAG, "Google sign in failed", e);
            }
        }
    }

    /*private void handleFacebookAccessToken(AccessToken token, LoginResult loginResult) {

        socialType = Constants.SOCIAL_TYPE_FACEBOOK;
        MyLogUtils.d(Constants.TAG, "handleFacebookAccessToken:" + token);
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String userId, firstName, lastName, email, birthday, gender;
                        URL profilePicture;
                        MyLogUtils.d(Constants.TAG, response.toString());
                        try {
                            userId = object.getString(Constants.FB_ID);
                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                            if (object.has(Constants.FB_FIRST_NAME))
                                firstName = object.getString(Constants.FB_FIRST_NAME);
                            if (object.has(Constants.FB_LAST_NAME))
                                lastName = object.getString(Constants.FB_LAST_NAME);
                            if (object.has(Constants.FB_EMAIL))
                                email = object.getString(Constants.FB_EMAIL);
                            if (object.has(Constants.FB_BIRTH_DAY))
                                birthday = object.getString(Constants.FB_BIRTH_DAY);
                            if (object.has(Constants.FB_GENDER))
                                gender = object.getString(Constants.FB_GENDER);

                            navigateToHomeScreen();

                        } catch (Exception e) {
                            e.printStackTrace();
                            MyLogUtils.d(Constants.TAG, "facebook Exception:");
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,birthday,picture,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }*/

    /* public void printhashkey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.yesspree.app",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                MyLogUtils.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            MyLogUtils.d("Exception:", "PackageManager.NameNotFoundException");
        } catch (NoSuchAlgorithmException e) {
            MyLogUtils.d("Exception:", "NoSuchAlgorithmException");
        }

    }*/
}
