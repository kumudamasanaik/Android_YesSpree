package com.yesspree.app.customviews;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class HideShowNestedScrollListener implements NestedScrollView.OnScrollChangeListener {
    private static final int HIDE_THRESHOLD = 10;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;


    public void onScrolled2(RecyclerView recyclerView, int dx, int dy) {
        //super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (pastVisibleItems + visibleItemCount >= totalItemCount) {
            //End of list
            if (!controlsVisible) {
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }
        } else if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();

    public abstract void endScroll();

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Log.d("ok", "onScrolled " + scrollY);


        View view = v.getChildAt(v.getChildCount() - 1);

        // if diff is zero, then the bottom has been reached
        if ((view.getBottom() - (v.getHeight() + v.getScrollY())) == 0) {
            if (!controlsVisible) {
                endScroll();
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
                scrolledDistance += -oldScrollY;
            }
        } else if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && scrollY > oldScrollY))
            scrolledDistance += oldScrollY;
        else if (!controlsVisible && scrollY < oldScrollY)
            scrolledDistance += -oldScrollY;
        //if ((controlsVisible && scrollY > oldScrollY) || (!controlsVisible && scrollY < oldScrollY)) {
        //    scrolledDistance += oldScrollY;
        //}
    }
}