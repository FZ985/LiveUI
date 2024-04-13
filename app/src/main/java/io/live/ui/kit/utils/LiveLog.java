package io.live.ui.kit.utils;

import android.util.Log;

/**
 * by JFZ
 * 2024/4/11
 * desc：
 **/
public class LiveLog {

    public static void e(String m) {
        e("LiveLog", m);
    }

    public static void e(String tag, String m) {
        Log.e(tag, m);
    }
}
