package io.uicomponents.switchpanel.log

import android.text.TextUtils
import android.util.Log
import android.view.View
import io.uicomponents.switchpanel.interfaces.listener.OnEditFocusChangeListener
import io.uicomponents.switchpanel.interfaces.listener.OnKeyboardStateListener
import io.uicomponents.switchpanel.interfaces.listener.OnPanelChangeListener
import io.uicomponents.switchpanel.interfaces.listener.OnViewClickListener
import io.uicomponents.switchpanel.view.panel.IPanelView

/**
 * single logTracker
 * Created by yummyLau on 18-7-07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
object LogTracker : OnEditFocusChangeListener, OnKeyboardStateListener, OnPanelChangeListener,
    OnViewClickListener {

    private val TAG = LogTracker::class.java.simpleName

    @JvmStatic
    fun log(methodName: String, message: String) {
        if (TextUtils.isEmpty(methodName) || TextUtils.isEmpty(message)) {
            return
        }
        if (io.uicomponents.switchpanel.Constants.DEBUG) {
            Log.d(TAG, "$methodName => $message")
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        log("OnEditFocusChangeListener#onFocusChange", "EditText has focus ( $hasFocus )")
    }

    override fun onKeyboardChange(show: Boolean,height: Int) {
        log("OnKeyboardStateListener#onKeyboardChange", "Keyboard is showing ( $show ),height is $height")
    }

    override fun onKeyboard() {
        log("OnPanelChangeListener#onKeyboard", "panel： keyboard")
    }

    override fun onNone() {
        log("OnPanelChangeListener#onNone", "panel： none")
    }

    override fun onPanel(panel: IPanelView?) {
        log("OnPanelChangeListener#onPanel", "panel：" + (panel?.toString()
                ?: "null"))
    }

    override fun onPanelSizeChange(panel: IPanelView?, portrait: Boolean, oldWidth: Int, oldHeight: Int, width: Int, height: Int) {
        log("OnPanelChangeListener#onPanelSizeChange", "panelView is " + (panel?.toString()
                ?: "null" +
                " portrait : " + portrait +
                " oldWidth : " + oldWidth + " oldHeight : " + oldHeight +
                " width : " + width + " height : " + height))
    }

    override fun onClickBefore(view: View?) {
        log("OnViewClickListener#onViewClick", "view is " + (view?.toString()
                ?: " null "))
    }
}