package com.zimo.guo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zimo.guo.manager.FloatWindowManager;
import com.zimo.guo.util.TaskUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zimo on 15/12/15.
 */
public class FloatWindowOptimizationService extends Service {

    private Handler handler = new Handler();

    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class RefreshTask extends TimerTask {
        @Override
        public void run() {

            if ((TaskUtils.isHome(FloatWindowOptimizationService.this) || !TaskUtils.isApplicationBroughtToBackground(FloatWindowOptimizationService.this))
                    && !FloatWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FloatWindowManager.createFloatWindow(getApplicationContext());
                    }
                });
            } else if (!TaskUtils.isHome(FloatWindowOptimizationService.this)
                    && TaskUtils.isApplicationBroughtToBackground(FloatWindowOptimizationService.this)
                    && FloatWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FloatWindowManager.removeFloatWindow(getApplicationContext());
                    }
                });

            }

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
        if (FloatWindowManager.isWindowShowing()) {
            FloatWindowManager.removeFloatWindow(getApplicationContext());
        }
    }
}
