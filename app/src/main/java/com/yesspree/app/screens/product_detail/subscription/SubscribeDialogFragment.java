package com.yesspree.app.screens.product_detail.subscription;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.screens.addsubscription.AddSubscriptionActivity;
import com.yesspree.app.screens.home.adapter.HomeRecyclerAdapter;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubscribeDialogFragment extends DialogFragment implements IAdapterClickListener {


    @BindView(R.id.tv_header_quantity)
    TextView tvHeaderQuantity;
    @BindView(R.id.product_name)
    TextView tvProductName;
    @BindView(R.id.recycler_subscription_items)
    RecyclerView recyclerSubscriptionItems;
    Unbinder unbinder;


    /*variables*/
    ProductData productData;
    ArrayList<ProductData> productDataArrayList;
    Context context;
    FragmentActivity activity;
    HomeRecyclerAdapter adapter;
    OnFragmentInteractionListener fragmentInteractionListener;
    private String TAG = "SubscribeDialogFragment";

    public static SubscribeDialogFragment newInstance(ProductData productData) {
        SubscribeDialogFragment fragment = new SubscribeDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.PRODUCTS, productData);
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            productData = getArguments().getParcelable(Constants.PRODUCTS);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.partail_popup_product_subscribe_screen, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (productData != null)
            setUpdata();
        return v;
    }

    private void setUpdata() {

        if (Validation.isValidObject(productData.getSelSku()) && Validation.isValidString(productData.getName()))
            tvProductName.setText(productData.getName() + " - " + productData.getSelSku().getSize());


        productDataArrayList = new ArrayList<>();
        productDataArrayList.add(productData);
        adapter = new HomeRecyclerAdapter(context, R.layout.partail_popup_product_subscribe_list_item, this, Constants.SUBSCRIBE_DIALOG_FRAGMENT);
        recyclerSubscriptionItems.setLayoutManager(new LinearLayoutManager(context));
        recyclerSubscriptionItems.setAdapter(adapter);
        adapter.addList(productDataArrayList);
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (Validation.isValidObject(data) && data instanceof ProductData) {
            startAddSubscriptionScreen((ProductData) data);
        }
    }

    void startAddSubscriptionScreen(ProductData productData) {
        dismiss();
        Intent intent = new Intent(activity, AddSubscriptionActivity.class);
        intent.putExtra(Constants.SUBSCRIBE_DIALOG_FRAGMENT, productData);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 100% of the screen width
        window.setLayout((size.x * 1), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
        if (context instanceof OnFragmentInteractionListener) {
            fragmentInteractionListener = (OnFragmentInteractionListener) context;
            MyLogUtils.e(TAG, "onAttach");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
