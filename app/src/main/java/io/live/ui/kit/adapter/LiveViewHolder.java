package io.live.ui.kit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class LiveViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private final View mConvertView;
    private final Context mContext;

    public LiveViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static LiveViewHolder createViewHolder(Context context, View itemView) {
        LiveViewHolder holder = new LiveViewHolder(context, itemView);
        return holder;
    }

    public static LiveViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return createViewHolder(context, itemView);
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public LiveViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv == null) {
            return this;
        }
        tv.setText(text);
        return this;
    }

    public LiveViewHolder setText(int viewId, Spannable text) {
        TextView tv = getView(viewId);
        if (tv == null) {
            return this;
        }
        tv.setText(text);
        return this;
    }

    public LiveViewHolder setText(int viewId, CharSequence text, TextView.BufferType type) {
        TextView tv = getView(viewId);
        if (tv == null) {
            return this;
        }
        tv.setText(text, type);
        return this;
    }

    public LiveViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        if (view == null) {
            return this;
        }
        try {
            view.setImageResource(resId);
        } catch (Exception e) {
            Log.e("LiveViewHolder", e.toString());
        }
        return this;
    }

    public LiveViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setImageBitmap(bitmap);
        return this;
    }

    public LiveViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setImageDrawable(drawable);
        return this;
    }

    public LiveViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setBackgroundColor(color);
        return this;
    }

    public LiveViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public LiveViewHolder setBackgroundDrawable(int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setBackground(drawable);
        return this;
    }

    public LiveViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTextColor(textColor);
        return this;
    }

    public LiveViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public LiveViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public LiveViewHolder setHoldVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public LiveViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        if (view == null) {
            return this;
        }
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public LiveViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            if (view == null) {
                continue;
            }
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public LiveViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setProgress(progress);
        return this;
    }

    public LiveViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public LiveViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setMax(max);
        return this;
    }

    public LiveViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setRating(rating);
        return this;
    }

    public LiveViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public LiveViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTag(tag);
        return this;
    }

    public LiveViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTag(key, tag);
        return this;
    }

    public LiveViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        if (view == null) {
            return this;
        }
        view.setChecked(checked);
        return this;
    }

    public LiveViewHolder setSelected(int viewId, boolean selected) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setSelected(selected);
        return this;
    }

    /**
     * 关于事件的
     */
    public LiveViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setOnClickListener(listener);
        return this;
    }

    public LiveViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setOnTouchListener(listener);
        return this;
    }

    public LiveViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setOnLongClickListener(listener);
        return this;
    }

    public LiveViewHolder setPadding(int viewId, int left, int top, int right, int bottom) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setPadding(left, top, right, bottom);
        return this;
    }
}
