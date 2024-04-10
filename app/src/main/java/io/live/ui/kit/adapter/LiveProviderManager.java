package io.live.ui.kit.adapter;

import androidx.collection.SparseArrayCompat;

import java.util.List;

public class LiveProviderManager<T> {
    private final int DEFAULT_ITEM_VIEW_TYPE = -100;
    private SparseArrayCompat<LiveViewProvider<T>> mProviders = new SparseArrayCompat<>();
    private LiveViewProvider<T> mDefaultProvider;

    public LiveProviderManager() {
        mDefaultProvider = new LiveDefaultProvider();
    }

    public LiveProviderManager(List<LiveViewProvider<T>> providerList) {
        this();
        for (LiveViewProvider<T> provider : providerList) {
            addProvider(provider);
        }
    }

    public int getProviderCount() {
        return mProviders.size();
    }

    public void addProvider(LiveViewProvider<T> provider) {
        int viewType = mProviders.size();
        if (provider != null) {
            mProviders.put(viewType, provider);
        }
    }

    public void addProvider(int viewType, LiveViewProvider<T> provider) {
        if (mProviders.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewProvider is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewProvider is "
                            + mProviders.get(viewType));
        }
        mProviders.put(viewType, provider);
    }

    /** 设置默认模板。当找不到和 viewType 对应的模板时，使用此默认模板进行 ui 处理。 */
    public void setDefaultProvider(LiveViewProvider<T> defaultProvider) {
        mDefaultProvider = defaultProvider;
    }

    public void removeProvider(LiveViewProvider<T> provider) {
        if (provider == null) {
            throw new NullPointerException("ItemViewProvider is null");
        }
        int indexToRemove = mProviders.indexOfValue(provider);

        if (indexToRemove >= 0) {
            mProviders.removeAt(indexToRemove);
        }
    }

    public void replaceProvider(Class oldProviderClass, LiveViewProvider<T> provider) {
        int key = -1;
        for (int i = 0; i < mProviders.size(); i++) {
            int index = mProviders.keyAt(i);
            LiveViewProvider<T> item = mProviders.get(index);
            if (item != null && item.getClass().equals(oldProviderClass)) {
                key = index;
                break;
            }
        }
        if (key != -1) {
            mProviders.put(key, provider);
        }
    }

    public void removeProvider(int itemType) {
        int indexToRemove = mProviders.indexOfKey(itemType);
        if (indexToRemove >= 0) {
            mProviders.removeAt(indexToRemove);
        }
    }

    public LiveViewProvider<T> getProvider(int viewType) {
        LiveViewProvider<T> provider = mProviders.get(viewType);
        if (provider == null) {
            provider = mDefaultProvider;
        }
        return provider;
    }

    public int getItemViewType(LiveViewProvider<T> provider) {
        return mProviders.indexOfValue(provider);
    }

    public int getItemViewType(T item, int position) {
        int count = mProviders.size();
        for (int i = count - 1; i >= 0; i--) {
            LiveViewProvider<T> provider = mProviders.valueAt(i);
            if (provider.isItemViewType(item)) {
                return mProviders.keyAt(i);
            }
        }
        return DEFAULT_ITEM_VIEW_TYPE;
    }

    public LiveViewProvider<T> getProvider(T item) {
        for (int i = 0; i < mProviders.size(); i++) {
            LiveViewProvider<T> provider = mProviders.valueAt(i);
            if (provider.isItemViewType(item)) {
                return provider;
            }
        }
        return mDefaultProvider;
    }
}
