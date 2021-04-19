package com.head.picker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 类名称：MyViewPager.java <br/>
 * 类描述：继承ViewPager并在onInterceptTouchEvent捕捉异常。
 * 因为ViewPager嵌套PhotoView使用，有时候会发生IllegalArgumentException异常。<br/>
 * 创建人：shuwen <br/>
 * 创建时间：2020-08-27 09:38 <br/>
 */
public class MyViewPager extends ViewPager
{
    
    public MyViewPager(Context context)
    {
        super(context);
    }
    
    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        try
        {
            return super.onInterceptTouchEvent(ev);
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }
}
