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
    private int preMove = 0;
    public NotyAnimHandler(NotyFlowView view){
        this.frame_count = 30;
        mCount = 0;
        targetView = view;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        doScroll();
    }

    private boolean doScroll(){
        mCount++;
        if (mCount<=frame_count){
            float fraction = (float) mCount/frame_count;
            int scrollY = (int) (fraction * deltaY);
            int tem = scrollY;
            scrollY-=preMove;
            targetView.scrollBy(0,scrollY);
            preMove = tem;
            int delay_time = 5;

            sendEmptyMessageDelayed(0, delay_time);
            return false;
        }else{
            mCount = 0;
            return true;
        }
    }

}
