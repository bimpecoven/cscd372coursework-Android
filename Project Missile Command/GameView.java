package com.impecoven.blake.bimpecovenprojectmissilecommand;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bimpecoven on 5/25/16.
 */
public class GameView extends View {
    /*
     * Variable Declarations
    **/
    final float HEIGHT = 600;
    final float WIDTH = 600;

    protected float height = HEIGHT;
    protected float width = WIDTH;

    protected float aspect_ratio = width / height;

    public boolean gameOver = false;
    MediaPlayer explode = MediaPlayer.create(getContext(), R.raw.explosion);
    MediaPlayer missile = MediaPlayer.create(getContext(), R.raw.missile);
    MediaPlayer levelup = MediaPlayer.create(getContext(), R.raw.levelup);

    /*
     * Game Statistic's Declarations
    **/
    int score = 0;
    int level = 0;
    final int numberOfScuds = 20;
    int remainingScuds = numberOfScuds;
    Paint scudPaint;
    int scudsleft = 20;
    int scudTimer = 50;

    Drawable zero = getResources().getDrawable(R.drawable.number0);

    /*
     * Color and drawing declarations
    **/
    BitmapFactory bitmapFactory = new BitmapFactory();

    //ground drawing
    final float ground_level = 535;
    Paint ground_paint = new Paint();
    RectF ground;

    //turrets
    Drawable turret1;
    boolean t1_rubble = false;
    Drawable turret2;
    boolean t2_rubble = false;
    Drawable turret3;
    boolean t3_rubble = false;
    final int startTurrets = 20;
    final int turrentCount = 3;
    final int turretSize = 40;
    final int turretSpace = 260;

    //cities
    final int citySpace = turretSpace / 4;
    final int startCities = startTurrets + citySpace;
    final int citySize = turretSize;

    //left
    Drawable c1;
    boolean c1_rubble = false;
    Drawable c2;
    boolean c2_rubble = false;
    Drawable c3;
    boolean c3_rubble = false;
    //right
    Drawable c4;
    boolean c4_rubble = false;
    Drawable c5;
    boolean c5_rubble = false;
    Drawable c6;
    boolean c6_rubble = false;

    /*
     * stuff needed for aimer/scuds
    **/
    Paint shot;
    final int[] destinations = {40, 40 + (65), 40 + (65 * 2),
            40 + (65 * 3), 40 + (65 * 4),
            40 + (65 * 5), 40 + (65 * 6),
            40 + (65 * 7), 40 + (65 * 8)};

    Patriot[]patriots = new Patriot[60];
    int patriotCount = 0;
    Scud[]scuds = initiateScuds();
    int scudCount = 0;
    int totalScuds = 0;

    public float shotFrom[] = {40, 300, 560};

    Patriot newPatriot;
    boolean fireShot = false;
    float[] shotCoordinates = new float[2];

    //{x,y}
    Paint aim;
    public float []xCenter = {300, 300};
    float []xTopLeft = {xCenter[0] - 5, xCenter[1] - 5};
    float []xTopRight = {xCenter[0] + 5, xCenter[1] - 5};
    float []xBottomLeft = {xCenter[0] - 5, xCenter[1] + 5};
    float []xBottomRight = {xCenter[0] + 5, xCenter[1] + 5};

    /*
     * Constructors
    **/
    public GameView(Context context) {
        super(context);
    }//end

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }//end

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }//end

    @Override
    protected void onDraw(Canvas canvas) {
        /*
         * Scale our canvas
        **/
        float yScale = height / HEIGHT;
        float xScale = width / WIDTH;
        canvas.scale(xScale, yScale);

        explode.setVolume(.5f, .5f);


        aim = new Paint();
        aim.setColor(Color.WHITE);
        aim.setStrokeWidth(3);

        shot = new Paint();
        shot.setColor(Color.BLUE);
        shot.setStrokeWidth(3);

        /*
         * Init all drawings and scenery
        **/
        canvas.drawColor(Color.BLACK);

        //aimer
        canvas.drawLine(xTopLeft[0], xTopLeft[1], xBottomRight[0], xBottomRight[1], aim);// startx, starty, stopx, stopy
        canvas.drawLine(xBottomLeft[0], xBottomLeft[1], xTopRight[0], xTopRight[1], aim);

        //ground
        ground_paint.setColor(Color.rgb(40, 170, 0));
        ground = new RectF(
                0,
                ground_level,
                WIDTH,
                HEIGHT
        );//left, top, right, bottom
        canvas.drawRect(ground, ground_paint);

        //turrets
        if(t1_rubble) {
            turret1 = getResources().getDrawable(R.drawable.turret);
            assert turret1 != null;
            turret1.setBounds(startTurrets, (int)(ground_level - (turretSize)), (20 + turretSize), (int)ground_level);
            assert zero!= null;
            zero.setBounds(startTurrets, (int)(ground_level + (turretSize)), (20 + turretSize), (int)ground_level);
            zero.draw(canvas);
        }//end if
        else {
            turret1 = getResources().getDrawable(R.drawable.turret);
            assert turret1 != null;
            turret1.setBounds(startTurrets, (int) (ground_level - turretSize), (20 + turretSize), (int) ground_level);
        }//end else
        turret1.draw(canvas);

        if(t2_rubble) {
            turret2 = getResources().getDrawable(R.drawable.turret);
            assert turret2 != null;
            turret2.setBounds(startTurrets + turretSpace, (int)(ground_level - (turretSize)), (startTurrets + turretSize) + turretSpace, (int)ground_level);
            assert zero!= null;
            zero.setBounds(startTurrets + turretSpace, (int)(ground_level + (turretSize)), (startTurrets + turretSize) + turretSpace, (int)ground_level);
            zero.draw(canvas);
        }//end if
        else {
            turret2 = getResources().getDrawable(R.drawable.turret);
            assert turret2 != null;
            turret2.setBounds(startTurrets + turretSpace, (int) (ground_level - turretSize), (startTurrets + turretSize) + turretSpace, (int) ground_level);
        }//end else
        turret2.draw(canvas);

        if(t3_rubble) {
            turret3 = getResources().getDrawable(R.drawable.turret);
            assert turret3 != null;
            turret3.setBounds(startTurrets + (turretSpace * 2), (int)(ground_level - (turretSize)), (startTurrets + turretSize) + (turretSpace * 2), (int)ground_level);
            assert zero!= null;
            zero.setBounds(startTurrets + (turretSpace * 2), (int)(ground_level + (turretSize)), (startTurrets + turretSize) + (turretSpace * 2), (int)ground_level);
            zero.draw(canvas);
        }//end if
        else {
            turret3 = getResources().getDrawable(R.drawable.turret);
            assert turret3 != null;
            turret3.setBounds(startTurrets + (turretSpace * 2), (int) (ground_level - turretSize), (startTurrets + turretSize) + (turretSpace * 2), (int) ground_level);
        }//end else
        turret3.draw(canvas);

        /*
         * Cities - we will separate these by subsections left and right,
         * the origin being the middle turret
        **/

        //left cities
        if(c1_rubble) {
            c1 = getResources().getDrawable(R.drawable.destroyed3);
            assert c1 != null;
            c1.setBounds(startCities, (int)(ground_level - (citySize / 2)), startCities + citySize, (int)(ground_level));
        }
        else {
            c1 = getResources().getDrawable(R.drawable.city2);
            assert c1 != null;
            c1.setBounds(startCities, (int)(ground_level - citySize), startCities + citySize, (int)(ground_level));
        }
        c1.draw(canvas);

        if(c2_rubble) {
            c2 = getResources().getDrawable(R.drawable.destroyed3);
            assert c2 != null;
            c2.setBounds(startCities + (citySpace), (int)(ground_level - (citySize / 2)), startCities + citySize + (citySpace), (int)(ground_level));
        }
        else {
            c2 = getResources().getDrawable(R.drawable.city2);
            assert c2 != null;
            c2.setBounds(startCities + (citySpace), (int)(ground_level - citySize), startCities + citySize + (citySpace), (int)(ground_level));
        }
        c2.draw(canvas);

        if(c3_rubble) {
            c3 = getResources().getDrawable(R.drawable.destroyed3);
            assert c3 != null;
            c3.setBounds(startCities + (citySpace * 2), (int)(ground_level - (citySize / 2)), startCities + citySize + (citySpace * 2), (int)(ground_level));
        }
        else {
            c3 = getResources().getDrawable(R.drawable.city2);
            assert c3 != null;
            c3.setBounds(startCities + (citySpace * 2), (int)(ground_level - citySize), startCities + citySize + (citySpace * 2), (int)(ground_level));
        }
        c3.draw(canvas);

        //right cities
        if(c4_rubble) {
            c4 = getResources().getDrawable(R.drawable.destroyed3);
            assert c4 != null;
            c4.setBounds(startCities + turretSpace, (int)(ground_level - (citySize / 2)), startCities + turretSpace + citySize, (int)(ground_level));
        }
        else {
            c4 = getResources().getDrawable(R.drawable.city2);
            assert c4 != null;
            c4.setBounds(startCities + turretSpace, (int)(ground_level - citySize), startCities + turretSpace + citySize, (int)(ground_level));
        }
        c4.draw(canvas);

        if(c5_rubble) {
            c5 = getResources().getDrawable(R.drawable.destroyed3);
            assert c5 != null;
            c5.setBounds(startCities + turretSpace + (citySpace), (int)(ground_level - (citySize / 2)), startCities  + turretSpace + citySize + (citySpace), (int)(ground_level));
        }
        else {
            c5 = getResources().getDrawable(R.drawable.city2);
            assert c5 != null;
            c5.setBounds(startCities + turretSpace + (citySpace), (int)(ground_level - citySize), startCities  + turretSpace + citySize + (citySpace), (int)(ground_level));
        }
        c5.draw(canvas);

        if(c6_rubble) {
            c6 = getResources().getDrawable(R.drawable.destroyed3);
            assert c6 != null;
            c6.setBounds(startCities + turretSpace + (citySpace * 2), (int)(ground_level - (citySize / 2)), startCities  + turretSpace + citySize + (citySpace * 2), (int)(ground_level));
        }
        else {
            c6 = getResources().getDrawable(R.drawable.city2);
            assert c6 != null;
            c6.setBounds(startCities + turretSpace + (citySpace * 2), (int)(ground_level - citySize), startCities  + turretSpace + citySize + (citySpace * 2), (int)(ground_level));
        }
        c6.draw(canvas);


        for (int x = 0; x < patriotCount; x++) {
            patriots[x].advancePatriot();
            patriots[x].drawPatriot(canvas, shot);
            if(patriots[x].explode) {
                explode.start();
                patriots[x].killScud(scuds);
                this.score += patriots[x].getScore();
            }
        }//end for

        scudPaint = new Paint();
        scudPaint.setColor(Color.RED);
        scudPaint.setStrokeWidth(3);

        for(scudCount = 0; scudCount < totalScuds; scudCount++) {
            scuds[scudCount].advanceScud();
            scuds[scudCount].fireScud(canvas, scudPaint);
            remainingScuds--;
            if(scuds[scudCount].currentPos[1] >= 515) {
                destroy((int)scuds[scudCount].destination);
            }
        }//end for

        if(c1_rubble && c2_rubble && c3_rubble && c4_rubble && c5_rubble && c6_rubble) {
            gameOver = true;
            Drawable youlose = getResources().getDrawable(R.drawable.gameover);
            assert youlose != null;
            youlose.setBounds(0, 0, 600, 600);//l,t,r,b
            youlose.draw(canvas);
            levelup.setVolume(.5f, .5f);
            levelup.start();
        }//end if
    }//end onDraw

    public int scudsLeft() {
        return this.scudsleft;
    }//end scudsleft


    public int getScore() {
        return this.score;
    }//end updateScore

    protected void destroy(final int x) {
        switch (x) {
            case 40:
                t1_rubble = true;
                break;
            case 105:
                c1_rubble = true;
                break;
            case 170:
                c2_rubble = true;
                break;
            case 235:
                c3_rubble = true;
                break;
            case 300:
                t2_rubble = true;
                break;
            case 365:
                c4_rubble = true;
                break;
            case 430:
                c5_rubble = true;
                break;
            case 495:
                c6_rubble = true;
                break;
            case 560:
                t3_rubble = true;
                break;
        }//end switch
    }//end destroy

//    final int[] destinations = {40, 40 + (65), 40 + (65 * 2),
//            40 + (65 * 3), 40 + (65 * 4),
//            40 + (65 * 5), 40 + (65 * 6),
//            40 + (65 * 7), 40 + (65 * 8)};

    protected void setAimer(final float x, final float y) {
        this.xCenter[0] = x;
        this.xCenter[1] = y;

        if(y > 425) {
            xCenter[1] = 425;
        }//end if

        xTopLeft[0] = xCenter[0] - 5;
        xTopLeft[1] = xCenter[1] - 5;
        xTopRight[0] = xCenter[0] + 5;
        xTopRight[1] = xCenter[1] - 5;
        xBottomLeft[0] = xCenter[0] - 5;
        xBottomLeft[1] = xCenter[1] + 5;
        xBottomRight[0] = xCenter[0] + 5;
        xBottomRight[1] = xCenter[1] + 5;

        missile.setVolume(.5f, .5f);
        missile.start();
        firePatriot(x, xCenter[1]);


    }//end setAimer

    protected void drawPatriots(Canvas canvas) {

    }//end drawPatriots

    protected void firePatriot(final float x, final float y) {
        shotCoordinates[0] = x;
        shotCoordinates[1] = y;

        fireShot = true;
        int chooseTurret;

        if(x <= 170) {
            chooseTurret = 0;
        }//end if
        else if(x > 170 && x <= 430) {
            chooseTurret = 1;
        }//end elseif
        else {
            chooseTurret = 2;
        }//end else

        if(patriotCount < 60) {
            newPatriot = new Patriot(x, y, chooseTurret);
            patriots[patriotCount] = newPatriot;
            patriotCount+=1;
        }//end if
    }//end firePatriot

    public Scud[] initiateScuds() {
        Scud[] tempScuds = new Scud[numberOfScuds];
        for(int x = 0; x < tempScuds.length; x++) {
            tempScuds[x] = new Scud();
        }//end for
        return tempScuds;
    }//end initiateScuds

    protected void reDraw() {

        if(totalScuds < numberOfScuds && scudTimer % 50 == 0) {
            totalScuds+=5;
            scudsleft -= 5;
        }//end if
        scudTimer++;
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
