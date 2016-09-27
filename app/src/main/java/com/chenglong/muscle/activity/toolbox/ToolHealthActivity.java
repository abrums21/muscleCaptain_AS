package com.chenglong.muscle.activity.toolbox;

import com.chenglong.muscle.R;
import com.chenglong.muscle.adapter.HealthAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ToolHealthActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
//        TextView rightView = (TextView) findViewById(R.id.toolbar_title_right);
//        rightView.setText("健康计算");

        ListView lv = (ListView) findViewById(R.id.calculation_list);
        String[] items = getResources().getStringArray(R.array.calculation_items);
        lv.setAdapter(new HealthAdapter(this, items));
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ToolHealthActivity.this, ToolHealthItemsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
