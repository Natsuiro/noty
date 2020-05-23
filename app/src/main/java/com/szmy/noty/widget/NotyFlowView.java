package com.szmy.noty.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

import com.szmy.noty.adapter.NotyAdapter;
import com.szmy.noty.handler.NotyAnimHandler;


public class NotyFlowView extends ViewGroup {

    private int leftTop;
    private int rightTop;
    private int childWidth;
    private OnItemClickListener mOnClickListener = null;
    private OnItemLongTouchListener mOnLongTouchListener = null;

    public NotyFlowView(Context context) {
        super(context);
    }

    public NotyFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotyFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NotyFlowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private View convertView = null;
    public int getChildWith(){
        return childWidth;
    }
    //测量孩子布局在本viewGroup的位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();//拿到子view数量
        int measuredWidth = getMeasuredWidth();
        ///中线的位置
        int middle = measuredWidth / 2;
        int paddingStart = 15;

        leftTop = 15;
        rightTop = 15;
        childWidth = middle - 30;
        int paddingMiddle = 30;
        int paddingHeight = 30;
        //遍历每一个子view进行定位
        for (int i = 0; i < childCount; i++) {
            int leftHeight, rightHeight;
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                //左低右高画左边
                if (leftTop <= rightTop) {
                    leftHeight = Math.min(child.getMeasuredHeight(), 800);
                    setChildFrame(child,  paddingStart, leftTop, childWidth, leftHeight);
                    leftTop += leftHeight + paddingHeight;
                } else {
                    rightHeight = Math.min(child.getMeasuredHeight(), 800);
                    setChildFrame(child, middle+15, rightTop, childWidth, rightHeight);
                    rightTop += rightHeight + paddingHeight;
                }
            }
        }


    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }


    private LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //ViewGroup中提供的方法,测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    private int getFlowBottom() {
        return Math.max(leftTop, rightTop);
    }

    //触摸滑动事件
    boolean isScrolling = false;
    int mLastMotionY;
    int extractMove = 300;
    float yVelocity = 0;
    int maxDelta = 0;
    NotyAnimHandler animHandler = null;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = (int) ev.getY();
                return false;
            case MotionEvent.ACTION_UP:
                return false;
            default:
                return true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        animHandler = new NotyAnimHandler(this);
        VelocityTracker tracker = VelocityTracker.obtain();
        tracker.addMovement(event);
        int flowBottom = getFlowBottom();
        maxDelta = flowBottom - getMeasuredHeight();
        if (maxDelta<0) maxDelta = 0;
        System.out.println("maxDelta:" + maxDelta);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mLastMotionY = (int) event.getY();
                if (getChildCount() == 0) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getY();
                System.out.println("scrollY:" + getScrollY());
                int deltaY = mLastMotionY - moveY;///<0:下滑反之:上滑
                //下滑 至超出extractMove的距离
                if (getScrollY() + deltaY < -extractMove) {
                    deltaY = -extractMove - getScrollY();
                }//上滑
                else if (getScrollY() + deltaY > maxDelta + extractMove) {
                    deltaY = maxDelta + extractMove - getScrollY();
                }
                scrollBy(0, deltaY);
                mLastMotionY = moveY;
                tracker.computeCurrentVelocity(1000);
                yVelocity = tracker.getYVelocity();//yVelocity / 500ms
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY() > maxDelta) {
                    animHandler.setDeltaY(maxDelta  - getScrollY());
                    animHandler.sendEmptyMessage(0);
                } else if (getScrollY() < 0) {
                    animHandler.setDeltaY(-getScrollY());
                    animHandler.sendEmptyMessage(0);
                }
                tracker.clear();
                tracker.recycle();
                break;
        }
        performClick();
        return true;
    }



    public interface OnItemClickListener{
        void onClick(int itemId,View item);
    }
    public interface OnItemLongTouchListener{
        boolean onLongTouch(int itemId,View item);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnClickListener = listener;
    }
    public void setOnItemLongTouchListener(OnItemLongTouchListener listener){
        this.mOnLongTouchListener = listener;
    }
    NotyAdapter adapter;
    public void setAdapter(NotyAdapter adapter) {
        if (this.adapter==null){
            this.adapter = adapter;
            adapter.setTargetView(this);
        }
        initView();
    }
    private void initView() {
        removeAllViews();
        for (int i = adapter.getCount()-1 ;i>=0;i--){
            final View view = adapter.getView(i,convertView,this);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("onClick","itemClick");
                    if (mOnClickListener !=null){
                        mOnClickListener.onClick(finalI,view);
                    }
                }
            });

            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnLongTouchListener.onLongTouch(finalI,view);
                }
            });
            addView(view);
        }
    }

}
