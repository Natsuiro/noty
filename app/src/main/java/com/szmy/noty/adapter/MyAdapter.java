package com.szmy.noty.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.szmy.noty.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends NotyAdapter<String> {
    private Context mContext;
    private List<String> list;
    public MyAdapter(Context context){
        mContext = context;
    }



    @Override
    List<String> getDataList() {
        if (list==null) list = new ArrayList<>();
        return list;
    }

    @Override
    void bindView(NotyViewHolder viewHolder, String item) {
        VH holder = (VH) viewHolder;
        holder.textView.setText(item);
    }

    @Override
    NotyViewHolder getViewHolder() {
        View view = View.inflate(mContext, R.layout.item_layout, null);
        return new VH(view);
    }

    @Override
    public void setList(List<String> list) {
        this.list = list;
    }

    static class VH extends NotyViewHolder{
        TextView textView;
        VH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
