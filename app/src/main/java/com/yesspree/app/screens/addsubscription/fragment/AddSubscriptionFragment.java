package com.yesspree.app.screens.addsubscription.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.interfaces.ISelectedDateListener;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.AddSubcrInput;
import com.yesspree.app.modelapi.SubscrWeekDays;
import com.yesspree.app.screens.addsubscription.AddSubscriptionActivity;
import com.yesspree.app.screens.addsubscription.WeekdayAdapter;
import com.yesspree.app.screens.subscriptionconfirmation.SubScriptionConfirmedFragment;
import com.yesspree.app.utility.CommonUtils;
import com.yesspree.app.utility.MyLogUtils;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddSubscriptionFragment extends Fragment implements IAdapterClickListener, ISelectedDateListener {
    @BindView(R.id.rv_weeklist)
    RecyclerView recyclerView;
    @BindView(R.id.tv_date)
    TextView tvSelDate;
    Unbinder unbinder;

    /*variables*/
    Context context;
    AddSubscriptionActivity activity;
    private OnFragmentInteractionListener mListener;
    String source = "";
    ArrayList<SubscrWeekDays> weekDaysArrayList;
    WeekdayAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private String selSubscrtype = "";
    private String TAG = "AddSubscriptionFragment";
    private Calendar minDate;
    private Calendar maxDate;
    boolean isAtleastOneDaySel;
    StringBuilder selDays;
    public AddSubcrInput subScrinput;
    String subscrType;


    public AddSubscriptionFragment() {

    }

    public static AddSubscriptionFragment newInstance() {
        AddSubscriptionFragment fragment = new AddSubscriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_subscribe, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpUi();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (getActivity() instanceof AddSubscriptionActivity)
            this.activity = (AddSubscriptionActivity) getActivity();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setUpUi() {
        if (!Validation.isValidList(getWeekDays()))
            return;
        manipulateCalenderRange();
        subScrinput = activity.subScrinput;
        adapter = new WeekdayAdapter(context, this);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.addList(getWeekDays());
    }

    private void manipulateCalenderRange() {
        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();

        minDate.add(Calendar.DATE, 2);

        maxDate.add(Calendar.DATE, 30);
        maxDate.set(Calendar.HOUR_OF_DAY, 23);
        maxDate.set(Calendar.MINUTE, 59);
        maxDate.set(Calendar.SECOND, 59);

        tvSelDate.setText(String.format("%1$ta, %1$td %1$tb  %1$tY", minDate));
    }

    public ArrayList<SubscrWeekDays> getWeekDays() {
        weekDaysArrayList = new ArrayList<>();
        String[] days = getActivity().getResources().getStringArray(R.array.week_days);
        for (int i = 0; i < days.length; i++)
            weekDaysArrayList.add(new SubscrWeekDays(days[i], false));
        return weekDaysArrayList;
    }

    @Override
    public void onAdapterClick(Object data, int pos, View view, String type) {
        if (data != null && data instanceof SubscrWeekDays) {
            SubscrWeekDays day = (SubscrWeekDays) data;
            day.setSelected(!day.isSelected());
            adapter.notifyItemChanged(pos);
            subScrinput.setSubscription_type(getString(R.string.subscr_custom_sel));
        }
    }

    @OnClick({R.id.rdbt_everyday, R.id.rdbt_alternate_days, R.id.rdbt_once_week})
    public void onClickRadioButton(View view) {
        subscrType = ((AppCompatRadioButton) view).getText().toString();
        for (int i = 0; i < weekDaysArrayList.size(); i++) {
            if (weekDaysArrayList.get(i).isSelected()) {
                weekDaysArrayList.get(i).setSelected(false);
                adapter.notifyItemChanged(i);
            }
        }
        selSubscrtype = ((AppCompatRadioButton) view).getText().toString();
        MyLogUtils.d(TAG, selSubscrtype);
        switch (view.getId()) {
            case R.id.rdbt_everyday:
                for (SubscrWeekDays days : weekDaysArrayList)
                    days.setSelected(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rdbt_alternate_days:
                for (int i = 0; i < weekDaysArrayList.size(); i++) {
                    if (i % 2 != 0) {
                        weekDaysArrayList.get(i).setSelected(true);
                        adapter.notifyItemChanged(i);
                    }
                }
                break;
            case R.id.rdbt_once_week:
                weekDaysArrayList.get(0).setSelected(true);
                adapter.notifyItemChanged(0);
                break;
        }
    }

    @OnClick({R.id.tv_start_date, R.id.tv_date})
    public void onclickStartDate() {
        CommonUtils.showCalenderPickerForReports(context, this, minDate.getTimeInMillis(), maxDate.getTimeInMillis());
    }


    @Override
    public void setSelectedDate(String date) {
        if (Validation.isValidString(date))
            tvSelDate.setText(date);
    }

    @OnClick(R.id.tv_confirm)
    public void confirmSubscribe(View view) {

        selDays = new StringBuilder();
        if (Validation.isValidList(weekDaysArrayList)) {
            for (SubscrWeekDays days : weekDaysArrayList)
                if (days.isSelected()) {
                    selDays.append(days.getDay() + ",");
                }
        }
        if (selDays.length() > 0) {
            subScrinput.setDay(selDays.substring(0, selDays.length() - 1));
            if (Validation.isValidString(subscrType))
                subScrinput.setSubscription_type(subscrType);
            if (Validation.isValidObject(activity.productData)) {
                subScrinput.setId_product(activity.productData.getProductId());
                subScrinput.setId_sku(activity.productData.getSelSku().get_id());
                subScrinput.setQuantity(String.valueOf(activity.productData.getSelSku().getMycart()));
            }
            subScrinput.setStart_date(tvSelDate.getText().toString());

            addFragment();
        } else
            CommonUtils.showToastMsg(context, getString(R.string.pls_sel_day), 0);
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


    private void addFragment() {
        subScrinput.setPay_type(getString(R.string.cod));
        subScrinput.setIs_doorbellring("0");
        subScrinput.setIs_alternate("1");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, SubScriptionConfirmedFragment.newInstance());
        transaction.addToBackStack(SubScriptionConfirmedFragment.class.getName());
        transaction.commit();
    }
}
