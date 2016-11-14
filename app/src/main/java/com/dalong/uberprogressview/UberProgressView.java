package com.dalong.uberprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * 仿Uber进度条
 * Created by dalong on 2016/11/14.
 */

public class UberProgressView  extends View{

    public Paint mPaint;
    public Context mContext;
    public int mUberBottomBgColor;
    public int mUberLeftColor;
    public int mUberRightColor;
    public float mUberProgressHeight;
    public int mLeftLineProgress;
    public int mRightLineProgress;

    public UberProgressView(Context context) {
        this(context,null);
    }

    public UberProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UberProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.UberProgressView);
        mUberBottomBgColor=typedArray.getColor(R.styleable.UberProgressView_UberBottomBgColor, Color.BLUE);
        mUberLeftColor=typedArray.getColor(R.styleable.UberProgressView_UberLeftColor, Color.GREEN);
        mUberRightColor=typedArray.getColor(R.styleable.UberProgressView_UberRightColor, Color.RED);
        mUberProgressHeight=typedArray.getDimension(R.styleable.UberProgressView_UberProgressHeight,20);
        typedArray.recycle();


        mPaint=new Paint();
        mPaint.setStrokeWidth(mUberProgressHeight);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }
    private int measureWidth(int widthMeasureSpec){
        int result;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (MeasureSpec.EXACTLY == mode) {
            result = size;
        }else {
            result = 200;
            if (MeasureSpec.AT_MOST == mode) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec){
        int result;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (MeasureSpec.EXACTLY == mode) {
            result = size;
        }else {
            result = 200;
            if (MeasureSpec.AT_MOST == mode) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBgLine(canvas);
        drawLeftLine(canvas);
        drawRifgtLine(canvas);

        mHandler.sendEmptyMessageDelayed(mMsgWhat,10);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mLeftLineProgress=getWidth()/2;
        mRightLineProgress=getWidth()/2;
    }

    /**
     * 画背景线
     * @param canvas
     */
    private void drawBgLine(Canvas canvas) {
        mPaint.setColor(mUberBottomBgColor);
        int start=0;
        int end=getWidth();
        drawLine(start,end,canvas,mPaint);
    }


    /**
     * 画左面进度线
     * @param canvas
     */
    private void drawLeftLine(Canvas canvas) {
        mPaint.setColor(mUberLeftColor);
        if(mLeftLineProgress<=0)mLeftLineProgress=getWidth()/2;
        int end=0;
        drawLine(mLeftLineProgress,end,canvas,mPaint);
    }


    /**
     * 画右面进度线
     * @param canvas
     */
    private void drawRifgtLine(Canvas canvas) {
        mPaint.setColor(mUberRightColor);
        if(mRightLineProgress>=getWidth())mRightLineProgress=getWidth()/2;
        int end=getWidth();
        drawLine(mRightLineProgress,end,canvas,mPaint);
    }


    /**
     * 划线
     * @param startX 起始位置
     * @param endX   结束位置
     * @param canvas
     * @param mPaint
     */
    private void drawLine(int startX, int endX, Canvas canvas,Paint mPaint) {
        canvas.drawLine(startX,getHeight()/2,endX,getHeight()/2,mPaint);
    }

    /**
     * 更新进度条
     */
    public final  int mMsgWhat=1000;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case mMsgWhat:
                    mLeftLineProgress-=4;
                    mRightLineProgress+=4;
                    postInvalidate();
                    break;
            }
        }
    };

}
