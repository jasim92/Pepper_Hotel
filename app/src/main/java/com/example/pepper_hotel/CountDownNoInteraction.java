package com.example.pepper_hotel;

import android.os.CountDownTimer;
import android.util.Log;

public class CountDownNoInteraction extends CountDownTimer {
    private String TAG = "NoInteraction";
    private MainActivity ma;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownNoInteraction(MainActivity mainActivity, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.ma = mainActivity;

    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        ma.showSplashScreenFragment();

    }
    public void reset(){
        Log.d(TAG,"Timer Reset");
        super.cancel();
        super.start();
    }
}
