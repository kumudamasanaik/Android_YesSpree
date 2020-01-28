package com.yesspree.app.screens.addsubscription;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.SubscrWeekDays;
import com.yesspree.app.utility.Validation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekdayAdapter extends RecyclerView.Adapter<WeekdayAdapter.ViewHolder> {
    ArrayList<SubscrWeekDays> weekDaysArrayList;
    Context context;
    IAdapterClickListener clickListener;

    public WeekdayAdapter(Context context, IAdapterClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        weekDaysArrayList = new ArrayList<>();

    }

    public void addList(ArrayList<SubscrWeekDays> weekDaysArrayList) {
        this.weekDaysArrayList.clear();
        this.weekDaysArrayList.addAll(weekDaysArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeekdayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_week_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekdayAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return (Validation.isValidList(weekDaysArrayList) ? weekDaysArrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_week_name)
        TextView tvWeekName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(int position) {
            SubscrWeekDays day = weekDaysArrayList.get(position);
            if (Validation.isValidObject(day) && Validation.isValidString(day.getDay())) {
                tvWeekName.setText(day.getDay());
                tvWeekName.setTextColor(day.isSelected() ? ResourcesCompat.getColor(context.getResources(), R.color.md_red_900, null) : ResourcesCompat.getColor(context.getResources(), R.color.person_prefix, null));
                itemView.setOnClickListener(v -> clickListener.onAdapterClick(day, getAdapterPosition(), itemView, Constants.SUBSCRIBE_DIALOG_FRAGMENT));
            }
        }
    }
}
