package io.uicomponents.switchpanel

import android.R
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.uicomponents.switchpanel.interfaces.ContentScrollMeasurer
import io.uicomponents.switchpanel.interfaces.ContentScrollMeasurerBuilder
import io.uicomponents.switchpanel.interfaces.PanelHeightMeasurer
import io.uicomponents.switchpanel.interfaces.PanelHeightMeasurerBuilder
import io.uicomponents.switchpanel.interfaces.TriggerViewClickInterceptor
import io.uicomponents.switchpanel.interfaces.listener.OnEditFocusChangeListener
import io.uicomponents.switchpanel.interfaces.listener.OnEditFocusChangeListenerBuilder
import io.uicomponents.switchpanel.interfaces.listener.OnKeyboardStateListener
import io.uicomponents.switchpanel.interfaces.listener.OnKeyboardStateListenerBuilder
import io.uicomponents.switchpanel.interfaces.listener.OnPanelChangeListener
import io.uicomponents.switchpanel.interfaces.listener.OnPanelChangeListenerBuilder
import io.uicomponents.switchpanel.interfaces.listener.OnViewClickListener
import io.uicomponents.switchpanel.interfaces.listener.OnViewClickListenerBuilder
import io.uicomponents.switchpanel.log.LogTracker
import io.uicomponents.switchpanel.view.PanelSwitchLayout

/**
 * the helper of panel switching
 * Created by yummyLau on 2018-6-21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 *
 *
 * updated by yummyLau on 20/03/18
 * 重构整个输入法切换框架，移除旧版使用 weight+Runnable延迟切换，使用新版 layout+动画无缝衔接！
 */
class PanelSwitchHelper private constructor(builder: io.uicomponents.switchpanel.PanelSwitchHelper.Builder, showKeyboard: Boolean) {

    private val mPanelSwitchLayout: PanelSwitchLayout

    init {
        io.uicomponents.switchpanel.Constants.DEBUG = builder.logTrack
        if (builder.logTrack) {
            builder.viewClickListeners.add(LogTracker)
            builder.panelChangeListeners.add(LogTracker)
            builder.keyboardStatusListeners.add(LogTracker)
            builder.editFocusChangeListeners.add(LogTracker)
        }
        mPanelSwitchLayout = builder.panelSwitchLayout!!
        mPanelSwitchLayout.setTriggerViewClickInterceptor(builder.triggerViewClickInterceptor)
        mPanelSwitchLayout.setContentScrollOutsizeEnable(builder.contentScrollOutsideEnable)
        mPanelSwitchLayout.setScrollMeasurers(builder.contentScrollMeasurers)
        mPanelSwitchLayout.setPanelHeightMeasurers(builder.panelHeightMeasurers)
        mPanelSwitchLayout.bindListener(builder.viewClickListeners, builder.panelChangeListeners, builder.keyboardStatusListeners, builder.editFocusChangeListeners)
        mPanelSwitchLayout.bindWindow(builder.window, builder.windowInsetsRootView)
        if (showKeyboard) {
            mPanelSwitchLayout.toKeyboardState(true)
        }
    }

    fun addSecondaryInputView(editText: EditText) {
        mPanelSwitchLayout.getContentContainer().getInputActionImpl().addSecondaryInputView(editText)
    }

    fun removeSecondaryInputView(editText: EditText) {
        mPanelSwitchLayout.getContentContainer().getInputActionImpl().removeSecondaryInputView(editText)
    }

    fun hookSystemBackByPanelSwitcher(): Boolean {
        return mPanelSwitchLayout.hookSystemBackByPanelSwitcher()
    }

    fun isPanelState() = mPanelSwitchLayout.isPanelState()

    fun isKeyboardState() = mPanelSwitchLayout.isKeyboardState()

    fun isResetState() = mPanelSwitchLayout.isResetState()

    /**
     * 设置内容滑动
     */
    fun setContentScrollOutsideEnable(enable: Boolean) {
        mPanelSwitchLayout.setContentScrollOutsizeEnable(enable)
    }

    /**
     * 判断内容是否允许滑动
     */
    fun isContentScrollOutsizeEnable() = mPanelSwitchLayout.isContentScrollOutsizeEnable()

    /**
     * 外部显示输入框
     */
    @JvmOverloads
    fun toKeyboardState(async: Boolean = false) {
        mPanelSwitchLayout.toKeyboardState(async)
    }

    /**
     * 外部显示面板
     */
    fun toPanelState(@IdRes triggerViewId: Int) {
        mPanelSwitchLayout.findViewById<View>(triggerViewId).let {
            it.performClick()
        }
    }

    /**
     * 隐藏输入法或者面板
     */
    fun resetState() {
        mPanelSwitchLayout.checkoutPanel(io.uicomponents.switchpanel.Constants.PANEL_NONE)
    }

    class Builder(window: Window?, root: View?) {
        internal var viewClickListeners: MutableList<OnViewClickListener> = mutableListOf()
        internal var panelChangeListeners: MutableList<OnPanelChangeListener> = mutableListOf()
        internal var keyboardStatusListeners: MutableList<OnKeyboardStateListener> = mutableListOf()
        internal var editFocusChangeListeners: MutableList<OnEditFocusChangeListener> = mutableListOf()
        internal var contentScrollMeasurers: MutableList<ContentScrollMeasurer> = mutableListOf()
        internal var panelHeightMeasurers: MutableList<PanelHeightMeasurer> = mutableListOf()
        internal var triggerViewClickInterceptor: TriggerViewClickInterceptor? = null
        internal var panelSwitchLayout: PanelSwitchLayout? = null
        internal var window: Window
        internal var rootView: View
        internal var windowInsetsRootView: View? = null  // 用于Android 11以上，通过OnApplyWindowInsetsListener获取键盘高度
        internal var logTrack = false
        internal var contentScrollOutsideEnable = true


        constructor(activity: Activity) : this(activity.window, activity.window.decorView.findViewById<View>(R.id.content))
        constructor(fragment: Fragment) : this(fragment.activity?.window, fragment.view)
        constructor(dialogFragment: DialogFragment) : this(dialogFragment.activity?.window, dialogFragment.view)

        init {
            requireNotNull(window) { "PanelSwitchHelper\$Builder#build : window can't be null!please set value by call #Builder" }
            this.window = window
            requireNotNull(root) { "PanelSwitchHelper\$Builder#build : rootView can't be null!please set value by call #Builder" }
            this.rootView = root
        }

        /**
         * 用于Android 11以上，通过OnApplyWindowInsetsListener获取键盘高度
         * 当 windowInsetsRootView == null 时，会默认使用 window.decorView 作为 rootView
         */
        fun setWindowInsetsRootView(view: View) : io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            windowInsetsRootView = view
            return this
        }


        fun setTriggerViewClickInterceptor(interceptor: TriggerViewClickInterceptor): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            this.triggerViewClickInterceptor = interceptor;
            return this;
        }

        /**
         * note: helper will set view's onClickListener to View ,so you should add OnViewClickListener for your project.
         *
         * @param listener
         * @return
         */
        fun addViewClickListener(listener: OnViewClickListener): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            if (!viewClickListeners.contains(listener)) {
                viewClickListeners.add(listener)
            }
            return this
        }

        fun addViewClickListener(function: OnViewClickListenerBuilder.() -> Unit): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            viewClickListeners.add(OnViewClickListenerBuilder().also(function))
            return this
        }

        fun addPanelChangeListener(listener: OnPanelChangeListener): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            if (!panelChangeListeners.contains(listener)) {
                panelChangeListeners.add(listener)
            }
            return this
        }

        fun addPanelChangeListener(function: OnPanelChangeListenerBuilder.() -> Unit): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            panelChangeListeners.add(OnPanelChangeListenerBuilder().also(function))
            return this
        }

        fun addKeyboardStateListener(listener: OnKeyboardStateListener): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            if (!keyboardStatusListeners.contains(listener)) {
                keyboardStatusListeners.add(listener)
            }
            return this
        }

        fun addKeyboardStateListener(function: OnKeyboardStateListenerBuilder.() -> Unit): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            keyboardStatusListeners.add(OnKeyboardStateListenerBuilder().also(function))
            return this
        }

        fun addEditTextFocusChangeListener(listener: OnEditFocusChangeListener): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            if (!editFocusChangeListeners.contains(listener)) {
                editFocusChangeListeners.add(listener)
            }
            return this
        }

        fun addEditTextFocusChangeListener(function: OnEditFocusChangeListenerBuilder.() -> Unit): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            editFocusChangeListeners.add(OnEditFocusChangeListenerBuilder().also(function))
            return this
        }

        fun addContentScrollMeasurer(function: ContentScrollMeasurerBuilder.() -> Unit): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            contentScrollMeasurers.add(ContentScrollMeasurerBuilder().also(function))
            return this
        }

        fun addContentScrollMeasurer(scrollMeasurer: ContentScrollMeasurer): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            if (!contentScrollMeasurers.contains(scrollMeasurer)) {
                contentScrollMeasurers.add(scrollMeasurer)
            }
            return this
        }

        fun addPanelHeightMeasurer(function: PanelHeightMeasurerBuilder.() -> Unit): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            panelHeightMeasurers.add(PanelHeightMeasurerBuilder().also(function))
            return this
        }

        fun addPanelHeightMeasurer(panelHeightMeasurer: PanelHeightMeasurer): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            if (!panelHeightMeasurers.contains(panelHeightMeasurer)) {
                panelHeightMeasurers.add(panelHeightMeasurer)
            }
            return this
        }

        fun contentScrollOutsideEnable(contentScrollOutsideEnable: Boolean): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            this.contentScrollOutsideEnable = contentScrollOutsideEnable
            return this
        }

        fun logTrack(logTrack: Boolean): io.uicomponents.switchpanel.PanelSwitchHelper.Builder {
            this.logTrack = logTrack
            return this
        }

        @JvmOverloads
        fun build(showKeyboard: Boolean = false): io.uicomponents.switchpanel.PanelSwitchHelper {
            findSwitchLayout(rootView)
            requireNotNull(panelSwitchLayout) { "PanelSwitchHelper\$Builder#build : not found PanelSwitchLayout!" }
            return io.uicomponents.switchpanel.PanelSwitchHelper(this, showKeyboard).also {
                panelSwitchLayout?.tryBindKeyboardChangedListener()
            }
        }

        private fun findSwitchLayout(view: View) {
            if (view is PanelSwitchLayout) {
                require(panelSwitchLayout == null) { "PanelSwitchHelper\$Builder#build : rootView has one more panelSwitchLayout!" }
                panelSwitchLayout = view
                return
            }
            if (view is ViewGroup) {
                val childCount = view.childCount
                for (i in 0 until childCount) {
                    findSwitchLayout(view.getChildAt(i))
                }
            }
        }
    }
}