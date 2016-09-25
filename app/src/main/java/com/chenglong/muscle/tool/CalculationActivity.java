package com.chenglong.muscle.tool;

import com.chenglong.muscle.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class CalculationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation);
//        TextView rightView = (TextView) findViewById(R.id.toolbar_title_right);
//        rightView.setText("健康计算");

        ListView lv = (ListView) findViewById(R.id.calculation_list);
        String[] items = getResources().getStringArray(R.array.calculation_items);
        lv.setAdapter(new CalculationAdapter(this, items));
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(CalculationActivity.this, CalcItemsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
