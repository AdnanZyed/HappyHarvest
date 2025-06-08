package com.example.happyharvest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class HollowSquareView extends View {

    private Paint paint;
    private Paint holePaint;

    public HollowSquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF6200EE);

        holePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        holePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rect, 40f, 40f, paint);

        float radius = getWidth() / 4f;
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, holePaint);
    }
}
