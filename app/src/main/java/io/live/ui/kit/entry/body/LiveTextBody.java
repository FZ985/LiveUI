package io.live.ui.kit.entry.body;

import androidx.annotation.Keep;

import org.json.JSONObject;

import io.live.ui.kit.entry.LiveBody;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
@Keep
public class LiveTextBody extends LiveBody {

    private String content;

    public static LiveTextBody obtain(String text) {
        LiveTextBody body = new LiveTextBody();
        body.setContent(text);
        return body;
    }

    public String getContent() {
        return compatString(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public LiveBody parseBody(JSONObject obj) {
        return this;
    }
}
