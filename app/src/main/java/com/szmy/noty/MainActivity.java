package com.szmy.noty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szmy.noty.adapter.FlowViewAdapter;
import com.szmy.noty.model.NotyBean;
import com.szmy.noty.model.NotyDB;
import com.szmy.noty.widget.NotyFlowView;
import java.util.ArrayList;
import java.util.List;

import static com.szmy.noty.R.id.noty;

public class MainActivity extends BaseActivity {
    private EditText searchNotes;
    private List<NotyBean> contentList = new ArrayList<>();
    private List<NotyBean> searchList = new ArrayList<>();
    private FlowViewAdapter flowViewAdapter = new FlowViewAdapter(this);

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
        search(searchNotes.getText().toString());
    }

    private void saveNewNote(String content) {
        NotyDB.instance().insert(content);
        search(searchNotes.getText().toString());
    }

    private List<NotyBean> getAllBeans() {
        return NotyDB.instance().search();
    }

    private void updateContentList(List<NotyBean> list) {
        contentList.clear();
        contentList.addAll(list);
        flowViewAdapter.notifyDataChange();
    }

    private void updateNote(int itemId, String content) {
        NotyDB.instance().update(itemId,content);
        search(searchNotes.getText().toString());
    }

    @Override
    protected void initView() {
        NotyFlowView flowView = findViewById(noty);
        flowViewAdapter.setList(contentList);
        flowView.setAdapter(flowViewAdapter);
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
                toEdit.putExtra("itemContent", contentList.get(itemId).getContent());
                toEdit.putExtra("itemTime", contentList.get(itemId).getTime());
                toEdit.putExtra("itemTime", contentList.get(itemId).getTime());
                toEdit.putExtra("itemId", contentList.get(itemId).getId());
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
                                deleteNote(contentList.get(itemId).getId());
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
        searchNotes = findViewById(R.id.searchNotes);

        searchNotes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search(v.getText().toString());
                return true;
            }
        });

    }

    //
    private void search(String keyWord) {
        if (keyWord.isEmpty()){
            updateContentList(getAllBeans());
            return;
        }
        searchList.clear();
        List<NotyBean> allBeans = getAllBeans();
        for (NotyBean bean : allBeans) {
            //匹配
            if(bean.getContent().contains(keyWord)){
                searchList.add(bean);
            }
        }
        updateContentList(searchList);
    }

    @Override
    int layoutResId() {
        return R.layout.activity_main;
    }


    /**
     * 分发点击除 EditText 外区域，当前焦点在 ET 上，EditText 取消焦点
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        if (v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            //点击位置不在edittext中
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void initData() {
        contentList.clear();
        contentList.addAll(getAllBeans());
        Log.d("listSize", contentList.size()+"");
    }
}
