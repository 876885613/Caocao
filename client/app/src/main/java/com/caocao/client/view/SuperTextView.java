package com.caocao.client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.StringUtils;
import com.caocao.client.R;

public class SuperTextView extends RelativeLayout {

    private int mLeftTextColor = Color.parseColor("#2a2a2a");
    private int mLeftTextHintColor = Color.parseColor("#999999");

    private int mRightTextColor = Color.parseColor("#2a2a2a");
    private int mRightTextHintColor = Color.parseColor("#999999");

    private int mBottomDividerColor = Color.parseColor("#e5e5e5");

    private int mLeftIconRes;
    private int mRightIconRes;
    private int mLeftTextRes;
    private String mLeftText;
    private int mRightTextRes;
    private String mRightText;
    private float mLeftTextSize;
    private int mLeftTextSizeRes;
    private float mRightTextSize;
    private int mRightTextSizeRes;
    private int mLeftTextColorRes;
    private int mRightTextColorRes;
    private int mLeftTextHintColorRes;
    private int mRightTextHintColorRes;
    private int mLeftTvDrawableLeftRes;
    private int mLeftTvDrawableRightRes;
    private int mBottomDividerColorRes;
    private float mBottomDividerHeight;
    private int mBottomDividerHeightRes;
    private float mBottomDividerMarginLeft;
    private int mBottomDividerMarginLeftRes;
    private float mBottomDividerMarginRight;
    private int mBottomDividerMarginRightRes;
    private int mBottomDividerVisibility;

    private String mLeftTextHint;
    private int mLeftTextHintRes;
    private String mRightTextHint;
    private int mRightTextHintRes;
    private int mRightTvDrawableLeftRes;
    private int mRightTvDrawableRightRes;
    private int mRightEditRes;
    private String mRightEdit;
    private int mRightEditHintRes;
    private String mRightEditHint;


    private AppCompatTextView tvRightView;
    private AppCompatTextView tvLeftView;
    private AppCompatEditText etRightView;
    private AppCompatImageView ivRightView;
    private int inputType;

    public SuperTextView(Context context) {
        this(context, null);
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_super_text, this, true);


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SuperTextView);
        mLeftIconRes = array.getResourceId(R.styleable.SuperTextView_sLeftIcon, 0);
        mRightIconRes = array.getResourceId(R.styleable.SuperTextView_sRightIcon, 0);
        mLeftTextRes = array.getResourceId(R.styleable.SuperTextView_sLeftText, 0);
        mLeftText = array.getString(R.styleable.SuperTextView_sLeftText);
        mRightTextRes = array.getResourceId(R.styleable.SuperTextView_sRightText, 0);
        mRightText = array.getString(R.styleable.SuperTextView_sRightText);
        mLeftTextSize = array.getDimension(R.styleable.SuperTextView_sLeftTextSize, 0);
        mLeftTextSizeRes = array.getResourceId(R.styleable.SuperTextView_sLeftTextSize, 0);
        mRightTextSize = array.getDimension(R.styleable.SuperTextView_sRightTextSize, 0);
        mRightTextSizeRes = array.getResourceId(R.styleable.SuperTextView_sRightTextSize, 0);
        mLeftTextColor = array.getColor(R.styleable.SuperTextView_sLeftTextColor, mLeftTextColor);
        mLeftTextColorRes = array.getResourceId(R.styleable.SuperTextView_sLeftTextColor, R.color.t1);
        mRightTextColor = array.getColor(R.styleable.SuperTextView_sLeftTextColor, mRightTextColor);
        mRightTextColorRes = array.getResourceId(R.styleable.SuperTextView_sLeftTextColor, R.color.t1);


        mLeftTextHint = array.getString(R.styleable.SuperTextView_sLeftTextHint);
        mLeftTextHintRes = array.getResourceId(R.styleable.SuperTextView_sLeftTextHint, 0);

        mRightTextHint = array.getString(R.styleable.SuperTextView_sRightTextHint);
        mRightTextHintRes = array.getResourceId(R.styleable.SuperTextView_sRightTextHint, 0);


        mLeftTextHintColor = array.getColor(R.styleable.SuperTextView_sLeftTextHintColor, mLeftTextHintColor);
        mLeftTextHintColorRes = array.getResourceId(R.styleable.SuperTextView_sLeftTextHintColor, R.color.t3);

        mRightTextHintColor = array.getColor(R.styleable.SuperTextView_sRightTextHintColor, mRightTextHintColor);
        mRightTextHintColorRes = array.getResourceId(R.styleable.SuperTextView_sRightTextHintColor, R.color.t3);

        inputType = array.getInt(R.styleable.SuperTextView_android_inputType, EditorInfo.TYPE_CLASS_TEXT);


        mLeftTvDrawableLeftRes = array.getResourceId(R.styleable.SuperTextView_sLeftTvDrawableLeft, 0);
        mLeftTvDrawableRightRes = array.getResourceId(R.styleable.SuperTextView_sLeftTvDrawableRight, 0);

        mRightTvDrawableLeftRes = array.getResourceId(R.styleable.SuperTextView_sRightTvDrawableLeft, 0);
        mRightTvDrawableRightRes = array.getResourceId(R.styleable.SuperTextView_sRightTvDrawableRight, 0);


        mRightEditRes = array.getResourceId(R.styleable.SuperTextView_sRightEdit, 0);
        mRightEdit = array.getString(R.styleable.SuperTextView_sRightEdit);

        mRightEditHintRes = array.getResourceId(R.styleable.SuperTextView_sRightEditHint, 0);
        mRightEditHint = array.getString(R.styleable.SuperTextView_sRightEditHint);


        mBottomDividerColor = array.getColor(R.styleable.SuperTextView_sBottomDividerColor, mBottomDividerColor);
        mBottomDividerColorRes = array.getResourceId(R.styleable.SuperTextView_sBottomDividerColor, R.color.color_divider_line);


        mBottomDividerHeight = array.getDimension(R.styleable.SuperTextView_sBottomDividerHeight, dp2px(0.5f));
        mBottomDividerHeightRes = array.getResourceId(R.styleable.SuperTextView_sBottomDividerHeight, 0);

        mBottomDividerMarginLeft = array.getDimension(R.styleable.SuperTextView_sBottomDividerMarginLeft, dp2px(10));
        mBottomDividerMarginLeftRes = array.getResourceId(R.styleable.SuperTextView_sBottomDividerMarginLeft, 0);

        mBottomDividerMarginRight = array.getDimension(R.styleable.SuperTextView_sBottomDividerMarginRight, dp2px(10));
        mBottomDividerMarginRightRes = array.getResourceId(R.styleable.SuperTextView_sBottomDividerMarginRight, 0);

        mBottomDividerVisibility = array.getInt(R.styleable.SuperTextView_sBottomDividerVisibility, VISIBLE);

        array.recycle();

        initView();
    }


    private void initView() {
        AppCompatImageView ivLeftIcoView = findViewById(R.id.iv_left_ico);
        tvLeftView = findViewById(R.id.tv_left);
        tvRightView = findViewById(R.id.tv_right);
        etRightView = findViewById(R.id.et_right);
        ivRightView = findViewById(R.id.iv_right);
        View lineView = findViewById(R.id.line);

        if (mLeftIconRes != 0) {
            ivLeftIcoView.setImageResource(mLeftIconRes);
        }

        if (mLeftTextRes != 0) {
            tvLeftView.setText(mLeftTextRes);
        }

        if (!StringUtils.isEmpty(mLeftText)) {
            tvLeftView.setText(mLeftText);
        }

        if (mLeftTextColorRes != 0) {
            tvLeftView.setTextColor(getContext().getResources().getColor(mLeftTextColorRes));
        }


        if (mLeftTextColor != 0) {
            tvLeftView.setTextColor(mLeftTextColor);
        }


        if (mLeftTextHintRes != 0) {
            tvLeftView.setHint(mLeftTextHintRes);
        }


        if (!StringUtils.isEmpty(mLeftTextHint)) {
            tvLeftView.setHint(mLeftTextHint);
        }


        if (mLeftTextColor != 0) {
            tvLeftView.setTextColor(mLeftTextColor);
        }

        if (mLeftTextHintColorRes != 0) {
            tvLeftView.setHintTextColor(getContext().getResources().getColor(mLeftTextHintColorRes));
        }


        if (mLeftTextHintColor != 0) {
            tvLeftView.setHintTextColor(mLeftTextHintColor);
        }


        if (mLeftTvDrawableRightRes != 0) {
            Drawable drawable = getResources().getDrawable(mLeftTvDrawableRightRes);
            drawable.setBounds(0, 0, (int) dp2px(12), (int) dp2px(12));
            tvLeftView.setCompoundDrawables(null, null, drawable, null);
            tvLeftView.setCompoundDrawablePadding((int) dp2px(4));
        }


        if (mRightTextRes != 0) {
            tvRightView.setVisibility(VISIBLE);
            tvRightView.setText(mLeftTextRes);
        }

        if (!StringUtils.isEmpty(mRightText)) {
            tvRightView.setVisibility(VISIBLE);
            tvRightView.setText(mLeftText);
        }


        if (mRightTextColorRes != 0) {
            tvRightView.setTextColor(getContext().getResources().getColor(mRightTextColorRes));
        }


        if (mRightTextColor != 0) {
            tvRightView.setTextColor(mRightTextColor);
        }


        if (mRightTextHintRes != 0) {
            tvRightView.setVisibility(VISIBLE);
            tvRightView.setHint(mRightTextHintRes);
        }


        if (!StringUtils.isEmpty(mRightTextHint)) {
            tvRightView.setVisibility(VISIBLE);
            tvRightView.setHint(mRightTextHint);
        }


        if (mRightEditRes != 0) {
            etRightView.setVisibility(VISIBLE);
            etRightView.setText(mRightEditRes);
        }

        if (!StringUtils.isEmpty(mRightEdit)) {
            etRightView.setVisibility(VISIBLE);
            etRightView.setText(mRightEdit);
        }


        if (mRightEditHintRes != 0) {
            etRightView.setVisibility(VISIBLE);
            etRightView.setHint(mRightEditHintRes);
        }


        if (!StringUtils.isEmpty(mRightEditHint)) {
            etRightView.setVisibility(VISIBLE);
            etRightView.setHint(mRightEditHint);
        }


        if (mRightTextHintColorRes != 0) {
            tvRightView.setHintTextColor(getContext().getResources().getColor(mRightTextHintColorRes));
        }


        if (mRightTextHintColor != 0) {
            tvRightView.setHintTextColor(mRightTextHintColor);
        }


        if (mRightTvDrawableRightRes != 0) {
            Drawable drawable = getResources().getDrawable(mRightTvDrawableRightRes);
            drawable.setBounds(0, 0, (int) dp2px(6), (int) dp2px(6));
            tvRightView.setCompoundDrawables(null, null, drawable, null);
            tvRightView.setCompoundDrawablePadding((int) dp2px(6));
        }

        if (mRightIconRes != 0) {
            ivRightView.setVisibility(VISIBLE);
            ivRightView.setImageResource(mRightIconRes);
        }

        etRightView.setInputType(inputType);

        lineView.setVisibility(mBottomDividerVisibility);
    }


    public AppCompatTextView getRightTextView() {
        return tvRightView;
    }

    public AppCompatTextView getLeftTextView() {
        return tvLeftView;
    }

    public AppCompatEditText getRightEditView() {
        return etRightView;
    }

    public AppCompatImageView getRightImageView() {
        return ivRightView;
    }

    /**
     * 单位转换工具类
     *
     * @param dipValue 值
     * @return 返回值
     */
    private float dp2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

}
