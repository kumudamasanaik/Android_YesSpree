package com.yesspree.app.screens.address_selection.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.AddressData;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;

/**
 * Created by FuGenX-14 on 14-05-2018.
 */

public class AddressSelectionViewHolder extends MyViewHolder {

    IAdapterClickListener clickListener;
    @BindView(R.id.tv_name)
    TextView addressName;
    @BindView(R.id.tv_addressType)
    TextView tv_addressType;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_selected)
    TextView tv_selected;

    @BindView(R.id.img_edit)
    AppCompatImageView img_edit;

    @BindView(R.id.img_delete)
    AppCompatImageView img_delete;

    @BindView(R.id.tv_Addressname)
    TextView tv_Address;


    AddressData addressData;


    public AddressSelectionViewHolder(View itemView, IAdapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;

    }

    @Override
    public void bind(Object item, Context context) {

        if (Validation.isValidObject(item) && item instanceof AddressData) {
            this.addressData = (AddressData) item;
            if (Validation.isValidString(addressData.getName())) {
                addressName.setText(addressData.getName());
            }
            if (Validation.isValidString(addressData.getAddress_type()))
                tv_addressType.setText(addressData.getAddress_type());

            if (Validation.isValidString(addressData.getCity()))
                tv_city.setText(addressData.getCity());

            if (Validation.isValidString(addressData.getAddress1()))
                tv_Address.setText(addressData.getAddress1());


            tv_selected.setVisibility(addressData.getSelected()==1?View.VISIBLE:View.GONE);






            img_delete.setOnClickListener(v -> {
                if (Validation.isValidObject(clickListener))
                    clickListener.onAdapterClick(item, getAdapterPosition(), null, Constants.DELETE);
            });

            img_edit.setOnClickListener(v -> {

                if (Validation.isValidObject(clickListener))
                    clickListener.onAdapterClick(item, getAdapterPosition(), null, Constants.EDIT);
            });


            itemView.setOnClickListener(v -> {
                if (Validation.isValidObject(clickListener)) {
                    if (((AddressData) item).getSelected()==0)
                        clickListener.onAdapterClick(item, getAdapterPosition(), null, Constants.LIST_ITEM);
                }
            });


        }
    }
}




