package com.zimo.guo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zimo.guo.R;
import com.zimo.guo.util.TaskUtils;

/**
 * Created by zimo on 15/12/15.
 */
public class FloatWindowView extends LinearLayout {

    private ImageView search;
    private LinearLayout layout;
    private View view;
    private WindowManager windowManager;
    public static int viewWidth, viewHeight;
    private int startX, startY;
    private int endX, endY;
    private int statusBarHeight;

    private WindowManager.LayoutParams mParams;

    private OnClickListener onClickListener;

    public FloatWindowView(Context context) {
        super(context);
        initView(context);
    }

    public FloatWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.layout_float_window, this);
        layout = (LinearLayout) findViewById(R.id.layout_float_win);
        viewWidth = layout.getLayoutParams().width;
        viewHeight = layout.getLayoutParams().height;
        search = (ImageView) findViewById(R.id.img_float_window);

        statusBarHeight = TaskUtils.getStatusBarHeight(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mParams.x = (int) (event.getRawX() - viewWidth / 2);
                mParams.y = (int) (event.getRawY() - viewHeight / 2 + 25);
                windowManager.updateViewLayout(this, mParams);
                break;
            case MotionEvent.ACTION_UP:
                endX = (int) event.getRawX();
                endY = (int) event.getRawY();
                if ((startX == endX) && (startY == endY)) {
                    onClickListener.onClick(search);
                }
                break;
        }
        return true;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setParams(WindowManager.LayoutParams mParams) {
        this.mParams = mParams;
    }
}
