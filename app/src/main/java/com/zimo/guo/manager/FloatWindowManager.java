package com.zimo.guo.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zimo.guo.SearchActivity;
import com.zimo.guo.view.FloatWindowView;

/**
 * Created by zimo on 15/12/15.
 */
public class FloatWindowManager {

    private static FloatWindowView floatWindowView;
    private static WindowManager.LayoutParams params;
    private static WindowManager windowManager;


    private static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    public static void createFloatWindow(final Context context) {
        WindowManager windowManager = getWindowManager(context);
        if (floatWindowView == null) {
            floatWindowView = new FloatWindowView(context);
            if (params == null) {
                params = new WindowManager.LayoutParams();
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
                params.format = PixelFormat.RGBA_8888;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                params.width = FloatWindowView.viewWidth;
                params.height = FloatWindowView.viewHeight;
                params.x = 100;
                params.y = 100;
            }
            floatWindowView.setParams(params);
            windowManager.addView(floatWindowView, params);

            floatWindowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SearchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    public static void removeFloatWindow(Context context) {
        if (floatWindowView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(floatWindowView);
            floatWindowView = null;
        }
    }

    public static boolean isWindowShowing() {
        return floatWindowView != null;
    }
}
