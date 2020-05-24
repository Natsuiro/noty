package com.szmy.noty.adapter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.szmy.noty.R;
import com.szmy.noty.model.NotyBean;
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
        holder.textView.setMaxWidth(400);
        holder.textView.setText(item.getContent());
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
        TextView textView;
        VH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
