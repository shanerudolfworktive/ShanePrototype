package com.prototype.shane.shaneprototype.view.paintCode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.prototype.shane.shaneprototype.R;

/**
 * TODO: document your custom view class.
 */
public class RobotView extends View {

    private float angle;
    private int red;

    public RobotView(Context context) {
        super(context);
        init(null, 0);
    }

    public RobotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RobotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RobotView, defStyle, 0);
        angle = a.getFloat(R.styleable.RobotView_angle, 180);
        red = a.getInteger(R.styleable.RobotView_red, 255);
        a.recycle();

    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        invalidate();
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RobotStyleKit.drawRobot(canvas, new RectF(0,0,getWidth(),getHeight()),RobotStyleKit.ResizingBehavior.AspectFit, Color.argb(red,30, 198, 57), angle);
    }


}
