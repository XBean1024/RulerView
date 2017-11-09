package com.smart.bean.rulerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;



/**
 * auther   : bean
 * on       : 2017/11/9
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function : 绘制一个尺子
 */

public class RulerView extends View {
    private Paint mPaint;

    private int mRulerLength;
    private int mWindowWidth;
    private int mWindowHeight;
    private int mWindowWidthHalf;
    private int mWindowHeightHalf;

    private int mStartScrollX = 0;//手指按下的X位置
    private int mEndScrollX = 0;

    private int mRulerStartX = 0;//尺子的起始位置
    private int mGraduationStartX = 0;//刻度的起始位置
    private int mVelocity = 15;//滑动速度，值越大，滑动越慢
    private int mValue = 100;//刻度的起始值，100
    private int mDeltaX = 20;//刻度值的跨度

    public void setOnValueChangedListener(OnRulerValueChangedListener valueChanged) {
        mValueChanged = valueChanged;
    }

    private OnRulerValueChangedListener mValueChanged;
    public interface OnRulerValueChangedListener {
        void sendValue(String value);
    }
    public RulerView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        mPaint = new Paint();
        mGraduationStartX = mWindowWidth = Util.getWindowWidth();
        mRulerLength = mWindowWidth * 4;
        mWindowHeight = 600;
//        mWindowHeight = Util.getWindowHeight();
        mWindowWidthHalf = mWindowWidth / 2;
        mWindowHeightHalf = mWindowHeight / 2;
        this.scrollBy(mWindowWidthHalf, 0);//初始化的时候，向左平移半个屏幕
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * view的大小控制
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));

    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result=600;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 200;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制外框
        drawOuter(canvas);
        // 绘制刻度线
        drawGraduation(canvas);
        drawTraingle(canvas);

    }

    public static final String TAG = "sss";

    private void drawTraingle(Canvas canvas) {
        Path path = new Path();
        int traingleMiddleX = getScrollX() + mWindowWidthHalf;

        int leftX = traingleMiddleX - 30;//底边的左边X
        int rightX = traingleMiddleX + 30;//底边右X

        int traingleYTop = mWindowHeightHalf - 150;//底边Y
        int traingleYBottom = mWindowHeightHalf - 130;//顶角Y


        path.moveTo(leftX, traingleYTop);
        path.lineTo(rightX, traingleYTop);
        path.lineTo(traingleMiddleX, traingleYBottom);
        path.close();

        mPaint.setColor(Util.getColor(R.color.white));
        canvas.drawPath(path, mPaint);

        //绘制刻度值
        mPaint.setColor(Util.getColor(R.color.balck));

        int intValue = (getScrollX() - 540) / mDeltaX;//整数部分
        int floatValue = ((getScrollX() - 540) % 20) / 2;//小数部分
        Log.i(TAG, "floatValue: " + floatValue % 10);
        String result = "";//显示的值
        result = String.valueOf(mValue + intValue) + "." + floatValue;
//        if (floatValue > 8) {
//            result = String.valueOf(mValue + intValue + 1) + ".0";
//        } else {
//            result = String.valueOf(mValue + intValue) + "0.5";
//        }
        canvas.drawText("身高：" + result + "(cm)", traingleMiddleX - 30, traingleYTop - 50, mPaint);
        mValueChanged.sendValue(result);
    }


    private void drawGraduation(Canvas canvas) {
        mPaint.setColor(Util.getColor(R.color.balck));
        int startY = mWindowHeightHalf - 150;
        int endY = startY + 150;
        canvas.save();

        mValue = 100;//刻度值
        int num = mRulerLength / mDeltaX;
        mPaint.setStrokeWidth(2);
        int textPos = 50;
        for (int i = 0; i < num; i++) {
            int endYTemp = 0;
            if (i % 10 == 0) {
                endYTemp = endY;
                mPaint.setTextSize(50);
                mPaint.setColor(Util.getColor(R.color.balck));
                //绘制文字
                canvas.drawText(String.valueOf(mValue + i), mGraduationStartX - 30, endYTemp + textPos, mPaint);

            } else if (i % 5 == 0) {
                endYTemp = endY - 50;
            } else {
                endYTemp = endY - 100;
            }
            //绘制刻度线
            canvas.drawLine(mGraduationStartX, startY, mGraduationStartX, endYTemp, mPaint);

            canvas.translate(mDeltaX, 0);
        }
        canvas.restore();
    }

    private void drawOuter(Canvas canvas) {
        int left = mRulerStartX;
        int top = mWindowHeight / 2 - 150;
        int right = mRulerLength;
        int bottom = mWindowHeight / 2 + 150;

        mPaint.setColor(Util.getColor(R.color.gold));

        canvas.drawRect(left, top, right, bottom, mPaint);
    }


    private int mScrollLen;//滑动距离，正负代表滑动方向，正左滑

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartScrollX = (int) event.getX();
                Log.i(TAG, "mStartScrollX: " + mStartScrollX);
                break;
            case MotionEvent.ACTION_MOVE:
                mEndScrollX = (int) event.getX();
                mScrollLen = mStartScrollX - mEndScrollX;
                Log.i(TAG, "mEndScrollX: " + mEndScrollX);

                if (getScrollX() <= mWindowWidthHalf && mScrollLen <= 0) {
                    /*
                    * */
                    break;
                }
                if (getScrollX() >= mRulerLength - mWindowWidthHalf && mScrollLen >= 0) {
                    break;
                }
                scrollBy(mScrollLen / mVelocity, 0);
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "scrolledX: " + getScrollX());
                Log.i(TAG, "半个屏幕的尺寸: mWindowWidthHalf = " + mWindowWidthHalf);
                Log.i(TAG, "滑动的距离: mScrollLen = " + mScrollLen);
                break;

        }

        return true;
    }


}
