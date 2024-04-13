package io.live.ui.kit.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import io.live.ui.kit.entry.LiveUiMessage;

/**
 * by JFZ
 * 2024/4/12
 * desc：扩展 多类型布局
 **/
public interface ILiveMessageProvider<VH extends LiveMessageDiffAdapter.LiveViewHolder> {

    VH onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int viewType);

    void onBindViewHolder(@NonNull LiveMessageDiffAdapter adapter,@NonNull VH holder, int position, @Nullable LiveUiMessage liveUiMessage, List<LiveUiMessage> list);


}
