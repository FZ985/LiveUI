package io.live.ui;

import android.app.Application;

import io.live.ui.kit.emoji.LiveAndroidEmoji;

/**
 * by JFZ
 * 2024/4/11
 * desc：
 **/
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LiveAndroidEmoji.init(this);
    }
}
