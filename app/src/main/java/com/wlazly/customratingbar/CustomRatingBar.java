package com.wlazly.customratingbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Wlazly on 2016/8/19 0019.
 */
public class CustomRatingBar extends LinearLayout {


    private int starNum;
    private int fillNum;
    private Drawable emptyDrawable;
    private Drawable fillDrawable;
    private boolean isClickable;
    private int margin;
    private RatingBarListener listener;


    public interface RatingBarListener {
         void onRatingBarListener(int grade);
    }

    public void setOnRatingBarListener(RatingBarListener listener){
        this.listener =  listener;
    }

    public CustomRatingBar(Context context) {
        super(context);

    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.CustomRatingBar);
        starNum = typedArray.getInt(R.styleable.CustomRatingBar_startNum, 5);
        fillNum = typedArray.getInt(R.styleable.CustomRatingBar_fillNum, 0);
        emptyDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_emptyDrawable);
        fillDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_fillDrawable);
        isClickable = typedArray.getBoolean(R.styleable.CustomRatingBar_isClickable, true);
        typedArray.recycle();

        //创建startNum个星星
        for (int i = 0; i < starNum; i++) {
            getStarImageView(context);
        }

        //填充星星
        setGrade(fillNum);

        //设置是否可以点击
        setClickable(isClickable);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getStarImageView(Context context) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,displayMetrics);
        ImageView imageView = new ImageView(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(margin,0,0,margin);
        lp.gravity = Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(lp);
        imageView.setBackground(emptyDrawable);
        addView(imageView);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setGrade(int grade) {
        grade = grade>starNum?starNum:grade;
        grade = grade>0?grade:0;
        for (int i = 0;i<grade;i++) {
            ImageView imageView = (ImageView)getChildAt(i);
            imageView.setBackground(fillDrawable);
        }
        for (int j=grade;j<starNum;j++) {
            ImageView imageView = (ImageView)getChildAt(j);
            imageView.setBackground(emptyDrawable);
        }
    }


    public void setClickable(boolean isClickable) {

        for (int i = 0;i<starNum;i++) {
            ImageView imagetView = (ImageView)getChildAt(i);
            imagetView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int fillCount = indexOfChild(v);
                    setGrade(fillCount+1);

                    if (listener != null) {
                        listener.onRatingBarListener(fillCount+1);
                    }
                }

            });
        }
    }

    public void setFillCount(int fillNum) {
        this.fillNum = fillNum;
        setGrade(fillNum);
    }





}
