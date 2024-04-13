package io.live.ui.kit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.live.ui.kit.emoji.LiveAndroidEmoji;
import io.live.ui.kit.utils.LiveLog;

/**
 * author : JFZ
 * date : 2024/1/26 16:25
 * description :
 */
public final class LiveExtViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> mExtensionBoardState = new MutableLiveData<>();

    private boolean isSoftInputShow;

    @SuppressLint("StaticFieldLeak")
    private EditText editText;

    private final TextWatcher mTextWatcher = new TextWatcher() {
        private int start;
        private int count;
        private boolean isProcess;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isProcess) {
                return;
            }
            this.start = start;
            this.count = count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isProcess) {
                return;
            }
            int selectionStart = editText.getSelectionStart();
            if (LiveAndroidEmoji.isEmoji(s.subSequence(start, start + count).toString())) {
                isProcess = true;
                String resultStr = LiveAndroidEmoji.replaceEmojiWithText(s.toString());
                editText.setText(LiveAndroidEmoji.ensure(resultStr), TextView.BufferType.SPANNABLE);
                editText.setSelection(Math.min(editText.getText().length(), Math.max(0, selectionStart)));
                isProcess = false;
            }
        }
    };

    public LiveExtViewModel(@NonNull Application application) {
        super(application);
    }

    public void setAttachChat(EditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(mTextWatcher);
    }

    public void onSendClick() {
        LiveLog.e("======onSendClick");
    }

    public void collapseBoard() {
        if (mExtensionBoardState.getValue() != null
                && mExtensionBoardState.getValue().equals(false)) {
            LiveLog.e("TAG", "already collapsed, return directly.");
            return;
        }
        LiveLog.e("TAG", "collapseExtensionBoard");
        setSoftInputKeyBoard(false);
        mExtensionBoardState.postValue(false);
    }

    public void setSoftInputKeyBoard(boolean isShow) {
        forceSetSoftInputKeyBoard(isShow);
    }

    public void forceSetSoftInputKeyBoard(boolean isShow) {
        forceSetSoftInputKeyBoard(isShow, true);
    }

    public void forceSetSoftInputKeyBoard(boolean isShow, boolean clearFocus) {
        if (editText == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getApplication().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (isShow) {
                editText.requestFocus();
                imm.showSoftInput(editText, 0);
            } else {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                if (clearFocus) {
                    editText.clearFocus();
                } else {
                    editText.requestFocus();
                }
            }
            isSoftInputShow = isShow;
        }
        if (isShow && mExtensionBoardState.getValue() != null && mExtensionBoardState.getValue().equals(false)) {
            mExtensionBoardState.setValue(true);
        }
    }

    public EditText getEditText() {
        return editText;
    }

    public boolean isSoftInputShow() {
        return isSoftInputShow;
    }

    public MutableLiveData<Boolean> getExtensionBoardState() {
        return mExtensionBoardState;
    }

}
