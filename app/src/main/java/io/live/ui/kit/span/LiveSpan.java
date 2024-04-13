package io.live.ui.kit.span;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * author : JFZ
 * date : 2023/12/28 09:45
 * description : 仅更改文字大小、颜色的span 工具
 */
public class LiveSpan {

    private final List<Builder> builders = new ArrayList<>();

    private LiveSpan() {
        builders.clear();
    }

    public static LiveSpan get() {
        return new LiveSpan();
    }

    public LiveSpan add(Builder build) {
        builders.add(build);
        return this;
    }

    public static Builder build(String text) {
        return new Builder(text);
    }

    public static Builder build(Spannable spannable) {
        return new Builder(spannable);
    }

    public SpannableStringBuilder build() {
        SpannableStringBuilder string = new SpannableStringBuilder();
        for (Builder build : builders) {
            String text = build.getText();
            List<TextCreator> creators = build.getCreators();
            SpannableString span = new SpannableString(text);
            for (TextCreator create : creators) {
                span.setSpan(create.getSpan(), create.getStart(), create.getEnd(), create.getFlag());
            }
            string.append(span);
        }
        return string;
    }

    public static class Builder {

        private final String text;
        private final List<TextCreator> creators = new ArrayList<>();


        public Builder(String string) {
            this.text = string;
            creators.clear();
        }

        public Builder(@NonNull Spannable spannable) {
            this.text = spannable.getText();
            creators.clear();
            addSpan(spannable);
        }

        public final Builder textColor(@ColorInt int color) {
            if (!TextUtils.isEmpty(text)) {
                creators.add(new TextCreator(new ForegroundColorSpan(color),
                        0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
            }
            return this;
        }

        public final Builder textSize(int dpSize) {
            if (!TextUtils.isEmpty(text)) {
                creators.add(new TextCreator(new AbsoluteSizeSpan(dpSize, true),
                        0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
            }
            return this;
        }

        public final Builder textStyle(int style) {
            if (!TextUtils.isEmpty(text)) {
                creators.add(new TextCreator(new StyleSpan(style),
                        0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
            }
            return this;
        }

        public final Builder addSpan(Object span) {
            if (!TextUtils.isEmpty(text)) {
                creators.add(new TextCreator(span, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE));
            }
            return this;
        }

        public final Builder addSpan(TextCreator creator) {
            if (!TextUtils.isEmpty(text)) {
                creators.add(creator);
            }
            return this;
        }

        final String getText() {
            return text;
        }

        List<TextCreator> getCreators() {
            return creators;
        }
    }

    public final static class TextCreator {

        private final Object span;

        private final int start;

        private final int end;

        private final int flag;

        public TextCreator(Object span, int start, int end, int flag) {
            this.span = span;
            this.start = start;
            this.end = end;
            this.flag = flag;
        }

        public Object getSpan() {
            return span;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public int getFlag() {
            return flag;
        }
    }

    public interface Spannable {

        String getText();
    }

}
