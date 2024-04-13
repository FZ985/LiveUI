package io.live.ui.kit.entry;

import androidx.annotation.Keep;

import java.util.Random;

import io.live.ui.kit.LiveType;

/**
 * by JFZ
 * 2024/4/11
 * desc：
 **/
@Keep
public class LiveMessage {
    private long messageId;

    private int liveType = LiveType.None;

    private LiveBody liveBody;


    public LiveMessage() {
        messageId = newMessageId();
    }

    public static LiveMessage obtain(int liveType, LiveBody body) {
        LiveMessage message = new LiveMessage();
        message.setLiveType(liveType);
        message.setLiveBody(body);
        return message;
    }


    public int getLiveType() {
        return liveType;
    }

    public void setLiveType(int liveType) {
        this.liveType = liveType;
    }

    public LiveBody getLiveBody() {
        return liveBody;
    }

    public void setLiveBody(LiveBody body) {
        this.liveBody = body;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    private long newMessageId() {
        return System.currentTimeMillis() + (new Random().nextInt(999 - 10) + 10);
    }
}
