package com.szmy.noty.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AutoSplitTextView extends androidx.appcompat.widget.AppCompatTextView {
    public AutoSplitTextView(Context context) {
        super(context);
    }

    public AutoSplitTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSplitTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public String autoSplitText(String rawText) {
        Log.i("rawTextLines","start Split");
        final Paint tvPaint = getPaint(); //paint，包含字体等信息
        final float tvWidth = getWidth() - getPaddingLeft() - getPaddingRight(); //控件可用宽度
        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            Log.i("rawTextLines",rawTextLine);
            float measureText = tvPaint.measureText(rawTextLine);
            Log.i("rawTextLines","split debug:"+measureText);
            if (measureText <= tvWidth) {
                //Log.i("rawTextLines","split debug");
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //Log.i("rawTextLines","split debug");
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt < rawTextLine.length(); ) {
                    Log.i("rawTextLines","split debug");
                    char ch = rawTextLine.charAt(cnt);
                    //累计当前行的长度
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    //如果长度仍然可以在一行中容纳，则直接拼接在后面
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                        cnt++;
                    } else {//否则另起一行
                        sbNewText.append("\n");
                        lineWidth = 0;
                    }
                }
            }
            sbNewText.append("\n");

        }
        Log.i("rawTextLines","split end");
        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }
}
