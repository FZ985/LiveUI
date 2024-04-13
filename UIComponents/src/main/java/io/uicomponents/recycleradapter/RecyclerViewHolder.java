package io.uicomponents.recycleradapter;

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


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private final View mConvertView;
    private final Context mContext;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static RecyclerViewHolder createViewHolder(Context context, View itemView) {
        RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView);
        return holder;
    }

    public static RecyclerViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
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
    public RecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv == null) {
            return this;
        }
        tv.setText(text);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, Spannable text) {
        TextView tv = getView(viewId);
        if (tv == null) {
            return this;
        }
        tv.setText(text);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, CharSequence text, TextView.BufferType type) {
        TextView tv = getView(viewId);
        if (tv == null) {
            return this;
        }
        tv.setText(text, type);
        return this;
    }

    public RecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        if (view == null) {
            return this;
        }
        try {
            view.setImageResource(resId);
        } catch (Exception e) {
            Log.e("RecyclerViewHolder", e.toString());
        }
        return this;
    }

    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setImageDrawable(drawable);
        return this;
    }

    public RecyclerViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setBackgroundColor(color);
        return this;
    }

    public RecyclerViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RecyclerViewHolder setBackgroundDrawable(int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setBackground(drawable);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTextColor(textColor);
        return this;
    }

    public RecyclerViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public RecyclerViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RecyclerViewHolder setHoldVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public RecyclerViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        if (view == null) {
            return this;
        }
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public RecyclerViewHolder setTypeface(Typeface typeface, int... viewIds) {
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

    public RecyclerViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setProgress(progress);
        return this;
    }

    public RecyclerViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RecyclerViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setMax(max);
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setRating(rating);
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setTag(key, tag);
        return this;
    }

    public RecyclerViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        if (view == null) {
            return this;
        }
        view.setChecked(checked);
        return this;
    }

    public RecyclerViewHolder setSelected(int viewId, boolean selected) {
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
    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setOnTouchListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setOnLongClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setPadding(int viewId, int left, int top, int right, int bottom) {
        View view = getView(viewId);
        if (view == null) {
            return this;
        }
        view.setPadding(left, top, right, bottom);
        return this;
    }
}
