package io.live.ui.kit.adapter.provider;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import io.live.ui.databinding.LiveItemTextBinding;
import io.live.ui.kit.entry.LiveUiMessage;
import io.live.ui.kit.entry.body.LiveTextBody;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
public class LiveTextItemProvider extends LiveBaseItemProvider<LiveTextBody, LiveItemTextBinding> {
    @Override
    protected void onBindData(LiveMessageViewHolder<LiveItemTextBinding> holder, LiveItemTextBinding binding, LiveTextBody body, LiveUiMessage uiMessage, int position, List<LiveUiMessage> list) {
        binding.itemText.setText(body.getContent());
    }

    @Override
    public LiveItemTextBinding getBinding(@NonNull LayoutInflater inflater, ViewGroup parent) {
        return LiveItemTextBinding.inflate(inflater, parent, false);
    }
}
