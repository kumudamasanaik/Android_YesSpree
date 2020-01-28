package com.yesspree.app.utility;

import android.os.Handler;

/**
 * Created by FuGenX-14 on 01-08-2018.
 */

public class TimerTask extends java.util.TimerTask {
    private Handler handler;
    private Runnable Update;

    public TimerTask(Handler handler, Runnable update) {
        this.handler = handler;
        Update = update;
    }

    @Override
    public void run() {
        handler.post(Update);
    }
}
