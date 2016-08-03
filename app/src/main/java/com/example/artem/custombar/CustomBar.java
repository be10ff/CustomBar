package com.example.artem.custombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

/**
 * Created by artem on 02.08.16.
 */
public class CustomBar extends View {

    private int value;
    private int leftStartColor;
    private int leftEndColor;
    private int rightStartColor;
    private int rightEndColor;
    private float cornerRadius;
    private float dashSpace;
    private int dashAngle;

    private Paint paintLeft;
    private Paint paintRight;
    private RectF bounds;
    private Path leftPart;
    private Path rightPart;
    private RectF leftTopCorner;
    private RectF leftBottomCorner;
    private RectF rightTopCorner;
    private RectF rightBottomCorner;

    private LinearGradient leftGradient;
    private LinearGradient rightGradient;

    public CustomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomBar,
                0, 0);

        try {
            leftStartColor = a.getInt(R.styleable.CustomBar_leftStartColor, leftStartColor);
            leftEndColor = a.getInt(R.styleable.CustomBar_leftEndColor, leftEndColor);
            rightStartColor = a.getInt(R.styleable.CustomBar_rightStartColor, rightStartColor);
            rightEndColor = a.getInt(R.styleable.CustomBar_rightEndColor, rightEndColor);
            value = a.getInt(R.styleable.CustomBar_value, 0);
            cornerRadius = a.getDimension(R.styleable.CustomBar_cornerRadius, cornerRadius);
            dashSpace = a.getDimension(R.styleable.CustomBar_dashSpace, dashSpace);
            dashAngle = a.getInt(R.styleable.CustomBar_dashAngle, dashAngle);
        } finally {
            a.recycle();
        }

        init();

    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        bounds.set(0, 0, getWidth(), getHeight());
        leftGradient = new LinearGradient(0, 0, bounds.left + bounds.width()*value/100 - dashSpace/2 + (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), 0, leftStartColor, leftEndColor, Shader.TileMode.CLAMP);
        rightGradient = new LinearGradient(bounds.left + bounds.width()*value/100 + dashSpace/2 - (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), 0, bounds.right,0, rightStartColor, rightEndColor, Shader.TileMode.CLAMP);
        invalidate();
        requestLayout();
    }

    private void init() {

        paintLeft = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLeft.setStyle(Paint.Style.FILL);
        paintRight = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRight.setStyle(Paint.Style.FILL);

        bounds = new RectF();
        leftPart = new Path();
        rightPart = new Path();
        leftTopCorner = new RectF();
        leftBottomCorner = new RectF();
        rightTopCorner = new RectF();
        rightBottomCorner = new RectF();

        leftGradient = new LinearGradient(0, 0, bounds.left + bounds.width()*value/100 - dashSpace/2 + (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), 0, leftStartColor, leftEndColor, Shader.TileMode.CLAMP);
        rightGradient = new LinearGradient(bounds.left + bounds.width()*value/100 + dashSpace/2 - (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), 0, bounds.right,0, rightStartColor, rightEndColor, Shader.TileMode.CLAMP);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(0, 0, w, h);
        leftGradient = new LinearGradient(0, 0, bounds.left + bounds.width()*value/100 - dashSpace/2 + (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), 0, leftStartColor, leftEndColor, Shader.TileMode.CLAMP);
        rightGradient = new LinearGradient(bounds.left + bounds.width()*value/100 + dashSpace/2 - (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), 0, bounds.right,0, rightStartColor, rightEndColor, Shader.TileMode.CLAMP);

    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bounds.set(0, 0, getWidth(), getHeight());

        leftPart.reset();
        leftPart.moveTo(bounds.left, bounds.top + cornerRadius/2);
        leftTopCorner.set(bounds.left, bounds.top, bounds.left + cornerRadius, bounds.top + cornerRadius);
        leftPart.arcTo(leftTopCorner, -180f, 90f, true);
        leftPart.lineTo(bounds.left + bounds.width()*value/100 - dashSpace/2 + (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), bounds.top);
        leftPart.lineTo(bounds.left + bounds.width()*value/100  - dashSpace/2 - (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2) , bounds.bottom);
        leftPart.lineTo( bounds.left + cornerRadius/2, bounds.bottom);
        leftBottomCorner.set(bounds.left, bounds.bottom - cornerRadius, bounds.left + cornerRadius, bounds.bottom);
        leftPart.arcTo(leftBottomCorner, -270f, 90f, true);
        leftPart.lineTo(bounds.left, bounds.top + cornerRadius/2);
        paintLeft.setShader(leftGradient);
        canvas.drawPath(leftPart, paintLeft);

        rightPart.reset();
        rightPart.moveTo(bounds.right, bounds.bottom - cornerRadius/2);
        rightBottomCorner.set(bounds.right - cornerRadius, bounds.bottom - cornerRadius, bounds.right, bounds.bottom);
        rightPart.arcTo(rightBottomCorner, 0f, 90f, true);
        rightPart.lineTo(bounds.left + bounds.width()*value/100 + dashSpace/2 - (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2), bounds.bottom);
        rightPart.lineTo(bounds.left + bounds.width()*value/100  + dashSpace/2 + (float)(bounds.height()/Math.tan(Math.PI*dashAngle/180)/2) , bounds.top);
        rightPart.lineTo( bounds.right - cornerRadius/2, bounds.top);
        rightTopCorner.set(bounds.right - cornerRadius, bounds.top, bounds.right, bounds.top + cornerRadius);
        rightPart.arcTo(rightTopCorner, -90f, 90f, true);
        rightPart.lineTo(bounds.right, bounds.bottom - cornerRadius/2);
        paintRight.setShader(rightGradient);
        canvas.drawPath(rightPart, paintRight);
    }

}
