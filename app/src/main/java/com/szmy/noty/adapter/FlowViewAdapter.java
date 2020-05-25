package com.szmy.noty.adapter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.szmy.noty.R;
import com.szmy.noty.model.NotyBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
public class FlowViewAdapter extends NotyAdapter<NotyBean> {
    private Context mContext;
    private List<NotyBean> list;
    public FlowViewAdapter(Context context){
        mContext = context;
    }


    @Override
    List<NotyBean> getDataList() {
        if (list==null) list = new ArrayList<>();
        return list;
    }

    @Override
    void bindView(NotyViewHolder viewHolder, NotyBean item) {
        VH holder = (VH) viewHolder;
        holder.content.setMaxWidth(400);
        holder.content.setText(item.getContent());
        holder.time.setText(item.getTime());
        //内容需要换行
    }


    @Override
    NotyViewHolder getViewHolder() {
        View view = View.inflate(mContext, R.layout.item_layout, null);
        return new VH(view);
    }

    @Override
    public void setList(List<NotyBean> list) {
        this.list = list;
    }

    static class VH extends NotyViewHolder{
        TextView content;
        TextView time;
        VH(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.text);
            time = itemView.findViewById(R.id.time);
        }
    }
}
