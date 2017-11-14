package com.smart.bean.rulerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


/**
 * auther   : bean
 * on       : 2017/11/9
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function : 绘制一个尺子
 */

public class RulerView extends BaseView {
    private Paint mPaint;

    private int mViewWidth;//布局中设置的宽度
    private int mViewHeight;//布局设置的高度
    private int mRulerWidth;//尺子的宽度

    private int mInitScrollX;

    private int mFingerDownX = 0;//手指按下的X位置
    private int mFingerUpX = 0;

    private int mPaddingTop = 0;//尺子的起始位置
    private int mPaddingBottom = 0;//尺子的起始位置
    private int mGraduationStartX = 0;//刻度的起始位置
    private int mVelocity = 15;//滑动速度，值越大，滑动越慢
    private int mRulerStartValue = 100;//刻度的起始值，100
    private int mRulerStartPosX;//绘制的起始位置
    private int mDeltaX = 20;//刻度值的跨度
    private int mColorId;
    private int mTraingleMiddleX;

    public void setOnValueChangedListener(OnRulerValueChangedListener valueChanged) {
        mValueChanged = valueChanged;
    }

    private OnRulerValueChangedListener mValueChanged;

    public interface OnRulerValueChangedListener {
        void sendValue(String value);
    }

    public RulerView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ruler_view_attrs);
        mColorId = array.getColor(R.styleable.ruler_view_attrs_graduation_color, 0);
        mInitScrollX = array.getInt(R.styleable.ruler_view_attrs_offset_left, 200);
        this.scrollBy(mInitScrollX, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    /**
     * view的大小控制
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mViewWidth = measureWidth(widthMeasureSpec);//得到view的宽度
        mViewHeight = measureHeight(heightMeasureSpec);//view 的高度
        mRulerWidth = mViewWidth * 4 - mViewWidth / 2;

        mTraingleMiddleX = mViewWidth / 2;//绘制三角指示器的位置在,位于view 的中部
        Log.i(TAG, "onMeasure: " + mInitScrollX);
        mGraduationStartX = mInitScrollX;//开始绘制刻度
        setMeasuredDimension(mViewWidth, mViewHeight);
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
        int traingleMiddleX = getScrollX() + mTraingleMiddleX;
        Log.i(TAG, "getWidth: " + getWidth());
        int leftX = traingleMiddleX - 30;//底边的左边X
        int rightX = traingleMiddleX + 30;//底边右X

        int traingleYTop = mPaddingTop;//底边Y
        int traingleYBottom = mPaddingTop + mViewHeight / 6;//顶角Y


        path.moveTo(leftX, traingleYTop);
        path.lineTo(rightX, traingleYTop);
        path.lineTo(traingleMiddleX, traingleYBottom);
        path.close();

        mPaint.setColor(getResources().getColor(R.color.white));
        canvas.drawPath(path, mPaint);

        //绘制刻度值
        mPaint.setColor(getResources().getColor(R.color.balck));


        int intValue = (getScrollX() + mViewWidth / 2 - mInitScrollX) / mDeltaX;//整数部分
        int floatValue = ((getScrollX() + mViewWidth / 2 - mInitScrollX) % 20) / 2;//小数部分
        Log.i(TAG, "floatValue: " + floatValue % 10);
        Log.i(TAG, "floatValue: " + floatValue % 10);
        String result = "";//显示的值
        result = String.valueOf(mRulerStartValue + intValue) + "." + floatValue;
//        if (floatValue > 8) {
//            result = String.valueOf(mRulerStartValue + intValue + 1) + ".0";
//        } else {
//            result = String.valueOf(mRulerStartValue + intValue) + "0.5";
//        }
        mPaint.setTextSize(30);
        canvas.drawText("身高：" + result, traingleMiddleX - 30, mPaddingTop + mViewHeight - 30, mPaint);
        mValueChanged.sendValue(result);
    }


    private void drawGraduation(Canvas canvas) {
        canvas.save();
        mRulerStartValue = 100;//刻度值
        int num = (mRulerWidth - mViewWidth * 3 / 4) / mDeltaX;
        mPaint.setStrokeWidth(2);
        int textPos = 50;
        for (int i = 0; i < num; i++) {
            int endYTemp = 0;
            if (i % 10 == 0) {
                endYTemp = mViewHeight * 3 / 5;//最长刻度线  尺子高度的五分之三
                mPaint.setTextSize(50);
                mPaint.setColor(getResources().getColor(R.color.balck));
//                绘制文字
                canvas.drawText(String.valueOf(mRulerStartValue + i), mGraduationStartX - 50, endYTemp + textPos + mPaddingTop, mPaint);

            } else if (i % 5 == 0) {
                endYTemp = mViewHeight / 2;
            } else {
                endYTemp = mViewHeight / 4;
            }
            //绘制刻度线
            Log.i(TAG, "endYTemp: " + endYTemp);

            canvas.drawLine(mGraduationStartX, mPaddingTop, mGraduationStartX, endYTemp + mPaddingTop, mPaint);

            canvas.translate(mDeltaX, 0);
        }
        canvas.restore();
    }

    private void drawOuter(Canvas canvas) {

        int left = 0;//开始绘制尺子
        mPaddingTop = getPaddingTop();//上-内边距
        mPaddingBottom = getPaddingBottom();//下-内边距
        int top = mPaddingTop;//尺子顶部距离此 view 顶部的位置
        int bottom = getBottom() - mPaddingBottom;//尺子底边 距离此view 顶部的距离

        Drawable background = getBackground();
        //background包括color和Drawable,这里分开取值
        if (background instanceof ColorDrawable) {
            ColorDrawable colordDrawable = (ColorDrawable) background;
            int color = colordDrawable.getColor();
            mPaint.setColor(color);
            setBackgroundDrawable(null);
        } else {
//            bgBitmap = ((BitmapDrawable) background).getBitmap();
            setBackgroundDrawable(null);
        }
        mPaint.setColor(getResources().getColor(R.color.skyblue));
        canvas.drawRect(left, top, mRulerWidth, bottom, mPaint);
    }


    private int mScrollLen;//滑动距离，正负代表滑动方向，正左滑

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mFingerDownX = (int) event.getX();
                Log.i(TAG, "mFingerDownX: " + mFingerDownX);
                break;
            case MotionEvent.ACTION_MOVE:
                mFingerUpX = (int) event.getX();
                mScrollLen = mFingerDownX - mFingerUpX;
                Log.i(TAG, "mFingerUpX: " + mFingerUpX);

                if (getScrollX() <= mInitScrollX / 2 && mScrollLen <= 0) {
                    /*
                    * */
                    Log.i(TAG, "onTouchEvent: l");
                    break;
                }
                if (getScrollX() >= mRulerWidth - mViewWidth - mInitScrollX && mScrollLen >= 0) {
                    Log.i(TAG, "onTouchEvent: r");
                    break;
                }
                scrollBy(mScrollLen / mVelocity, 0);
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "scrolledX: " + getScrollX());
                Log.i(TAG, "半个屏幕的尺寸: mInitScrollX = " + mInitScrollX);
                Log.i(TAG, "滑动的距离: mScrollLen = " + mScrollLen);
                break;

        }

        return true;
    }


}
