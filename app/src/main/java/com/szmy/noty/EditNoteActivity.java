package com.szmy.noty;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends BaseActivity {

    private Button save;
    private EditText noteContent;
    private TextView time;
    private Intent data;
    private int itemId;
    @Override
    void initData() {
        data = getIntent();
    }

    @Override
    void initView() {
        noteContent = findViewById(R.id.note_content);
        time = findViewById(R.id.curTime);
        if (data!=null){
            String itemContent = data.getStringExtra("itemContent");
            noteContent.setText(itemContent);
            noteContent.setSelection(noteContent.getText().length());
            itemId = data.getIntExtra("itemId",-1);
        }
        String nowTime = SimpleDateFormat.getDateInstance().format(new Date());
        time.setText(nowTime);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //内容为空在接收结果的时候判断
                String content = noteContent.getText().toString();
                Intent data = new Intent();
                data.putExtra("content",content);
                data.putExtra("itemId",itemId);
                setResult(RESULT_OK,data);
                finish();
            }
        });

    }

    @Override
    int layoutResId() {
        return R.layout.activity_edit_note;
    }
}
