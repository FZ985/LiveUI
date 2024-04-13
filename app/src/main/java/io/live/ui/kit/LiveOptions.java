package io.live.ui.kit;

import java.util.HashMap;
import java.util.Map;

import io.live.ui.kit.adapter.ILiveMessageProvider;
import io.live.ui.kit.adapter.provider.LiveNoneItemProvider;
import io.live.ui.kit.adapter.provider.LiveTextItemProvider;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
public class LiveOptions {

    private static final Map<Integer, ILiveMessageProvider> liveItems = new HashMap<>();

    private static final LiveNoneItemProvider defaultLiveItemProvider = new LiveNoneItemProvider();

    static {
        liveItems.put(LiveType.None, new LiveNoneItemProvider());
        liveItems.put(LiveType.Text, new LiveTextItemProvider());
    }


    public static Map<Integer, ILiveMessageProvider> getItemProvider() {
        return liveItems;
    }


    public static ILiveMessageProvider getDefaultLiveItemProvider() {
        return defaultLiveItemProvider;
    }

}
