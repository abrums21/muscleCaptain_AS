package com.chenglong.muscle.tool;

import com.chenglong.muscle.R;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class DietActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.diet);
        TextView rightView = (TextView) findViewById(R.id.toolbar_title_right);
        rightView.setText("餐饮食谱");

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
