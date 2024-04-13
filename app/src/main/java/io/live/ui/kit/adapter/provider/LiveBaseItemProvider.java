package io.live.ui.kit.adapter.provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import io.live.ui.kit.adapter.ILiveMessageProvider;
import io.live.ui.kit.adapter.LiveMessageDiffAdapter;
import io.live.ui.kit.entry.LiveBody;
import io.live.ui.kit.entry.LiveUiMessage;
import io.live.ui.kit.utils.LiveLog;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
public abstract class LiveBaseItemProvider<T extends LiveBody, VB extends ViewBinding> implements ILiveMessageProvider<LiveBaseItemProvider.LiveMessageViewHolder<VB>> {
    @Override
    public final LiveMessageViewHolder<VB> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int viewType) {
        return new LiveBaseItemProvider.LiveMessageViewHolder<>(context, getBinding(LayoutInflater.from(context), viewGroup));
    }

    @Override
    public final void onBindViewHolder(@NonNull LiveMessageDiffAdapter adapter, @NonNull LiveMessageViewHolder<VB> holder, int position, @Nullable LiveUiMessage uiMessage, List<LiveUiMessage> list) {
        try {
            if (uiMessage != null && uiMessage.getMessage() != null) {
                T body = (T) uiMessage.getMessage().getLiveBody();
                if (body != null) {
                    onBindData(holder, holder.binding, body, uiMessage, position, list);
                }
                uiMessage.setChange(false);
            }
        } catch (ClassCastException e) {
            LiveLog.e("bindViewHolder Message cast Exception, e:" + e.getMessage());
        }
    }

    protected abstract void onBindData(LiveMessageViewHolder<VB> holder, VB binding, T body, LiveUiMessage uiMessage, int position, List<LiveUiMessage> list);


    public abstract VB getBinding(@NonNull LayoutInflater inflater, ViewGroup parent);

    public static class LiveMessageViewHolder<VB extends ViewBinding> extends LiveMessageDiffAdapter.LiveViewHolder {

        public VB binding;

        public LiveMessageViewHolder(Context context, VB vb) {
            super(context, vb.getRoot());
            this.binding = vb;
        }
    }
}
