package io.live.ui.kit.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.lang.ref.WeakReference;

import io.live.ui.R;
import io.live.ui.kit.LiveActivity;
import io.live.ui.kit.LiveExtViewModel;
import io.live.ui.kit.emoji.LiveEmoticonBoard;
import io.uicomponents.api.AppLifecycle;
import io.uicomponents.switchpanel.PanelSwitchHelper;
import io.uicomponents.switchpanel.interfaces.listener.OnPanelChangeListener;
import io.uicomponents.switchpanel.view.panel.IPanelView;
import io.uicomponents.switchpanel.view.panel.PanelView;

/**
 * by JFZ
 * 2024/4/11
 * desc：直播页面帮助类
 **/
public final class LivePageHelper implements AppLifecycle {

    private LiveActivity mActivity;

    private PanelSwitchHelper mHelper;

    private LiveExtViewModel extViewModel;

    private boolean canInputText = true;

    public void bindPage(LiveActivity activity) {
        this.mActivity = new WeakReference<>(activity).get();
        extViewModel = new ViewModelProvider(activity).get(LiveExtViewModel.class);
        extViewModel.setAttachChat(activity.getPageBinding().liveEditInput);
        initClickListener();
    }

    private void initClickListener() {
        //发送
        mActivity.getPageBinding().send.setOnClickListener(new FixClickListener() {
            @Override
            protected void onViewClick(@NonNull View v) {

            }
        });

        //弹出输入
        mActivity.getPageBinding().say.setOnClickListener(new FixClickListener() {
            @Override
            protected void onViewClick(@NonNull View v) {
                if (canInputText) {
                    mActivity.getPageBinding().editLayout.setVisibility(View.VISIBLE);
                    mActivity.getPageBinding().btnLayout.setVisibility(View.GONE);
                    mActivity.getPageBinding().liveEditInput.postDelayed(() -> {
                        mActivity.getPageBinding().liveEditInput.requestFocus();
                        extViewModel.forceSetSoftInputKeyBoard(true, false);
                    }, 10);
                }
            }
        });

        mActivity.getPageBinding().liveEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mActivity.getPageBinding().send.setVisibility(View.GONE);
                } else {
                    mActivity.getPageBinding().send.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onStart() {
        if (mHelper == null) {
            mHelper = new PanelSwitchHelper.Builder(mActivity)
                    //可选
                    .addKeyboardStateListener((visible, height) -> {
                        log("系统键盘是否可见 : " + visible + " 高度为：" + height);
                    })
                    .addEditTextFocusChangeListener((view, hasFocus) -> {
                        log("输入框是否获得焦点 : " + hasFocus);
                        if (hasFocus) {
//                            scrollToBottom();
                        }
                    })
                    //可选
                    .addPanelChangeListener(new OnPanelChangeListener() {

                        @Override
                        public void onKeyboard() {
                            log("唤起系统输入法");
//                            mBinding.emotionBtn.setSelected(false);
//                            scrollToBottom();
                        }

                        @Override
                        public void onNone() {
                            log("隐藏所有面板");
                            mActivity.getPageBinding().editLayout.setVisibility(View.GONE);
                            mActivity.getPageBinding().btnLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onPanel(IPanelView view) {
                            log("唤起面板 : " + view);
//                            if (view instanceof PanelView) {
//                                mBinding.emotionBtn.setSelected(((PanelView) view).getId() == R.id.panel_emotion ? true : false);
//                                scrollToBottom();
//                            }
                        }

                        @Override
                        public void onPanelSizeChange(IPanelView panel, boolean portrait, int oldWidth, int oldHeight, int width, int height) {
                            if (panel instanceof PanelView) {
                                PanelView panelView = (PanelView) panel;
                                if (panelView.getId() == R.id.panel_emotion) {
                                    LiveEmoticonBoard emoticonBoard = mActivity.getPageBinding().panelEmotion.findViewById(R.id.emoji_board);
                                    emoticonBoard.initEmoji(extViewModel);
                                }
                            }
                        }
                    })
                    .logTrack(false)
                    .build();
        }
    }


    public boolean onBackPressed() {
        if (mHelper != null && mHelper.hookSystemBackByPanelSwitcher()) {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        this.mActivity = null;
    }


    private void log(String m) {
        Log.e("LivePageHelper", m);
    }
}
