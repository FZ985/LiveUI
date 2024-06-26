package io.uicomponents.recycleradapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final String TAG = RecyclerBaseAdapter.class.getSimpleName();
    protected RecyclerViewProviderListener<T> mListener;
    protected List<T> mDataList = new ArrayList<>();
    protected OnItemClickListener mOnItemClickListener;
    protected RecyclerProviderManager<T> mProviderManager = new RecyclerProviderManager<>();
    private final int EMPTY_ITEM_VIEW_TYPE = -200;
    private static final int BASE_ITEM_TYPE_HEADER = -300;
    private static final int BASE_ITEM_TYPE_FOOTER = -400;
    private View mEmptyView;
    private @LayoutRes int mEmptyId;
    private final SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private final SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public RecyclerBaseAdapter() {
    }

    public RecyclerBaseAdapter(RecyclerViewProviderListener<T> listener, RecyclerProviderManager<T> providerManager) {
        mListener = listener;
        mProviderManager = providerManager;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(
                    position - (getHeadersCount() + (isEmpty() ? 1 : getRealItemCount())));
        } else if (isEmpty()) {
            return EMPTY_ITEM_VIEW_TYPE;
        } else {
            int listPosition = position - getHeadersCount();
            // 没有空布局返回真实数值
            if (mProviderManager != null) {
                return mProviderManager.getItemViewType(mDataList.get(listPosition), listPosition);
            } else {
                throw new IllegalArgumentException("adapter did not set providerManager");
            }
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
        } else if (viewType == EMPTY_ITEM_VIEW_TYPE) {
            RecyclerViewHolder holder;
            if (mEmptyView != null) {
                holder = RecyclerViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = RecyclerViewHolder.createViewHolder(parent.getContext(), parent, mEmptyId);
            }
            return holder;
        } else {
            RecyclerViewProvider<T> provider = mProviderManager.getProvider(viewType);
            return provider.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        } else if (isFooterViewPos(position)) {
            return;
        } else if (isEmpty()) {
            return;
        } else {
            final int listPosition = position - getHeadersCount();
            RecyclerViewProvider<T> provider = mProviderManager.getProvider(mDataList.get(listPosition));
            provider.bindViewHolder(
                    holder, mDataList.get(listPosition), listPosition, mDataList, mListener);
            holder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder, listPosition);
                }
            });
            holder.itemView.setOnLongClickListener(v -> {
                if (mOnItemClickListener != null) {
                    return mOnItemClickListener.onItemLongClick(
                            v, holder, listPosition);
                }
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) {
            return getHeadersCount() + getFootersCount() + 1;
        } else {
            // 没有空布局返回真实数值
            return getHeadersCount() + getFootersCount() + getRealItemCount();
        }
    }

    public void setDataCollection(List<T> data) {
        if (data != null) {
            this.mDataList.clear();
            this.mDataList.addAll(data);
        }
    }

    public void add(T t) {
        mDataList.add(t);
    }

    public void remove(T t) {
        mDataList.remove(t);
    }

    public List<T> getData() {
        return mDataList;
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerViewHolder holder, int position);
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + (isEmpty() ? 1 : getRealItemCount());
    }

    protected boolean isEmpty() {
        return (mEmptyView != null || mEmptyId != 0) && getRealItemCount() == 0;
    }


    public int addHeaderView(View view) {
        int viewType = mHeaderViews.size() + BASE_ITEM_TYPE_HEADER;
        mHeaderViews.put(viewType, view);
        return viewType;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeHeaderView(int viewType) {
        if (mHeaderViews.containsKey(viewType)) {
            mHeaderViews.remove(viewType);
            notifyDataSetChanged();
        }
    }

    public int addFootView(View view) {
        int viewType = mFootViews.size() + BASE_ITEM_TYPE_FOOTER;
        mFootViews.put(viewType, view);
        return viewType;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeFooterView(int viewType) {
        if (mFootViews.containsKey(viewType)) {
            mFootViews.remove(viewType);
            notifyDataSetChanged();
        }
    }

    public void setEmptyView(View view) {
        mEmptyView = view;
    }

    public void setEmptyView(@LayoutRes int emptyId) {
        mEmptyId = emptyId;
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

    private int getRealItemCount() {
        return mDataList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerWrapperUtils.onAttachedToRecyclerView(
                this,
                recyclerView,
                new RecyclerWrapperUtils.SpanSizeCallback() {
                    @Override
                    public int getSpanSize(
                            GridLayoutManager layoutManager,
                            GridLayoutManager.SpanSizeLookup oldLookup,
                            int position) {
                        int viewType = getItemViewType(position);
                        if (mHeaderViews.get(viewType) != null) {
                            return layoutManager.getSpanCount();
                        } else if (mFootViews.get(viewType) != null) {
                            return layoutManager.getSpanCount();
                        } else if (isEmpty()) {
                            return layoutManager.getSpanCount();
                        }
                        if (oldLookup != null) {
                            return oldLookup.getSpanSize(position);
                        }
                        return 1;
                    }
                });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            RecyclerWrapperUtils.setFullSpan(holder);
        } else if (isEmpty()) {
            RecyclerWrapperUtils.setFullSpan(holder);
        }
    }
}
