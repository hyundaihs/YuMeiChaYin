package com.sp.shangpin.ui;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/11.
 */

public class RuleActivity extends AppCompatActivity {

    private TextView ruleContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        initActionBar();
        ruleContent = (TextView) findViewById(R.id.rule_content);
        ruleContent.setText(Html.fromHtml(MyApplication.systemInfo.getGz_contents()));
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.rule_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
