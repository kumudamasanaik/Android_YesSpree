package com.yesspree.app.utility;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yesspree.app.R;
import com.yesspree.app.network.ApiConstants;


public class ImageUtils {

    public static void setImage(AppCompatImageView imageView, String imageUrl) {
        Picasso.get()
                .load(ApiConstants.IMAGE_BASE_URL + imageUrl)
                .placeholder(R.drawable.place_holder)
                .fit()
                .error(R.drawable.place_holder)
                .into(imageView);
    }

    public static void setImageWitDefPic(Context context, AppCompatImageView imageView, String imageUrl) {
        Picasso.get()
                .load(ApiConstants.IMAGE_BASE_URL + imageUrl)
                .placeholder(ResourcesCompat.getDrawable(context.getResources(), R.drawable.place_holder, null))
                .error(ResourcesCompat.getDrawable(context.getResources(), R.drawable.place_holder, null))
                .into(imageView);
    }

    public static void setImage(Context context, AppCompatImageView imageView, String imageUrl, int placeHolder, int errorImage) {
        Picasso.get()
                .load(ApiConstants.IMAGE_BASE_URL + imageUrl)
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(imageView);
    }

    public static void setImageWithUrl(Context context, AppCompatImageView imageView, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(imageView);
    }

    public static void setImageWithErrListner(Context context, ImageView imgview, final String url) {
        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e("ImageUtils", "url:" + url + ":" + exception.getMessage());
                    }
                })
                .build();
        picasso.load(ApiConstants.IMAGE_BASE_URL + url)
                .fit()
                .into(imgview);
    }

}
