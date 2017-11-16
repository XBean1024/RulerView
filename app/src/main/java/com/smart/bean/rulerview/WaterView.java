package com.smart.bean.rulerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * auther   : bean
 * on       : 2017/11/15
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class WaterView extends BaseView implements View.OnClickListener {
    private ValueAnimator mValueAnimator;
    Path path = new Path();
    private int mOffset;

    public WaterView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(this);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Util.getColor(R.color.blue));
        //        canvas.drawRect(0, 0, mViewWidth, mViewHeight, mPaint);
        //        mPaint.setStyle(Paint.Style.STROKE);
        int waveShowNum = 4;//现实的波的个数
        int waveLen = mViewWidth / waveShowNum;//波长
        int waveLenHalf = waveLen / 2;//半波长


        int startPosX = -waveLen;//起始波的起始点

        int centerY = mViewHeight / 3;//半波长的三分之一；振动轴

        int firstStartControllerX = startPosX + waveLenHalf / 2;//第一个控制点x坐标
        int secondStartControllerX = firstStartControllerX + waveLenHalf;//第二个控制点的 x 坐标 ，由第一个平移半个波长

        int controllerY = waveLenHalf / 3;//半个波长的3分之一 ；为了波形的协调

        int offsetY = centerY > controllerY ? controllerY : centerY;//控制距离震动中心的长度

        int upperControllerY = centerY - offsetY;//上控制点
        int downControllerY = centerY + offsetY;//下控制点

        path.reset();
        path.moveTo(startPosX, centerY);//移动到 起始点

        int firstEndPosX = startPosX + waveLenHalf;
        int secondEndPosX = firstEndPosX + waveLenHalf;
        int realNum = waveShowNum + 2;
        for (int i = 0; i < realNum; i++) {
            path.quadTo(firstStartControllerX + i * waveLen + mOffset, upperControllerY, firstEndPosX + i * waveLen + mOffset, centerY);
            path.quadTo(secondStartControllerX + i * waveLen + mOffset, downControllerY, secondEndPosX + i * waveLen + mOffset, centerY);
        }
        path.lineTo(mViewWidth, mViewHeight);
        path.lineTo(0, mViewHeight);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator = ValueAnimator.ofInt(0, mViewWidth / 4);
        mValueAnimator.setDuration(800);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffset = (int) valueAnimator.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: " + mOffset);
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
