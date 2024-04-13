package io.live.ui.kit.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.live.ui.R;
import io.live.ui.databinding.LivePanelEmojiBoardBinding;
import io.live.ui.kit.LiveExtViewModel;
import io.live.ui.kit.utils.LiveLog;
import io.uicomponents.api.AppFun;
import io.uicomponents.recycleradapter.RecyclerViewHolder;


/**
 * author : JFZ
 * date : 2024/2/19 13:42
 * description :
 */
public class LiveEmoticonBoard extends LinearLayout {

    private final LivePanelEmojiBoardBinding binding;

    static final String DELETE = "delete";

    final int spanCount = 9;

    public LiveEmoticonBoard(Context context) {
        this(context, null);
    }

    public LiveEmoticonBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveEmoticonBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = LivePanelEmojiBoardBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    LiveEmojiListAdapter adapter;
    private LiveExtViewModel extViewModel;

    public void initEmoji(LiveExtViewModel extViewModel) {
        if (extViewModel == null) return;
        this.extViewModel = extViewModel;
        if (adapter != null) return;

        binding.delete.setOnClickListener(v -> inputText(DELETE));
        binding.recycler.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        adapter = new LiveEmojiListAdapter(getContext(), spanCount);
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(index -> {
            try {
                int code = LiveAndroidEmoji.getEmojiCode(index);
                char[] chars = Character.toChars(code);
                StringBuilder key = new StringBuilder(Character.toString(chars[0]));
                for (int i = 1; i < chars.length; i++) {
                    key.append(chars[i]);
                }
                inputText(key.toString());
            } catch (Exception e) {
                LiveLog.e("====emoji item click err:" + e.getMessage());
            }
        });
    }

    private void inputText(String text) {
        if (extViewModel.getEditText() == null) return;
        if (text.equals(DELETE)) {
            extViewModel.getEditText().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {
            int start = extViewModel.getEditText().getSelectionStart();
            extViewModel.getEditText().getText().insert(start, text);
        }
    }

    private static class LiveEmojiListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private AppFun.Fun1<Integer> clickListener;
        private final int spanCount;
        private final int dp15;

        public LiveEmojiListAdapter(Context context, int spanCount) {
            this.spanCount = spanCount;
            dp15 = (int) (context.getResources().getDisplayMetrics().density * 15);
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_emoji_tab_default_item, null, false);
            return new RecyclerViewHolder(parent.getContext(), view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            RelativeLayout yl_rl_root = holder.getView(R.id.yl_rl_root);
            int count = getItemCount();
            // 计算总行数
            int rowCount = (int) Math.ceil((double) count / spanCount);
            // 计算当前位置所在的行数
            int currentRow = (int) Math.ceil((double) (position + 1) / spanCount);
            // 如果当前行数等于总行数，则为最后一行
            boolean isLastLine = currentRow == rowCount;
            if (isLastLine) {
                yl_rl_root.setPadding(0, dp15, 0, dp15 * 3);
            } else {
                yl_rl_root.setPadding(0, dp15, 0, 0);
            }

            ImageView yl_image = holder.getView(R.id.yl_image);
            yl_image.setImageDrawable(LiveAndroidEmoji.getEmojiDrawable(holder.getContext(), position));
            holder.itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.apply(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            List<LiveAndroidEmoji.EmojiInfo> list = LiveAndroidEmoji.getEmojiList();
            return list == null ? 0 : list.size();
        }

        public void setOnItemClickListener(AppFun.Fun1<Integer> clickListener) {
            this.clickListener = clickListener;
        }
    }

}
