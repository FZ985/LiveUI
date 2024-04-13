package io.live.ui.kit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.viewholder.QuickViewHolder;

import java.util.List;

import io.live.ui.kit.LiveOptions;
import io.live.ui.kit.entry.LiveUiMessage;

/**
 * by JFZ
 * 2024/4/11
 * desc：
 **/
public class LiveMessageDiffAdapter extends BaseQuickAdapter<LiveUiMessage, LiveMessageDiffAdapter.LiveViewHolder> {

    @Override
    protected void onBindViewHolder(@NonNull LiveViewHolder quickViewHolder, int position, @Nullable LiveUiMessage liveUiMessage) {
        int itemViewType = getItemViewType(position, getItems());
        ILiveMessageProvider provider = LiveOptions.getItemProvider().get(itemViewType);
        if (provider != null) {
            provider.onBindViewHolder(this, quickViewHolder, position, liveUiMessage, getItems());
        } else {
            LiveOptions.getDefaultLiveItemProvider().onBindViewHolder(this, quickViewHolder, position, liveUiMessage, getItems());
        }
    }

    @NonNull
    @Override
    protected LiveViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int viewType) {
        ILiveMessageProvider provider = LiveOptions.getItemProvider().get(viewType);
        if (provider != null) {
            return provider.onCreateViewHolder(context, viewGroup, viewType);
        }
        return LiveOptions.getDefaultLiveItemProvider().onCreateViewHolder(context, viewGroup, viewType);
    }

    @Override
    protected int getItemViewType(int position, @NonNull List<? extends LiveUiMessage> list) {
        return list.get(position).getType();
    }

    public static class LiveViewHolder extends QuickViewHolder {

        public Context context;

        public LiveViewHolder(Context context, @NonNull View view) {
            super(view);
            this.context = context;
        }
    }
}

