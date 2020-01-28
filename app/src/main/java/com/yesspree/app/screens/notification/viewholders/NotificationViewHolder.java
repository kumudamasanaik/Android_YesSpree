package com.yesspree.app.screens.notification.viewholders;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.screens.home.vieholder.MyViewHolder;
import com.yesspree.app.modelapi.Notification;
import com.yesspree.app.utility.ImageUtils;
import com.yesspree.app.utility.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FuGenX-14 on 10-05-2018.
 */

public class NotificationViewHolder extends MyViewHolder {

    @BindView(R.id.tv_header_name)
    TextView tv_header;
    @BindView(R.id.tv_query)
    TextView tv_tv_query;
    @BindView(R.id.iv_image)
    AppCompatImageView iv_image;
    private Notification notification;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Object item, Context context) {
        if (Validation.isValidObject(item) && item instanceof Notification) {
            this.notification = (Notification) item;
            if (Validation.isValidString(notification.getTitle()))
                tv_header.setText(notification.getTitle());

            if (Validation.isValidString(notification.getMessage()))
                tv_tv_query.setText(notification.getMessage());

            if (Validation.isValidString(notification.getImage()))
                ImageUtils.setImage(iv_image, notification.getImage());

        }

    }
}
