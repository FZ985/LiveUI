package io.live.ui.kit.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.live.ui.kit.utils.LiveLog;


/**
 * RecyclerView官方的BUG，继承封装LinearLayoutManager类，重写onLayoutChildren()方法，try-catch捕获该异常
 */
public class LiveFixedLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = LiveFixedLinearLayoutManager.class.getSimpleName();

    public LiveFixedLinearLayoutManager(Context context) {
        super(context);
    }

    public LiveFixedLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LiveFixedLinearLayoutManager(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            LiveLog.e(TAG, e.getMessage());
        }
    }
}
