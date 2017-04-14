package com.sheaye.widget;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by yexinyan on 2017/4/12.
 */

public abstract class CubePagerAdapter<T> {

    protected Context mContext;
    private List<T> mData;
//    private SparseArray<View> mViews;
    private DataSetObserver mObserver;
    private DataSetObservable mObservable = new DataSetObservable();
    private View mConvertView;

    public CubePagerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
//        mViews = new SparseArray<>();
    }

    public void addAll(Collection<? extends T> collection) {
        mData.addAll(collection);
        notifyDataSetChanged();
    }

    public void addAll(T[] t) {
        Collections.addAll(mData, t);
        notifyDataSetChanged();
    }

    public final int getCount() {
        return mData != null ? mData.size() : 0;
    }

    public T getItem(int position) {
        if (mData.size() > position) {
            return mData.get(position);
        }
        return null;
    }

    final View instantiateItem(ViewGroup parent, int position) {
        View view = getItemView(position, parent, mConvertView, mData.get(position));
//        mViews.append(position, view);
        return view;
    }

    final void destroyItem(ViewGroup parent, int position, int index) {
        mConvertView = parent.getChildAt(index);
        if (mConvertView != null) {
            parent.removeView(mConvertView);
//            mViews.delete(position);
        }
    }

    public abstract View getItemView(int position, ViewGroup parent, View convertView, T item);

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    void setCubeObserver(DataSetObserver dataSetObserver) {
        mObserver = dataSetObserver;
    }

    private void notifyDataSetChanged() {
        synchronized (this) {
            if (mObserver != null) {
                mObserver.onChanged();
            }
        }
        mObservable.notifyChanged();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mObservable.registerObserver(observer);
    }

    public void unregisterObserver(DataSetObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    public List<T> getData() {
        return mData;
    }
}
