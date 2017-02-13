package com.dd.morphingbutton.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.dd.morphingbutton.IProgress;
import com.dd.morphingbutton.MorphingButton;

public class CircularProgressButton extends MorphingButton implements IProgress {

    public static final int MAX_PROGRESS = 100;
    public static final int MIN_PROGRESS = 0;

    private int mProgress;
    private int mProgressColor;
    private Paint mPaint;
    private RectF mRectF;

    private float barWidth = 8;

    public CircularProgressButton(Context context) {
        super(context);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (!mAnimationInProgress && mProgress > MIN_PROGRESS && mProgress <= MAX_PROGRESS) {
            if (mPaint == null) {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Style.STROKE);
                mPaint.setColor(mProgressColor);
                mPaint.setStrokeWidth(barWidth);
            }

            if (mRectF == null) {
                mRectF = new RectF(
                      barWidth,
                      barWidth,
                      getWidth() - barWidth,
                      getHeight() - barWidth);
            }

            canvas.drawArc(mRectF, 270, mProgress * 3.6f, false, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void morph(@NonNull Params params) {
        super.morph(params);
        mProgress = MIN_PROGRESS;
        mPaint = null;
        mRectF = null;
    }

    @Override
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void morphToProgress(int color, int progressColor, int radius, int duration, int icon) {
        mProgressColor = progressColor;

        Params longRoundedSquare = Params.create()
              .duration(duration)
              .cornerRadius(radius)
              .icon(icon)
              .width(radius)
              .height(radius)
              .color(color)
              .colorPressed(color);
        morph(longRoundedSquare);
    }
}
