package com.chenglong.muscle.activity.toolbox;

import com.chenglong.muscle.R;
import com.chenglong.muscle.adapter.DietAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ToolDietActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tool_diet);
//        TextView rightView = (TextView) findViewById(R.id.toolbar_title_right);
//        rightView.setText("餐饮食谱");

        ListView lv = (ListView) findViewById(R.id.diet_list);
        lv.setAdapter(new DietAdapter(this));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().stop();
    }
}
