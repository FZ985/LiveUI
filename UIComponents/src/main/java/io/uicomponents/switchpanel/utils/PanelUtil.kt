package io.uicomponents.switchpanel.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.uicomponents.switchpanel.log.LogTracker
import io.uicomponents.switchpanel.utils.DisplayUtil.dip2px
import io.uicomponents.switchpanel.utils.DisplayUtil.isPortrait

/**
 * panel helper
 * Created by yummyLau on 18-7-07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
object PanelUtil {

    private var pHeight: Int = -1
    private var lHeight: Int = -1

    @JvmStatic
    fun clearData(context: Context) {
        pHeight = -1
        lHeight = -1
        val sp = context.getSharedPreferences(io.uicomponents.switchpanel.Constants.KB_PANEL_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
    }

    @JvmStatic
    fun showKeyboard(context: Context, view: View): Boolean {
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        val mInputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return mInputManager.showSoftInput(view, 0)
    }

    @JvmStatic
    fun hideKeyboard(context: Context, view: View): Boolean {
        val mInputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return mInputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
    fun getKeyBoardHeight(context: Context): Int {
        val isPortrait = isPortrait(context)
        if (isPortrait && pHeight != -1) {
            return pHeight
        }
        if (!isPortrait && lHeight != -1) {
            return lHeight
        }
        val sp = context.getSharedPreferences(io.uicomponents.switchpanel.Constants.KB_PANEL_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val key = if (isPortrait) io.uicomponents.switchpanel.Constants.KEYBOARD_HEIGHT_FOR_P else io.uicomponents.switchpanel.Constants.KEYBOARD_HEIGHT_FOR_L
        val defaultHeight = dip2px(context, if (isPortrait) io.uicomponents.switchpanel.Constants.DEFAULT_KEYBOARD_HEIGHT_FOR_P else io.uicomponents.switchpanel.Constants.DEFAULT_KEYBOARD_HEIGHT_FOR_L)
        val result = sp.getInt(key, defaultHeight)
        if (result != defaultHeight) {
            if (isPortrait) {
                pHeight = result
            } else {
                lHeight = result
            }
        }
        return result
    }

    @JvmStatic
    fun isPanelHeightBelowKeyboardHeight(context: Context, curPanelHeight: Int): Boolean = hasMeasuredKeyboard(context) && getKeyBoardHeight(context) > curPanelHeight

    @JvmStatic
    fun setKeyBoardHeight(context: Context, height: Int): Boolean {

        if (getKeyBoardHeight(context) == height) {
            LogTracker.log("PanelUtil#onGlobalLayout", "current KeyBoardHeight is equal，just ignore！")
            return false
        }
        val isPortrait = isPortrait(context)
        if (isPortrait && pHeight == height) {
            LogTracker.log("PanelUtil#onGlobalLayout", "current KeyBoardHeight is equal，just ignore！")
            return false
        }
        if (!isPortrait && lHeight == height) {
            LogTracker.log("PanelUtil#onGlobalLayout", "current KeyBoardHeight is equal，just ignore！")
            return false
        }
        val sp = context.getSharedPreferences(io.uicomponents.switchpanel.Constants.KB_PANEL_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val key = if (isPortrait) io.uicomponents.switchpanel.Constants.KEYBOARD_HEIGHT_FOR_P else io.uicomponents.switchpanel.Constants.KEYBOARD_HEIGHT_FOR_L
        val result = sp.edit().putInt(key, height).commit()
        if (result) {
            if (isPortrait) {
                pHeight = height
            } else {
                lHeight = height
            }
        }
        return result
    }

    internal fun hasMeasuredKeyboard(context: Context): Boolean {
        getKeyBoardHeight(context)
        return pHeight != -1 || lHeight != -1
    }
}