package com.impecoven.blake.bimpecovenprojectmissilecommand;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Blake Impecoven on 6/6/16.
 */
public class Scud {
    final float[] destinations = {40, 40 + (65), 40 + (65 * 2),
            40 + (65 * 3), 40 + (65 * 4),
            40 + (65 * 5), 40 + (65 * 6),
            40 + (65 * 7), 40 + (65 * 8)};
    protected float ground_level = 515;

    protected float start;
    protected float destination;
    protected float slope;
    protected boolean stop = false;

    protected float[] currentPos = new float[2]; //x,y

    public Scud() {
        this.destination = destinations[getRandom(9)];
        this.start = (float)getRandom(600);
        this.slope = calculateSlope(this.start, 0, this.destination, ground_level);
        this.currentPos[0] = start;
        this.currentPos[1] = 0;
    }//end scud

    public int getRandom(final int range) {
        Random randomGenerator = new Random();

        return randomGenerator.nextInt(range);
    }//end getRandom

    public void fireScud(Canvas canvas, Paint scudColor) {
        if(!this.stop) {
            canvas.drawLine(start, 0, currentPos[0], currentPos[1], scudColor);
        }
    }//end fireScud

    public void advanceScud() {
        if(!this.stop) {
            if (slope < 0) {
                currentPos[0] -= .5;
                currentPos[1] -= slope/2;
            } else {
                currentPos[0] += .5;
                currentPos[1] += slope/2;
            }
            //currentPos[1] += slope;
        }//end if
        if(this.currentPos[1] >= ground_level) {
            this.stop = true;
        }
    }//end advanceScud

    public float calculateSlope(float startX, float startY, float endX, float endY){
        float slope;
        float x = endX - startX;
        float y = endY - startY;
        slope = y / x;

        return slope;
    }//end calculateSlope
}
