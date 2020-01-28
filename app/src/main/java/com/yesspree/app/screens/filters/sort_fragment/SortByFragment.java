package com.yesspree.app.screens.filters.sort_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.customviews.InsetDecoration;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.FilterValue;
import com.yesspree.app.modelapi.ProductListInput;
import com.yesspree.app.productlist.ProductListActivity;
import com.yesspree.app.screens.filters.FilterActivity;
import com.yesspree.app.screens.filters.FilterValueAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SortByFragment extends Fragment implements ISortView, IAdapterClickListener {
    private String TAG = "SortByFragment";


    @BindView(R.id.tv_availible_products)
    TextView tvAvailibleProducts;
    @BindView(R.id.filter_headers_recyclerview)
    RecyclerView filterHeadersRecyclerview;
    @BindView(R.id.search_view)
    AppCompatEditText searchView;
    @BindView(R.id.filter_values_recyclerview)
    RecyclerView filterValuesRecyclerview;
    @BindView(R.id.btn_filter)
    TextView btnFilter_Done;
    @BindView(R.id.btn_clear)
    TextView btnClearAll;
    Unbinder unbinder;


    FilterValueAdapter mFilterValuesAdapter;
    Context context;
    private FragmentActivity activity;
    public ArrayList<FilterValue> sortArrayList;
    private String filterType;
    private String filtervalue;
    ProductListInput input;


    public SortByFragment() {
        // Required empty public constructor
    }


    public static SortByFragment newInstance() {
        SortByFragment fragment = new SortByFragment();
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
        View view = inflater.inflate(R.layout.fragment_sort_by_screen, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void init() {
        filterHeadersRecyclerview.setVisibility(View.GONE);
        btnClearAll.setVisibility(View.GONE);
        setupRecyclerValues();
        setData();
    }

    private void setData() {
        if (context != null && activity != null && activity instanceof FilterActivity) {
            this.sortArrayList = ((FilterActivity) activity).sortArrayList;
            if (Validation.isValidList(sortArrayList)) {
                filterType = Constants.SORT;
                mFilterValuesAdapter.addList(sortArrayList);
            } else
                showMsg(getString(R.string.no_data_found));
        }
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && data instanceof FilterValue && type.equals(Constants.SORT)) {
            for (FilterValue filterValue : sortArrayList) {
                if (data.hashCode() != filterValue.hashCode())
                    filterValue.setChecked(false);
            }
            mFilterValuesAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btn_filter)
    public void clickDone() {
        if (Validation.isValidObject(mFilterValuesAdapter) && Validation.isValidList(mFilterValuesAdapter.itemList)) {
            for (FilterValue filterValues : mFilterValuesAdapter.itemList) {
                if (filterValues.isChecked()) {
                    filtervalue = filterValues.getName();
                    startProductListScreen();
                    return;
                }
            }
            showMsg(getString(R.string.err_nothing_is_selected, getString(R.string.sort)));
        } else
            showMsg(getString(R.string.no_data_found));
    }

    void startProductListScreen() {
        if (Validation.isValidString(filterType)) {
            if (Validation.isValidString(filtervalue)) {
                input = new ProductListInput();
                input.setSort(filtervalue);
                input.set_id(CommonUtils.getCustomerId(context));
                input.set_session(CommonUtils.getSession(context));
                input.setWh_pincode(CommonUtils.getPincode(context));

                Intent intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra(Constants.SOURCE, Constants.SOURCE_FILTER);
                intent.putExtra(Constants.FILTER_OBJ, input);
                startActivity(intent);
            }
        }
    }


    @Override
    public void showMsg(String msg) {
        CommonUtils.showToastMsg(context, (Validation.isValidObject(msg) && Validation.isValidString(msg) ? msg : getString(R.string.error_something_wrong)), 0);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /*private void setRecyclerHeader() {
        mFilterHeaderAdapter = new HomeRecyclerAdapter(context, R.layout.filter_refined_adapter_headers_list_item);
        filterHeadersRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        filterHeadersRecyclerview.addItemDecoration(new InsetDecoration(context));
        filterHeadersRecyclerview.setAdapter(mFilterHeaderAdapter);
    }*/


    private void setupRecyclerValues() {
        mFilterValuesAdapter = new FilterValueAdapter(context, Constants.SORT, this);
        filterValuesRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        filterValuesRecyclerview.addItemDecoration(new InsetDecoration(context));
        filterValuesRecyclerview.setAdapter(mFilterValuesAdapter);
    }
}
