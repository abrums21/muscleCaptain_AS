package com.chenglong.muscle.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.chenglong.muscle.R;
import com.chenglong.muscle.util.ScreenUtil;

/**
 * Created by 20 on 2016/9/25.
 */

public class CircleProgressBar extends View {

    private Paint mPaintOval;
    private Paint mPaintCircle;
    private Paint mPaintText;
    private RectF mOval;
    private final static float STROKE_WIDTH = 4;
    private float mStrokeWidth;
    private int mTextSize;
    private String mText = "";

    private int mCircleRadius;
    private float mProgress = 150f;

    public CircleProgressBar(Context context)
    {
        super(context,null,0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub

        TypedArray ar = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);
        String mTextRead = ar.getString(R.styleable.CircleProgressBar_text);
        if (mTextRead != null)
        {
            mText = mTextRead;
        }

        mStrokeWidth = ar.getDimension(R.styleable.CircleProgressBar_strokeWidth, ScreenUtil.sp2px(context, STROKE_WIDTH));
        ar.recycle();

//        mStrokeWidth = ScreenUtil.sp2px(context, mStrokeWidthRead);

        mPaintOval = new Paint();
        mPaintOval.setAntiAlias(true);
        mPaintOval.setColor(getResources().getColor(R.color.colorPrimary));
        mOval = new RectF();
        mPaintOval.setStrokeWidth(mStrokeWidth); // 线宽
        mPaintOval.setStyle(Paint.Style.STROKE);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.GRAY);
        mPaintCircle.setStrokeWidth(mStrokeWidth); // 线宽
        mPaintCircle.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setFakeBoldText(true);
        mPaintText.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);

        if (height >= width)
        {
            mCircleRadius = width / 2;
        }
        else
        {
            mCircleRadius = height / 2;
        }

        setMeasuredDimension(mCircleRadius * 2, mCircleRadius * 2);

        mOval.top = mStrokeWidth / 2;
        mOval.left = mStrokeWidth / 2;
        mOval.right = mCircleRadius * 2 - mStrokeWidth / 2;
        mOval.bottom = mCircleRadius * 2 - mStrokeWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCircleRadius, mCircleRadius, mCircleRadius - mStrokeWidth / 2, mPaintCircle);
        canvas.drawArc(mOval, -90, mProgress, false, mPaintOval);
        int mTextSize = (int)(0.75 * (mCircleRadius * 2 - mStrokeWidth) / mText.length());
        mPaintText.setTextSize(mTextSize);
        int textWidth = (int) mPaintText.measureText(mText, 0, mText.length());
        canvas.drawText(mText, mCircleRadius - textWidth / 2, mCircleRadius + mTextSize / 2, mPaintText);
    }

    public void setProgress(float mProgress)
    {
         this.mProgress = mProgress * 360;
        invalidate();
    }

    public void setText(String mText)
    {
        this.mText = mText;
    }
}
