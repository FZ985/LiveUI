package io.uicomponents.switchpanel.interfaces

/**
 * --------------------
 * | PanelSwitchLayout  |
 * |  ----------------  |
 * | |                | |
 * | |ContentContainer| |
 * | |                | |
 * |  ----------------  |
 * |  ----------------  |
 * | | PanelContainer | |
 * |  ----------------  |
 * --------------------
 * There are some rules that must be processed:
 *
 * 1. [io.live.ui.kit.widgets.components.switchpanel.view.PanelSwitchLayout] must have only two children
 * [io.live.ui.kit.widgets.components.switchpanel.view.content.IContentContainer] and [io.live.ui.kit.widgets.components.switchpanel.view.PanelContainer]
 *
 * 2. [io.live.ui.kit.widgets.components.switchpanel.view.content.IContentContainer] must set "edit_view" value to provide [android.widget.EditText]
 *
 * 3. [io.live.ui.kit.widgets.components.switchpanel.view.PanelContainer] has some Children that are [io.live.ui.kit.widgets.components.switchpanel.view.PanelView]
 * [io.live.ui.kit.widgets.components.switchpanel.view.PanelView] must set "panel_layout" value to provide panelView and set "panel_trigger"  value to
 * specify layout for click to checkout panelView
 *
 * Created by yummyLau on 18-7-10
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
interface ViewAssertion {
    fun assertView()
}