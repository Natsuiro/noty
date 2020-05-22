package com.szmy.noty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.szmy.noty.adapter.MyAdapter;
import com.szmy.noty.model.NotyBean;
import com.szmy.noty.model.NotyDB;
import com.szmy.noty.widget.NotyFlowView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private List<NotyBean> noties = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        final NotyFlowView flowView = findViewById(R.id.noty);
        final MyAdapter myAdapter = new MyAdapter(this);
        myAdapter.setList(noties);
        flowView.setAdapter(myAdapter);

        Button btn = findViewById(R.id.newTip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                int anInt = new Random().nextInt(10)+2;
                while (anInt-->0){
                    sb.append("text");
                    sb.append("\n");
                }
                NotyDB.instance().insert(sb.toString());
                noties.clear();
                noties.addAll(NotyDB.instance().search());
//                flowView.setAdapter(myAdapter);
                myAdapter.notifyDataChange();

            }
        });

    }

    private void initData() {
        noties.clear();
        noties.addAll(NotyDB.instance().search());
        Log.d("listSize",noties.size()+"");
    }
}
