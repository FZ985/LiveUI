package io.uicomponents.recycleradapter;

import android.view.ViewGroup;

import java.util.List;

public interface RecyclerViewProvider<T> {
    RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

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
    void bindViewHolder(RecyclerViewHolder holder, T t, int position, List<T> list, RecyclerViewProviderListener<T> listener);
}
