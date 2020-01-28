package com.yesspree.app.screens.sub_sub_category.category_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.MultiStateView;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.Category;
import com.yesspree.app.modelapi.MultipleCatRespModel;
import com.yesspree.app.screens.basescreen.DashBoardActivity;
import com.yesspree.app.screens.productlist.ProductMainMainListFragment;
import com.yesspree.app.screens.sub_sub_category.adapter.MultipleChildCatAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MultipleCatFragment extends Fragment implements IMultipleCatFragmentView, IAdapterClickListener {
    Context mContext;
    private String TAG = "MultipleCatFragment";
    View view;
    Unbinder unbinder;
    MultipleChildCatAdapter mSubSubCatAdapter;
    MultipleCatFragmentPresenter multiCatFragmentPresenter;
    List<Category> mCatgeoryList;
    String mCatId = "";
    MultipleCatRespModel.ChildCategory multipleCategoryResponsModel;
    FragmentActivity mActivity;

    @BindView(R.id.rv_subcatagory)
    RecyclerView rv_subCatgory;
    @BindView(R.id.multistateview)
    MultiStateView multiStateView;
    @BindView(R.id.layout_emptyView)
    View layout_emptyView;

    private OnFragmentInteractionListener mListener;


    public MultipleCatFragment() {
        // Required empty public constructor
    }

    public static MultipleCatFragment newInstance() {
        MultipleCatFragment fragment = new MultipleCatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        view = inflater.inflate(R.layout.fragment_multiplecat, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Validation.isValidObject(getArguments())) {
            // categoryArrayList = getArguments().getParcelableArrayList(Constants.EXTRAS_CATEGORY_LIST);
            mCatId = getArguments().getString(Constants.EXTRAS_MULTI_FRAG_CATEGORY_ID);
        }

        init();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = getActivity();
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


    @Override
    public void setCategoryApiRes(MultipleCatRespModel multipleCatRespModel) {

        if (Validation.isValidStatus(multipleCatRespModel)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (Validation.isValidObject(multipleCatRespModel.getChildcat())) {
                multipleCategoryResponsModel = multipleCatRespModel.getChildcat();
                setData();
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR);
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR);


    }


    @Override
    public void showViewState(int state) {
        if (multiStateView != null)
            multiStateView.setViewState(state);

    }

    @OnClick(R.id.empty_button)
    void clickEmptyView() {
        callMultiCategoryApi(mCatId);
    }

    @OnClick(R.id.error_button)
    void clickErrorView() {
        callMultiCategoryApi(mCatId);
    }


    @Override
    public void init() {
        multiCatFragmentPresenter = new MultipleCatFragmentPresenter(this);
        setupChildCategoryData();
    }


    public void callMultiCategoryApi(String mCatId) {
        if (Validation.isValidObject(multiCatFragmentPresenter)) {
            mCatId = mCatId;
            if (Validation.isValidObject(layout_emptyView))
                layout_emptyView.setVisibility(View.GONE);

            multiCatFragmentPresenter.callMultipleCatData(mCatId);
        }
    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoader(String msg) {
        CommonUtils.showLoading(mContext, (msg != null) ? msg : getString(R.string.please_wait), false);

    }

    @Override
    public void hideLoader() {
        CommonUtils.hideLoading();
    }


    @Override
    public void onAdapterClick(Object data, int pos, View view,String opType) {
        if (Validation.isValidObject(data) && data instanceof Category) {
            Category category = ((Category) data);
            if (getActivity() != null && getActivity() instanceof DashBoardActivity ) {

                Bundle bundle = new Bundle();
                bundle.putString(Constants.CATEGOPRY_ID, ((Category) data).get_id());
                bundle.putString(Constants.EXTRAS_CATEGORY_NAME, ((Category) data).getName());
                ProductMainMainListFragment lastLevCatFragment = ProductMainMainListFragment.newInstance();
                lastLevCatFragment.setArguments(bundle);

                //((DashBoardActivity) mActivity).bottomNavigationView.setSelectedItemId(R.id.action_category);
                CommonUtils.addFragment(lastLevCatFragment, mActivity);



            }
        }

        if (Validation.isValidList(mCatgeoryList) && mCatgeoryList.size() > pos)
            rv_subCatgory.smoothScrollToPosition(pos);

    }


    private void setData() {


        if (Validation.isValidList(multipleCategoryResponsModel.getCategory())) {
            if (Validation.isValidObject(layout_emptyView))
                layout_emptyView.setVisibility(View.GONE);

            mSubSubCatAdapter.addList(multipleCategoryResponsModel.getCategory());
        } else {
            if (Validation.isValidObject(layout_emptyView))
                layout_emptyView.setVisibility(View.VISIBLE);

        }


    }

    private void setupChildCategoryData() {

        mCatgeoryList = new ArrayList<>();
        rv_subCatgory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_subCatgory.hasFixedSize();
        mSubSubCatAdapter = new MultipleChildCatAdapter(mContext, mCatgeoryList, this);
        rv_subCatgory.setAdapter(mSubSubCatAdapter);

    }


}
