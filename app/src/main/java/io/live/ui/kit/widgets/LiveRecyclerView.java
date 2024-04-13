package io.live.ui.kit.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * by JFZ
 * 2024/4/10
 * desc：
 **/
public class LiveRecyclerView extends RecyclerView {
    public LiveRecyclerView(@NonNull Context context) {
        super(context);
    }

    public LiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected float getBottomFadingEdgeStrength() {
        return 0;
    }
}
