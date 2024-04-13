package io.live.ui.kit.entry;

import androidx.annotation.Keep;

import io.live.ui.kit.LiveType;

/**
 * by JFZ
 * 2024/4/11
 * desc：
 **/
@Keep
public class LiveUiMessage extends LiveUiBaseBean {

    private LiveMessage message;

    public LiveUiMessage(LiveMessage message) {
        this.message = message;
        change();
    }

    public LiveMessage getMessage() {
        return message;
    }

    public void setMessage(LiveMessage message) {
        this.message = message;
        change();
    }

    public int getType() {
        if (message != null) {
            return message.getLiveType();
        }
        return LiveType.None;
    }

    public long getMessageId() {
        return message != null ? message.getMessageId() : -1;
    }
}
