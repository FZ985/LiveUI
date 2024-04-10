package io.live.ui.kit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.live.ui.R;


public class LiveDefaultProvider implements LiveViewProvider {
    @Override
    public LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_item_msg_default, parent, false);
        return new LiveViewHolder(view.getContext(), view);
    }

    @Override
    public boolean isItemViewType(Object item) {
        return true;
    }

    @Override
    public void bindViewHolder(LiveViewHolder holder, Object o, int position, List list, LiveViewProviderListener listener) {
        // do nothing
    }
}
