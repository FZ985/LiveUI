package io.uicomponents.recycleradapter;

import androidx.collection.SparseArrayCompat;

import java.util.List;

public class RecyclerProviderManager<T> {
    private final int DEFAULT_ITEM_VIEW_TYPE = -100;
    private SparseArrayCompat<RecyclerViewProvider<T>> mProviders = new SparseArrayCompat<>();
    private RecyclerViewProvider<T> mDefaultProvider;

    public RecyclerProviderManager() {
        mDefaultProvider = new RecyclerDefaultProvider();
    }

    public RecyclerProviderManager(List<RecyclerViewProvider<T>> providerList) {
        this();
        for (RecyclerViewProvider<T> provider : providerList) {
            addProvider(provider);
        }
    }

    public int getProviderCount() {
        return mProviders.size();
    }

    public void addProvider(RecyclerViewProvider<T> provider) {
        int viewType = mProviders.size();
        if (provider != null) {
            mProviders.put(viewType, provider);
        }
    }

    public void addProvider(int viewType, RecyclerViewProvider<T> provider) {
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
    public void setDefaultProvider(RecyclerViewProvider<T> defaultProvider) {
        mDefaultProvider = defaultProvider;
    }

    public void removeProvider(RecyclerViewProvider<T> provider) {
        if (provider == null) {
            throw new NullPointerException("ItemViewProvider is null");
        }
        int indexToRemove = mProviders.indexOfValue(provider);

        if (indexToRemove >= 0) {
            mProviders.removeAt(indexToRemove);
        }
    }

    public void replaceProvider(Class oldProviderClass, RecyclerViewProvider<T> provider) {
        int key = -1;
        for (int i = 0; i < mProviders.size(); i++) {
            int index = mProviders.keyAt(i);
            RecyclerViewProvider<T> item = mProviders.get(index);
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

    public RecyclerViewProvider<T> getProvider(int viewType) {
        RecyclerViewProvider<T> provider = mProviders.get(viewType);
        if (provider == null) {
            provider = mDefaultProvider;
        }
        return provider;
    }

    public int getItemViewType(RecyclerViewProvider<T> provider) {
        return mProviders.indexOfValue(provider);
    }

    public int getItemViewType(T item, int position) {
        int count = mProviders.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerViewProvider<T> provider = mProviders.valueAt(i);
            if (provider.isItemViewType(item)) {
                return mProviders.keyAt(i);
            }
        }
        return DEFAULT_ITEM_VIEW_TYPE;
    }

    public RecyclerViewProvider<T> getProvider(T item) {
        for (int i = 0; i < mProviders.size(); i++) {
            RecyclerViewProvider<T> provider = mProviders.valueAt(i);
            if (provider.isItemViewType(item)) {
                return provider;
            }
        }
        return mDefaultProvider;
    }
}
