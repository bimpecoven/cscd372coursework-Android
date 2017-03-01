package com.impecoven.blake.bimpecovenlab5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Blake Impecoven on 5/4/16.
 */

public class CustomView extends View{

    final float g = 9.80665f; //gravity = 9.80655m/sec^2
    final float objectMass = 1.0f; //1kg
    final float k = 1.5f; //spring = 1.5kg/sec
    float velocity;

    final float HEIGHT = 800;
    final float WIDTH = 600;

    final float centerX = WIDTH / 2;
    final float centerY = HEIGHT / 2;

    protected float height = HEIGHT;
    protected float width = WIDTH;
    protected float aspect_ratio = width / height;

    final float RECT_WIDTH = 130;
    final float RECT_HEIGHT = 100;
    float rectTop = centerY - (RECT_HEIGHT / 2);

    final float CHAIN_WIDTH = 20;

    Paint outline = new Paint();
    Paint rect = new Paint();

    float y = rectTop;

    public CustomView(Context context) { //Used for programmatically creating view
        super(context);
    }//end constructor

    public CustomView(Context context, AttributeSet attrs) { //when inflated from XML
        super(context, attrs);
    }//end constructor

    public CustomView(Context context, AttributeSet attrs, int defStyle) { //XML but specific base style from a theme
        super(context, attrs, defStyle);
    }//end constructor

    @Override
    protected void onDraw(Canvas canvas) {
        //setup paint
        outline.setColor(Color.rgb(0, 0, 0));
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(5);

        rect.setColor(Color.rgb(0, 0, 0));

        float yScale = height / HEIGHT;
        float xScale = width / WIDTH;
        canvas.scale(xScale, yScale);

        RectF mass = new RectF(
                centerX - (RECT_WIDTH / 2),
                rectTop,
                centerX + (RECT_WIDTH / 2),
                rectTop + RECT_HEIGHT);

        canvas.drawRect(mass, outline);

        float top = 0;
        final float chainLinkCount = 10;

        final float space = rectTop; // for this it will be 300
        float bottom = space / ((chainLinkCount + 1) / 2);//for this it will be 30
        float radius = bottom / 2; // will be 15

        outline.setStrokeWidth(3);
        for(int x = 0; x < 10; x++) {
            RectF temp = new RectF(
                    centerX - (CHAIN_WIDTH / 2),
                    top,
                    centerX + (CHAIN_WIDTH / 2),
                    bottom
            );
            canvas.drawOval(temp, outline);//left, top, right, bottom
            top += radius;
            bottom += radius;
        }//end for
    }//end onDraw

    public void reDraw() {
        float oldY = y;

        double acceleration = g - y *  k /objectMass; //acceleration
        velocity += acceleration  * .1; //velocity
        y += velocity * .1; //top

        rectTop += (oldY - y);


        invalidate();
    }//end reDraw

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        height = h;
        width = w;

        super.onSizeChanged(w, h, oldw, oldh);
    }//end onSizeChanged

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalWidth;
        int finalHeight;

        int allocatedWidth = MeasureSpec.getSize(widthMeasureSpec);
        int allocatedHeight = MeasureSpec.getSize(heightMeasureSpec);

        int potentialWidth = (int) (allocatedHeight * aspect_ratio);
        int potentialHeight = (int) (allocatedWidth / aspect_ratio);

        if(potentialHeight > allocatedHeight) {
            finalHeight = allocatedHeight;
            finalWidth = potentialWidth;
        }//end if
        else {
            finalHeight = potentialHeight;
            finalWidth = allocatedWidth;
        }//end else

        setMeasuredDimension(finalWidth, finalHeight);
    }//end onMeasure
}//end class
