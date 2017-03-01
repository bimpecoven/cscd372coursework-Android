package com.impecoven.blake.bimpecovenlab4_new;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Blake Impecoven on 4/21/16.
 */
public class SevenSegment extends View {

    protected int value;
    protected boolean[] toggle;

    final int brightRed = Color.rgb(255, 0, 0);
    final int darkRed = Color.rgb(76, 0, 0);

    final float[] xy = {40, 30, 50, 40, 190, 40, 200, 30, 190, 20, 50, 20};

    final boolean[][] onOffState = {
            {true, true, true, false, true, true, true},//0
            {false, false, true, false, false, true, false},//1
            {true, false, true, true, true, false, true},//2
            {true, false, true, true, false, true, true},//3
            {false, true, true, true, false, true, false},//4
            {true, true, false, true, false, true, true},//5
            {true, true, false, true, true, true, true},//6
            {true, false, true, false, false, true, false},//7
            {true, true, true, true, true, true, true},//8
            {true, true, true, true, false, true, true},//9
            {false, false, false, false, false, false, false},//off
    };//end 2d boolean

    protected float HEIGHT = 190;
    protected float WIDTH = 110;

    float aspect_ratio = WIDTH / HEIGHT;

    public SevenSegment(Context context) { //Used for programmatically creating view
        super(context);

        setLayerType(this.LAYER_TYPE_SOFTWARE, null);
        //if(!isInEditMode())
            initialize();
    }//end constructor

    public SevenSegment(Context context, AttributeSet attrs) { //when inflated from XML
        super(context, attrs);

        setLayerType(this.LAYER_TYPE_SOFTWARE, null);
        //if(!isInEditMode())
            initialize();
    }//end constructor

    public SevenSegment(Context context, AttributeSet attrs, int defStyle) { //XML but specific base style from a theme
        super(context, attrs, defStyle);

        setLayerType(this.LAYER_TYPE_SOFTWARE, null);
        //if(!isInEditMode())
            initialize();
    }//end constructor

    private void initialize() {
        this.toggle = new boolean[7];
        this.value = 10;
        //setWillNotDraw(false);
    }//end initialize

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        Log.wtf("DBUG", "onDraw() value="+this.value) ;

        //getValue();

        //Initialize paints
        Paint brightR = new Paint();
        brightR.setColor(brightRed);

        Paint darkR = new Paint();
        darkR.setColor(darkRed);

        canvas.drawColor(Color.BLACK);

        float canvasH = canvas.getHeight();
        float canvasW = canvas.getWidth();

        float yScale = HEIGHT / canvasH;
        float xScale = WIDTH / canvasW;
        canvas.scale(xScale, yScale);
        canvas.save();
        canvas.restore();
        isInEditMode();

        Path sevenSeg = new Path();
        makePath(sevenSeg);

        //initial seven seg - Top - 0
        if(toggle[0])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);

        //Top Left - 1
        canvas.restore();
        canvas.save();
        canvas.scale(xScale, yScale);
        canvas.translate(70, -10);
        canvas.rotate(90);
        canvas.drawPath(sevenSeg, brightR);
        if(toggle[1])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);

        //Top Right - 2
        canvas.restore();
        canvas.save();
        canvas.scale(xScale, yScale);
        canvas.translate(230, -10);
        canvas.rotate(90);
        if(toggle[2])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);

        //Middle - 3
        canvas.restore();
        canvas.save();
        canvas.scale(xScale, yScale);
        canvas.translate(0, 160);
        if(toggle[3])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);

        //Bottom Left - 4
        canvas.restore();
        canvas.save();
        canvas.scale(xScale, yScale);
        canvas.translate(70, 150);
        canvas.rotate(90);
        if(toggle[4])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);

        //Bottom Right - 5
        canvas.restore();
        canvas.save();
        canvas.scale(xScale, yScale);
        canvas.translate(230, 150);
        canvas.rotate(90);
        if(toggle[5])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);

        //Bottom - 6
        canvas.restore();
        canvas.save();
        canvas.scale(xScale, yScale);
        canvas.translate(0, 320);
        if(toggle[6])
            canvas.drawPath(sevenSeg, brightR);
        else
            canvas.drawPath(sevenSeg, darkR);
    }//end onDraw

    private void makePath(Path path) {
        path.moveTo(xy[0], xy[1]);

        path.lineTo(xy[2], xy[3]);
        path.lineTo(xy[4], xy[5]);
        path.lineTo(xy[6], xy[7]);
        path.lineTo(xy[8], xy[9]);
        path.lineTo(xy[10], xy[11]);
        path.lineTo(xy[0], xy[1]);

        path.close();
    }//end makePath

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        HEIGHT = h;
        WIDTH = w;

        aspect_ratio = WIDTH / HEIGHT;
        super.onSizeChanged(w, h, oldw, oldh);
    }//end onSizeChanged

    protected void setValue(int changeValue) {
        if(changeValue == 10)
            this.value = changeValue;
        else
            changeValue = changeValue % 10;

        this.value = changeValue;
        this.toggle = onOffState[this.value];
        Log.wtf("DBUG", "setValue() value="+this.value) ;

        invalidate();
    }//end set

    protected int getValue() {
        return this.value % 10;
    }//end get

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
