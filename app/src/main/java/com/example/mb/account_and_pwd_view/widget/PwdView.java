package com.example.mb.account_and_pwd_view.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.mb.account_and_pwd_view.R;

public class PwdView extends EditText implements TextWatcher, View.OnFocusChangeListener {
    //隐藏密码
    private Drawable mInvisibleDrawable;
    //显示密码
    private Drawable mVisibleDrawable;
    //控件是否有焦点
    private boolean isFocus;
    private boolean isPwdVisible = false;


    public PwdView(Context context) {
        this(context, null);
    }

    public PwdView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PwdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //获取EditText的DrawableRight，没有设置就用我们自己默认的图片
        mInvisibleDrawable = getCompoundDrawables()[2];//存放着四个元素，分别是左，上，右，下，所以右边的图片就是2
        mVisibleDrawable = getCompoundDrawables()[2];
        if (mInvisibleDrawable == null) {
            mInvisibleDrawable = getResources().getDrawable(R.mipmap.login_icon_invisible);
        }
        if (mVisibleDrawable == null) {
            mVisibleDrawable = getResources().getDrawable(R.mipmap.login_icon_visible);
        }
        mInvisibleDrawable.setBounds(0, 0, mInvisibleDrawable.getIntrinsicWidth(), mInvisibleDrawable.getIntrinsicHeight());
        mVisibleDrawable.setBounds(0, 0, mVisibleDrawable.getIntrinsicWidth(), mVisibleDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setPwdVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight() - 10) && (event.getX() < ((getWidth() - getPaddingRight()) + 10));
                if (touchable) {
                    setPwdVisible(!isPwdVisible);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setPwdVisible(boolean visible) {
        Drawable right = visible ? mVisibleDrawable : mInvisibleDrawable;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        this.setTransformationMethod(visible ? (HideReturnsTransformationMethod.getInstance()) : (PasswordTransformationMethod.getInstance()));
        isPwdVisible = visible;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocus) {
            setPwdVisible(isPwdVisible);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
