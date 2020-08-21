package com.caocao.client.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.caocao.client.R;


public class CustomCheckBox extends AppCompatCheckBox {
    private int drawbleW;

    private int drawbleH;

    public CustomCheckBox(Context context) {

        this(context, null);

    }

    public CustomCheckBox(Context context, AttributeSet attrs) {

        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomCheckBox);

        Drawable drawable = array.getDrawable(R.styleable.CustomCheckBox_android_button);

        drawbleW = (int) array.getDimension(R.styleable.CustomCheckBox_attr_drawableWidth, 25);

        drawbleH = (int) array.getDimension(R.styleable.CustomCheckBox_attr_drawableHight, 25);

        setCompoundDrawablePadding(4);

        setButtonDrawable(drawable);

    }

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }

    @Override

    public void setButtonDrawable(int resId) {

        setButtonDrawable(AppCompatResources.getDrawable(getContext(), resId));

    }

    //一定要在设置 Drawable 之前设置 否者不生效

    public void setDrawbleSize(int drawbleW, int drawbleH) {

        this.drawbleW = drawbleW;

        this.drawbleH = drawbleH;

    }

    @Override

    public void setButtonDrawable(Drawable buttonDrawable) {

        if (buttonDrawable != null) {
            buttonDrawable.setBounds(0, 0, drawbleW, drawbleH);

            setCompoundDrawables(buttonDrawable, null, null, null);
        }

    }

}
