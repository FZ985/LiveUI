package io.live.ui.kit.entry.body;

import org.json.JSONObject;

import io.live.ui.kit.entry.LiveBody;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
public class LiveNoneBody extends LiveBody {
    @Override
    public LiveBody parseBody(JSONObject obj) {
        return this;
    }
}
