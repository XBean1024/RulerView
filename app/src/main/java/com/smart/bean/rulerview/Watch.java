package com.smart.bean.rulerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * auther   : bean
 * on       : 2017/11/14
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class Watch extends BaseView {
    private Paint mPaint;
    private int mMeasureWidth;
    private int mMeasureHeight;

    private int mCicleRadius;

    /*
    * 表盘中心
    * 同是也是 表针的旋转中心
    * */
    private int mCirlceCenterX;
    private int mCircleCenterY;
    private int mHand;

    public Watch(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    public Watch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureWidth = measureHeight(widthMeasureSpec);
        mMeasureHeight = measureHeight(heightMeasureSpec);

        mCicleRadius = (mMeasureHeight >= mMeasureWidth) ? mMeasureWidth / 2 - mMeasureWidth / 10 : mMeasureHeight / 2 - mMeasureHeight / 10;
        mCirlceCenterX = mMeasureWidth / 2;
        mCircleCenterY = mMeasureHeight / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWatch(canvas);
    }


    private void drawWatch(Canvas canvas) {
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(mCirlceCenterX, mCircleCenterY, mCicleRadius, mPaint);
        canvas.drawPoint(mCirlceCenterX, mCircleCenterY, mPaint);
        /*
        * 表盘上一共 60格
        * 每格代表6度，为1秒（短线）
        * 每30度为一个小时，（长线）
        * 当弧度能被30整除，则为一个小时
        * 当弧度能被6整除，但不能被30整除，则
        * */
        int value = 0;
        canvas.save();
        for (int i = 0; i < 360; i++) {
            if (i % 6 == 0) {
                canvas.rotate(6, mCirlceCenterX, mCircleCenterY);
            }
            if (i % 30 == 0) {
                //时针
                Log.i(TAG, "drawWatch: " + i);
                mPaint.setStrokeWidth(1);
                mHand = mMeasureHeight / 15;
                if (i == 0) {
                    value = 12;
                } else {
                    value = value + 1;
                }
                mPaint.setTextSize(20);
                canvas.drawText(value + "", mCirlceCenterX - 10, mMeasureHeight / 10 + mHand + 20, mPaint);
                if (value == 12) {
                    value = 0;
                }
            } else {
                mPaint.setStrokeWidth(2);
                mHand = mMeasureHeight / 30;
            }

            if (i % 6 == 0) {
                if (i % 30 == 0) {
                    mPaint.setStrokeWidth(5);
                }
                canvas.drawLine(mCirlceCenterX, mMeasureHeight / 10, mCirlceCenterX, mMeasureHeight / 10 + mHand, mPaint);
            }
        }
        canvas.restore();
    }
}
