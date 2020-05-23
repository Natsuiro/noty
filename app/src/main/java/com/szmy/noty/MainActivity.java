package com.szmy.noty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.szmy.noty.adapter.MyAdapter;
import com.szmy.noty.model.NotyBean;
import com.szmy.noty.model.NotyDB;
import com.szmy.noty.widget.NotyFlowView;

import java.util.ArrayList;
import java.util.List;



import static com.szmy.noty.R.id.noty;


public class MainActivity extends BaseActivity {

    private List<NotyBean> noties = new ArrayList<>();
    private MyAdapter myAdapter = new MyAdapter(this);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //请求新建便签
        if (requestCode==REQUEST_NEW &&resultCode==RESULT_OK){
            if(data!=null){
                String content = data.getStringExtra("content");
                //插入新便签
                if(TextUtils.isEmpty(content)){
                    //不做处理，内容为空
                    toast("内容为空!");
                }else{
                    saveNewNote(content);
                    toast("添加成功~");
                }

            }
        //请求修改/查看/确认便签内容
        }else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK){

            if(data!=null){
                String content = data.getStringExtra("content");
                int itemId = data.getIntExtra("itemId",-1);
                if (itemId!=-1){
                    //更新便签内容
                    if (TextUtils.isEmpty(content)) {
                        //文本内容为空，删除便签
                        deleteNote(itemId);
                    }else {
                        updateNote(itemId,content);
                        toast("修改成功~");
                    }


                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void deleteNote(int itemId) {
        NotyDB.instance().delete(itemId);
        updateView();
    }

    private void saveNewNote(String content) {
        NotyDB.instance().insert(content);
        updateView();
    }

    private void updateView() {
        noties.clear();
        noties.addAll(NotyDB.instance().search());
        myAdapter.notifyDataChange();
    }

    private void updateNote(int itemId, String content) {
        NotyDB.instance().update(itemId,content);
        updateView();
    }

    @Override
    protected void initView() {
        NotyFlowView flowView = findViewById(noty);
        myAdapter.setList(noties);
        flowView.setAdapter(myAdapter);
        Button btn = findViewById(R.id.newTip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEdit = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivityForResult(toEdit,REQUEST_NEW);
            }
        });

        flowView.setOnItemClickListener(new NotyFlowView.OnItemClickListener() {
            @Override
            public void onClick(int itemId, View item) {

                Intent toEdit = new Intent(MainActivity.this, EditNoteActivity.class);
                toEdit.putExtra("itemContent",noties.get(itemId).getContent());
                toEdit.putExtra("itemTime",noties.get(itemId).getTime());
                toEdit.putExtra("itemTime",noties.get(itemId).getTime());
                toEdit.putExtra("itemId",noties.get(itemId).getId());
                startActivityForResult(toEdit,REQUEST_UPDATE);
            }
        });

        flowView.setOnItemLongTouchListener(new NotyFlowView.OnItemLongTouchListener() {
            @Override
            public boolean onLongTouch(final int itemId, final View item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除便签")
                        .setMessage("确定删除便签吗?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteNote(noties.get(itemId).getId());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("手抖了~", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return true;
            }
        });
    }

    @Override
    int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        noties.clear();
        noties.addAll(NotyDB.instance().search());
        Log.d("listSize",noties.size()+"");
    }
}
