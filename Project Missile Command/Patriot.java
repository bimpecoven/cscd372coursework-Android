package com.impecoven.blake.bimpecovenprojectmissilecommand;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by bimpecoven on 6/6/16.
 */
public class Patriot {
    public float shotFrom[] = {40, 300, 560};
    public float ground_level = 535;

    public int score = 0;

    protected float destinationX;
    protected float destinationY;
    protected int turretNumber;
    protected float currentX;
    protected float currentY;
    protected boolean explode;
    protected float slope;
    protected float radius = 40;
    protected boolean stop = false;
    protected int explodeTime = 20;

    public Patriot(final float destinationX, final float destinationY, final int turretNumber) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.turretNumber = turretNumber;
        this.currentX = shotFrom[turretNumber];
        this.currentY = ground_level;
        this.explode = false;
        this.slope = calculateSlope(currentX, currentY, destinationX, destinationY);
    }//end constructor

    public float calculateSlope(float startX, float startY, float endX, float endY){
        float slope;
        float x = endX - startX;
        float y = endY - startY;
        slope = y / x;

        return slope;
    }//end calculateSlope

    public float getCurrentX() { return this.currentX; }
    public float getCurrentY() { return this.currentY; }
    public boolean isExplode() { return this.explode; }
    public float getSlope() { return slope; }
    public int getTurretNumber() { return turretNumber; }
    public int getScore() { return this.score; }

    public void drawPatriot(Canvas canvas, Paint shot) {
        if(!this.stop) {
            if (!explode) {
                canvas.drawLine(shotFrom[this.turretNumber], ground_level - 45, this.currentX, this.currentY, shot);
            } else {
                canvas.drawCircle(this.destinationX, this.destinationY, radius, shot);
                explodeTime--;
                if(explodeTime <= 0) {
                    this.stop = true;
                    this.explode = false;
                }//end if
            }
        }
    }//end drawPatriot

    public void advancePatriot() {
        if(this.currentY >= this.destinationY) {
            if (slope < 0) {
                currentX += 10;
                currentY += 10 * slope;
            } else {
                currentX -= 10;
                currentY -= 10 * slope;
            }
        }//end if
        else {
            if(!this.stop) {
                this.explode = true;
            }
        }

    }//end advancePatriot

    public void killScud(Scud[]scuds) {
        for(int x = 0; x < scuds.length; x++) {
            if(scuds[x].currentPos[0] >= this.destinationX - 40 && scuds[x].currentPos[0] <= this.destinationX + 40
                    && scuds[x].currentPos[1] >= this.destinationY - 40 && scuds[x].currentPos[1] <= this.destinationY + 40 && this.explode) {
                scuds[x].stop = true;
                score += 20;
            }
        }//end for
    }

}//end class
