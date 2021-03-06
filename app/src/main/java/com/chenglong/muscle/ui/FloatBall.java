package com.chenglong.muscle.ui;

import com.chenglong.muscle.R;
import com.chenglong.muscle.util.BitmapUtil;
import com.chenglong.muscle.util.ScreenUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AnimationUtils;

public class FloatBall extends View {

    private static final float RADIUS_PX = 20f;
    //	private final String text = "21";
    private Context context;
    private boolean dragState = false;

    private int radius = 0;
    private Paint ballPaint;
    //	private Paint textPaint;
    private Bitmap bitmap;
    private Bitmap bitmap2;

    public FloatBall(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        radius = (int) (ScreenUtil.dp2px(context, RADIUS_PX));

        ballPaint = new Paint();
        ballPaint.setColor(Color.GRAY);
        ballPaint.setAlpha(150);
        // Shader sd = new Shader();
        // mPaint.setShader(sd);
        // bitmap = BitmapUtil.decodeBitmapByRes(context,
        // R.drawable.ic_small);
        // BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
        // TileMode.CLAMP);
        // ballPaint.setShader(shader);
        ballPaint.setAntiAlias(true);

//		textPaint = new Paint();
//		textPaint.setColor(Color.BLACK);
//		FontMetrics metrics = textPaint.getFontMetrics();
//		textPaint.setTextSize(40);

        Bitmap src = BitmapUtil.decodeBitmapByRes(context, R.drawable.ic_small);
        bitmap = Bitmap.createScaledBitmap(src, 2 * radius, 2 * radius, true);
        src = com.chenglong.muscle.util.BitmapUtil.decodeBitmapByRes(context, R.drawable.floatball_press);
        bitmap2 = Bitmap.createScaledBitmap(src, 2 * radius, 2 * radius, true);
        src.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2 * radius, 2 * radius);
        // setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        // super.onDraw(canvas);
        // canvas.drawCircle(radius, radius, radius, ballPaint);
        // Bitmap bitmap = BitmapUtil.decodeBitmapByRes(context,
        // R.drawable.ic_small);
        if (dragState == true) {
            canvas.drawBitmap(bitmap2, 0, 0, null);
        } else {
            canvas.drawBitmap(bitmap, 0, 0, ballPaint);
//			float textWidth = textPaint.measureText(text);
//			float x = radius - textWidth / 2;
//			FontMetrics metrics = textPaint.getFontMetrics();
//			float y = radius - (metrics.descent + metrics.ascent) / 2;
//			canvas.drawText(text, x, y, textPaint);
        }
    }

    public void setDragState(boolean state) {
        this.dragState = state;
        invalidate();
    }
}
