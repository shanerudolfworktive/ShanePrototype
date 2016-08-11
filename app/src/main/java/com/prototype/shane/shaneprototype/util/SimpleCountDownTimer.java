package com.prototype.shane.shaneprototype.util;

import android.os.CountDownTimer;

/**
 * Created by shane on 7/9/16.
 */
public abstract class SimpleCountDownTimer extends CountDownTimer {


    /**
     * @param milisecond The number of millis in the future from the call
     */
    public SimpleCountDownTimer(long milisecond) {
        super(milisecond, 1000);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }


}

