package com.yesspree.app.screens.filters.refined_fragment;

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
import com.yesspree.app.modelapi.Refine;
import com.yesspree.app.productlist.ProductListActivity;
import com.yesspree.app.screens.filters.FilterActivity;
import com.yesspree.app.screens.filters.FilterValueAdapter;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RefinedFragment extends Fragment implements IRefinedView, IAdapterClickListener {


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

    /*variables*/
    private String TAG = "RefinedFragment";
    private HomeRecyclerAdapter mFilterHeaderAdapter;
    private FilterValueAdapter mFilterValuesAdapter;
    private Context context;
    ArrayList<Refine> refineArrayList;
    ArrayList<FilterValue> refinevaluesArrayList;
    private FragmentActivity activity;
    private String filtervalue;
    private String filterType;
    ProductListInput input;


    public RefinedFragment() {
    }


    public static RefinedFragment newInstance() {
        RefinedFragment fragment = new RefinedFragment();
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
        View view = inflater.inflate(R.layout.fragment_refined_screen, container, false);
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
    public void init() {
        setRecyclerHeader();
        setupRecyclerValues();
        setData();
    }

    private void setRecyclerHeader() {
        mFilterHeaderAdapter = new HomeRecyclerAdapter(context, R.layout.filter_refined_adapter_headers_list_item, this,"");
        filterHeadersRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        filterHeadersRecyclerview.addItemDecoration(new InsetDecoration(context));
        filterHeadersRecyclerview.setAdapter(mFilterHeaderAdapter);
        filterHeadersRecyclerview.setNestedScrollingEnabled(false);
    }


    private void setupRecyclerValues() {
        mFilterValuesAdapter = new FilterValueAdapter(context, Constants.REFINE, this);
        filterValuesRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        filterValuesRecyclerview.addItemDecoration(new InsetDecoration(context));
        filterValuesRecyclerview.setAdapter(mFilterValuesAdapter);
        filterValuesRecyclerview.setNestedScrollingEnabled(false);
    }

    private void setData() {
        if (context != null && activity != null && activity instanceof FilterActivity) {
            this.refineArrayList = ((FilterActivity) activity).refineArrayList;
            if (Validation.isValidList(refineArrayList)) {
                refinevaluesArrayList = new ArrayList<>();
                refineArrayList.get(0).setSelected(true);
                filterType = refineArrayList.get(0).getName();
                refinevaluesArrayList.addAll(refineArrayList.get(0).getFilterValueArrayList());

                mFilterValuesAdapter.addList(refinevaluesArrayList);
                mFilterHeaderAdapter.addList(refineArrayList);
            } else
                showMsg(getString(R.string.no_data_found));
        }
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && type.equals(Constants.REFINE)) {
            if (data instanceof Refine) {
                for (Refine refine : refineArrayList)
                    refine.setSelected(false);
                ((Refine) data).setSelected(true);
                mFilterHeaderAdapter.notifyDataSetChanged();
                filterType = ((Refine) data).getName();
                mFilterValuesAdapter.addList(((Refine) data).getFilterValueArrayList());
            }
            if (data instanceof FilterValue && Validation.isValidString(filterType)) {

            }
        }
    }

    @OnClick(R.id.btn_filter)
    public void clickDone() {
        if (Validation.isValidObject(mFilterValuesAdapter) && Validation.isValidList(mFilterValuesAdapter.itemList)) {
            for (FilterValue filterValue : mFilterValuesAdapter.itemList) {
                if (filterValue.isChecked()) {
                    startProductListScreen(mFilterValuesAdapter.itemList);
                    return;
                }
            }
            showMsg(getString(R.string.err_nothing_is_selected, getString(R.string.refine)));
        } else
            showMsg(getString(R.string.no_data_found));
    }

    void startProductListScreen(ArrayList<FilterValue> itemList) {
        if (Validation.isValidString(filterType)) {
            StringBuilder filterValBuilder = new StringBuilder();
            for (FilterValue value : itemList) {
                if (value.isChecked()) {
                    if (filterType.equals(Constants.OFFER))
                        filterValBuilder.append(value.getValue() + ",");
                    else
                        filterValBuilder.append(value.getName() + ",");
                }
            }
            filtervalue = filterValBuilder.substring(0, filterValBuilder.length() - 1);
            if (Validation.isValidString(filtervalue)) {
                input = new ProductListInput();
                if (filterType.equals(Constants.PRICE))
                    input.setPrice(filtervalue);
                if (filterType.equals(Constants.OFFER))
                    input.setOffer(filtervalue);
                if (filterType.equals(Constants.BRAND))
                    input.setBrand(filtervalue);
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

    @OnClick(R.id.btn_clear)
    public void clickClear() {
        if (Validation.isValidObject(mFilterValuesAdapter) && Validation.isValidList(mFilterValuesAdapter.itemList)) {
            for (FilterValue filterValue : mFilterValuesAdapter.itemList) {
                filterValue.setChecked(false);
            }
            mFilterValuesAdapter.notifyDataSetChanged();
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
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
