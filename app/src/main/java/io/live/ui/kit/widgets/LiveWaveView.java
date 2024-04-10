package io.live.ui.kit.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * 圆环扩散控件，
 * 直播头像水波纹效果
 */
public class LiveWaveView extends View {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float radius;

    private final long startDuration = 350;
    private final long endDuration = 450;

    private int centerViewWidth;
    private int width, height;

    private boolean isStart;

    public LiveWaveView(Context context) {
        this(context, null);
    }

    public LiveWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typed = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
        int color = typed.getColor(0, Color.BLACK);
        mPaint.setColor(color);
        typed.recycle();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics()));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStart) {
            canvas.drawCircle((float) (width / 2), (float) (height / 2), radius, mPaint);
        }
    }

    @SuppressLint("Recycle")
    private void start() {
        float startValue = 0;
        float endValue = (width - mPaint.getStrokeWidth()) * 0.4f;
        if (centerViewWidth != 0) {
            startValue = (float) centerViewWidth / 2 - mPaint.getStrokeWidth();
            endValue = (float) centerViewWidth / 2 + (float) (width - centerViewWidth) / 4 - mPaint.getStrokeWidth() / 2;
        }
        ValueAnimator val1 = ValueAnimator.ofFloat(startValue, endValue);
        ValueAnimator val2 = ValueAnimator.ofInt(0, 255);

        val1.addUpdateListener(animation -> {
            radius = (float) animation.getAnimatedValue();
            postInvalidate();
        });

        val2.addUpdateListener(animation -> {
            int alpha = (int) animation.getAnimatedValue();
            mPaint.setAlpha(alpha);
            postInvalidate();
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(val1, val2);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(startDuration);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                end();
            }
        });
        set.start();
    }

    @SuppressLint("Recycle")
    private void end() {
        float startValue = (width - mPaint.getStrokeWidth()) * 0.4f;
        float endValue = (width - mPaint.getStrokeWidth()) * 0.5f;

        if (centerViewWidth != 0) {
            startValue = (float) centerViewWidth / 2 + (float) (width - centerViewWidth) / 4 - mPaint.getStrokeWidth() / 2;
            endValue = (float) width * 0.5f - mPaint.getStrokeWidth();
        }
        ValueAnimator val1 = ValueAnimator.ofFloat(startValue, endValue);
        val1.addUpdateListener(animation -> {
            radius = (float) animation.getAnimatedValue();
            postInvalidate();
        });

        ValueAnimator val2 = ValueAnimator.ofInt(255, 0);
        val2.addUpdateListener(animation -> {
            int alpha = (int) animation.getAnimatedValue();
            mPaint.setAlpha(alpha);
            postInvalidate();
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(val1, val2);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(endDuration);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isStart) {
                    start();
                }
            }
        });
        set.start();
    }

    public void bindAndStart(View centerView) {
        isStart = true;
        if (centerViewWidth == 0 && centerView != null) {
            centerView.post(() -> {
                this.centerViewWidth = centerView.getWidth();
                start();
            });
        } else {
            start();
        }
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void stop() {
        mPaint.setAlpha(0);
        radius = 0;
        isStart = false;
        postInvalidate();
    }
}

