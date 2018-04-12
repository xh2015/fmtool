package com.fm.tool.scan.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/4/12 下午2:30
 */
public class FMIconfontView extends AppCompatTextView {


    private Typeface __cachedTypeFace;

    public FMIconfontView(Context context) {
        super(context);
        initFont();
    }

    public FMIconfontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public FMIconfontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    private void initFont() {
        if (__cachedTypeFace == null) {
            final Typeface iconfont = Typeface.createFromAsset(getContext().getAssets(), "iconfontscan.ttf");
            __cachedTypeFace = iconfont;
        }
        setTypeface(__cachedTypeFace);
    }
}
