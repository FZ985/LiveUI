package io.uicomponents.recycleradapter;

import android.view.View;

public interface RecyclerViewProviderListener<T> {

    /**
     * @param clickType 区分点击事件的标记位
     * @param data      传递的数据源
     */
    void onViewClick(View view, int clickType, T data);

    /**
     * @param clickType 区分点击事件的标记位
     * @param data      传递的数据源
     */
    boolean onViewLongClick(View view, int clickType, T data);
}
