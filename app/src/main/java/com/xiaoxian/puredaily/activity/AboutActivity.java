package com.xiaoxian.puredaily.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoxian.puredaily.R;

public class AboutActivity extends Activity{
    private TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        about=(TextView)findViewById(R.id.about);

        SpannableString sp=new SpannableString("开发者有点懒，什么都木有留下\n\n科科");
        sp.setSpan(new ClickText(this),16,sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        about.setText(sp);
        //实现文本可滑动出错
        about.setMovementMethod(LinkMovementMethod.getInstance());
        //改变选中文本高亮颜色
        about.setHighlightColor(Color.TRANSPARENT);
    }

    class ClickText extends ClickableSpan{
        private Context context;

        public ClickText(Context context){
            this.context=context;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(true);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context,"嘲笑：你是猪啊！",Toast.LENGTH_SHORT).show();
        }
    }

}
