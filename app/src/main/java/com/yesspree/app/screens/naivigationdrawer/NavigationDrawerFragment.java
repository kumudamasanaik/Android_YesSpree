package com.yesspree.app.screens.naivigationdrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.NavigationDrawerModel;
import com.yesspree.app.preferences.SharedPreferenceManger;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class NavigationDrawerFragment extends Fragment {

    //view
    View view;

    //header
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.nav_header_before_login)
    RelativeLayout navHeaderBeforeLogin;
    @BindView(R.id.tv_user_name)
    AppCompatTextView tvUserName;
    @BindView(R.id.iv_user_pic)
    AppCompatImageView ivUserPic;
    @BindView(R.id.nav_header_after_login)
    RelativeLayout navHeaderAfterLogin;
    @BindView(R.id.header_layout)
    RelativeLayout headerLayout;

    //nav recycler view
    @BindView(R.id.rv_drawer_item)
    RecyclerView rvDrawerItem;
    Unbinder unbinder;

    //variable
    ArrayList<NavigationDrawerModel> navList;
    private OnFragmentInteractionListener mListener;
    NavigationDrawerAdapter adapter;
    private Context context;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mUserLearnedDrawer;
    private String TAG = "NavigationDrawerFragment";
    private String customerName, customerPicUrl;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserLearnedDrawer = (Boolean) SharedPreferenceManger.getPrefVal(getActivity(), Constants.KEY_USER_LEARNED_DRAWER, false, SharedPreferenceManger.VALUE_TYPE.BOOLEAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setUpNavDrawer(int fragmentId, DrawerLayout drawerLayout, IAdapterClickListener clickListener) {
        if (adapter == null) {
            adapter = new NavigationDrawerAdapter(context, getList(), clickListener);
            rvDrawerItem.setLayoutManager(new LinearLayoutManager(context));
            rvDrawerItem.setAdapter(adapter);
            rvDrawerItem.setNestedScrollingEnabled(false);
            setHeader();
        }

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerOpened(drawerView);
                ActivityCompat.invalidateOptionsMenu(getActivity());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferenceManger.setPrefVal(getActivity(), Constants.KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer, SharedPreferenceManger.VALUE_TYPE.BOOLEAN);
                }
                ActivityCompat.invalidateOptionsMenu(getActivity());
            }
        };
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    public ArrayList<NavigationDrawerModel> getList() {
        navList = new ArrayList<>();
        String[] titles = getActivity().getResources().getStringArray(R.array.nav_list);
        TypedArray icons = getResources().obtainTypedArray(R.array.nav_icon);
        NavigationDrawerModel logout = null;
        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerModel current = new NavigationDrawerModel();
            current.id = i;
            current.title = titles[i % titles.length];
            if (i < icons.length())
                current.icon = ContextCompat.getDrawable(context, icons.getResourceId(i, -1));
            if (current.title.equalsIgnoreCase(getString(R.string.logout)))
                logout = current;
            navList.add(current);
        }
        if (logout != null && navList.size() > 0 && !CommonUtils.isLoggedIn(context)) {
            navList.remove(navList.indexOf(logout));
        }
        MyLogUtils.d(TAG, navList.toString());
        return navList;
    }

    public void setHeader() {
        if (CommonUtils.isLoggedIn(context)) {
            navHeaderAfterLogin.setVisibility(View.VISIBLE);
            navHeaderBeforeLogin.setVisibility(View.GONE);
            customerName = CommonUtils.getCustomerName(context);
            customerPicUrl = CommonUtils.getCustomerPic(context);
            if (Validation.isValidString(customerName))
                tvUserName.setText(customerName);
            if (Validation.isValidString(customerPicUrl))
                ImageUtils.setImageWithErrListner(context, ivUserPic, customerPicUrl);
        } else {
            navHeaderAfterLogin.setVisibility(View.GONE);
            navHeaderBeforeLogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_login)
    void navigateToLogin() {
        CommonUtils.doLogin(context, Constants.SOURCE_DASH_BOARD);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
