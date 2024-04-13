package io.live.ui.kit

import android.content.res.Resources
import io.live.ui.kit.span.LiveSpan


/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
val Number.dp
    get() = (this.toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()


fun String.addLiveSpan(span: Any): LiveSpan.Builder = LiveSpan.build(this).addSpan(span)