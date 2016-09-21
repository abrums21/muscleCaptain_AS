package com.chenglong.muscle.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenglong.muscle.R;

/**
 * Created by 20 on 2016/9/18.
 */
public class MyToolBar extends Toolbar implements View.OnClickListener {

    private Context mContext;

    //添加布局必不可少的工具
    private LayoutInflater mInflater;

    //左标题
    private TextView mLeftTextTitle;

    //右标题
    private TextView mRightTextTitle;

    //左边按钮
    private ImageView mLeftButton;

    private View mView= null;

    public MyToolBar(Context mContext)
    {
        this(mContext, null);
    }

    public MyToolBar(Context mContext, AttributeSet attrs)
    {
        this(mContext, attrs, 0);
    }

    public MyToolBar(Context mContext, AttributeSet attrs, int defStyle)
    {
        super(mContext, attrs, defStyle);

        initView();

        //Set the content insets for this toolbar relative to layout direction.
//        setContentInsetsRelative(10, 10);
        //setContentInsetsRelative(0, 0);

        if (attrs != null) {
            //读写自定义的属性，如果不会自己写的时候，就看看官方文档是怎么写的哈
            /**
             * 下面是摘自官方文档
             * final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
             R.styleable.Toolbar, defStyleAttr, 0);

             mTitleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
             mSubtitleTextAppearance = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
             mGravity = a.getInteger(R.styleable.Toolbar_android_gravity, mGravity);
             mButtonGravity = Gravity.TOP;
             mTitleMarginStart = mTitleMarginEnd = mTitleMarginTop = mTitleMarginBottom =
             a.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, 0);

             final int marginStart = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
             if (marginStart >= 0) {
             mTitleMarginStart = marginStart;
             }

             *
             */
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyle, 0);

            final Drawable leftIcon = a.getDrawable(R.styleable.MyToolBar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }

            final String leftText = a.getString(R.styleable.MyToolBar_leftTextTitle);
            if (leftText != null)
            {
                setLeftTextTitle(leftText);
            }

            final String rightText = a.getString(R.styleable.MyToolBar_rightTextTitle);
            {
                setRightTextTitle(rightText);
            }

            //资源的回收
            a.recycle();
        }

    }

    private void initView() {

        if (mView == null) {
            //初始化
            mInflater = LayoutInflater.from(getContext());
            //添加布局文件
            mView = mInflater.inflate(R.layout.mytoolbar, null);

            //绑定控件
            mLeftButton = (ImageView) mView.findViewById(R.id.mytoolbar_leftButton);
            mLeftTextTitle = (TextView) mView.findViewById(R.id.mytoolbar_leftText);
            mRightTextTitle = (TextView) mView.findViewById(R.id.mytoolbar_rightText);
            mLeftButton.setOnClickListener(this);

            //然后使用LayoutParams把控件添加到子view中
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }

    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    public void setLeftTextTitle(CharSequence title) {
//        initView();
        if (mLeftTextTitle != null) {
            mLeftTextTitle.setText(title);
        }
    }

    public void setRightTextTitle(CharSequence title) {
//        initView();
        if (mRightTextTitle != null) {
            mRightTextTitle.setText(title);
        }
    }

    //给左侧按钮设置图片，也可以在布局文件中直接引入
    public void setLeftButtonIcon(Drawable icon) {
        if (mLeftButton != null){
            mLeftButton.setImageDrawable(icon);
//            mLeftButton.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();
        switch(resId)
        {
            case R.id.mytoolbar_leftButton:
            {
                ((Activity)getContext()).finish();
            }
        }
    }

    //设置左侧按钮监听事件
//    public void setLeftButtonOnClickLinster(OnClickListener listener) {
//        mLeftButton.setOnClickListener(listener);
//    }

}
