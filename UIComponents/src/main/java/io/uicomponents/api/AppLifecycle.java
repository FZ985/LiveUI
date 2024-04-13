package io.uicomponents.api;

import android.content.Intent;

/**
 * author : JFZ
 * date : 2023/12/9 13:59
 * description :
 */
public interface AppLifecycle {

    default void onStart() {

    }

    default void onResume() {
    }

    default void onPause() {
    }

    default void onStop() {
    }

    default void onDestroy() {
    }

    default boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }
}
