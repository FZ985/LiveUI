package io.uicomponents.switchpanel.interfaces.listener

import android.view.View

/**
 * preventing listeners that [io.live.ui.kit.widgets.components.switchpanel.PanelSwitchHelper] set these to view from being overwritten
 * Created by yummyLau on 18-7-07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 * update 2020/05/08 支持 dsl
 */
interface OnViewClickListener {
    fun onClickBefore(view: View?)
}

private typealias OnClickBefore = (view: View?) -> Unit

class OnViewClickListenerBuilder : OnViewClickListener {

    private var onClickBefore: OnClickBefore? = null

    override fun onClickBefore(view: View?) {
        onClickBefore?.invoke(view)
    }

    fun onClickBefore(onClickBefore: OnClickBefore) {
        this.onClickBefore = onClickBefore
    }
}