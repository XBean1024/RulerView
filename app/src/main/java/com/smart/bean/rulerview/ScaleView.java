package com.smart.bean.rulerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * auther   : bean
 * on       : 2017/11/13
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class ScaleView extends View {
    public static final String TAG = "sss";
    private Paint mPaint;

    public ScaleView(Context context) {
        super(context);
        init(context);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = Util.getWindowWidth() >> 1;
        int height = Util.getWindowHeight() >> 1;
        Rect rect = new Rect(width - 400, height - 400, width + 400, height + 400);
        canvas.drawPoint(Util.getWindowWidth() >> 1, Util.getWindowHeight() >> 1, mPaint);//绘制屏幕中心点

        int num = 20;

        for (int i = 0; i < num; i++) {
            canvas.save();
            float fraction = (float) i / 20;
            canvas.scale(fraction, fraction, rect.centerX(), rect.centerY());
//            canvas.scale(fraction, fraction, width, width);
            canvas.drawRect(rect, mPaint);
            canvas.restore();
        }
    }

    private void init(Context c) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 将画笔设置为空心
        mPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔颜色
        mPaint.setColor(Color.BLACK);
        // 设置画笔宽度
        mPaint.setStrokeWidth(5);
    }

}
