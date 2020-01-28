package com.yesspree.app.screens.addsubscription;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.yesspree.app.modelapi.SubscrWeekDays;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback{

    List<SubscrWeekDays> oldPersons;
    List<SubscrWeekDays> newPersons;

    public MyDiffCallback(List<SubscrWeekDays> newPersons, List<SubscrWeekDays> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).getDay() == newPersons.get(newItemPosition).getDay();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
