package com.szmy.noty.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.szmy.noty.widget.NotyFlowView;

import java.util.Collection;
import java.util.List;

public abstract class NotyAdapter<T> extends BaseAdapter {

    private NotyFlowView view;
    public void setTargetView(NotyFlowView view){this.view = view;}
    public void notifyDataChange(){
        view.setAdapter(this);
    }

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
        Log.d("itemId",""+position);
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
