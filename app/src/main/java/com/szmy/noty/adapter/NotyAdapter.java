package com.szmy.noty.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

public abstract class NotyAdapter<T> extends BaseAdapter {

    @Override
    public int getCount() {
        return getDataList().size();
    }

    abstract List<T> getDataList();

    @Override
    public T getItem(int position) {
        return getDataList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotyViewHolder viewHolder;
        if (convertView==null){
            viewHolder = getViewHolder();
            convertView = viewHolder.getItemView();
            convertView.setTag(viewHolder);
        }
        viewHolder = (NotyViewHolder) convertView.getTag();
        bindView(viewHolder,getItem(position));
        return convertView;
    }

    abstract void bindView(NotyViewHolder viewHolder, T item);

    abstract NotyViewHolder getViewHolder();

    abstract void setList(List<T> list);

    abstract static class NotyViewHolder{
        private View itemView;
        NotyViewHolder(View itemView){
            this.itemView = itemView;
        }
        View getItemView() {
            return itemView;
        }
    }

}
