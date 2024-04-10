package io.live.ui.kit.adapter;

import android.view.ViewGroup;

import java.util.List;

public interface LiveViewProvider<T> {
    LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * @param item 数据
     * @return 是否是模板类型
     */
    boolean isItemViewType(T item);

    /**
     * 根据数据源绑定视图
     *
     * @param holder   视图
     * @param t        数据
     * @param position 位置
     */
    void bindViewHolder(LiveViewHolder holder, T t, int position, List<T> list, LiveViewProviderListener<T> listener);
}
