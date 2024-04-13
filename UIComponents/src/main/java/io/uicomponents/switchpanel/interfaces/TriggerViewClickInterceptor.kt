package io.uicomponents.switchpanel.interfaces

interface TriggerViewClickInterceptor {
    fun intercept(triggerId: Int): Boolean
}