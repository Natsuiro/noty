package com.szmy.noty.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.szmy.noty.widget.NotyFlowView;

public class NotyAnimHandler extends Handler {

    private int mCount;
    private int frame_count;
    private int deltaY;
    private NotyFlowView targetView;
    public NotyAnimHandler(NotyFlowView view,int deltaY){
        this.frame_count = 60;
        mCount = 0;
        this.deltaY = deltaY;
        targetView = view;
    }


    @Override
    public void handleMessage(@NonNull Message msg) {
        mCount++;
        if (mCount<=frame_count){
            float fraction = (float) mCount/frame_count;
            int scrollY = (int) (fraction * deltaY);
            targetView.scrollTo(0,targetView.getScrollY()+scrollY);
            int delay_time = 20;
            sendEmptyMessageDelayed(0, delay_time);
        }
    }
}
