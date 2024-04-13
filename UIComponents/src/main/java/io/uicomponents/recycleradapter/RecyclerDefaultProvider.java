package io.uicomponents.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.uicomponents.R;


public class RecyclerDefaultProvider implements RecyclerViewProvider {
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_provider_item_msg_default, parent, false);
        return new RecyclerViewHolder(view.getContext(), view);
    }

    @Override
    public boolean isItemViewType(Object item) {
        return true;
    }

    @Override
    public void bindViewHolder(RecyclerViewHolder holder, Object o, int position, List list, RecyclerViewProviderListener listener) {
        // do nothing
    }
}
