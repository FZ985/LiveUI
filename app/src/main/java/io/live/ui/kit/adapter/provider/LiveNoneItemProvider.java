package io.live.ui.kit.adapter.provider;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import io.live.ui.databinding.LiveItemNoneBinding;
import io.live.ui.kit.entry.LiveUiMessage;
import io.live.ui.kit.entry.body.LiveNoneBody;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
public class LiveNoneItemProvider extends LiveBaseItemProvider<LiveNoneBody, LiveItemNoneBinding> {

    @Override
    protected void onBindData(LiveMessageViewHolder<LiveItemNoneBinding> holder, LiveItemNoneBinding binding, LiveNoneBody body, LiveUiMessage uiMessage, int position, List<LiveUiMessage> list) {

    }

    @Override
    public LiveItemNoneBinding getBinding(@NonNull LayoutInflater inflater, ViewGroup parent) {
        return LiveItemNoneBinding.inflate(inflater, parent, false);
    }
}
