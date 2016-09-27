package com.chenglong.muscle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.chenglong.muscle.util.ScreenUtil;

import java.util.List;

public class GameAdapter extends BaseAdapter {

    private List<Bitmap> image;
    private Context context;

    private int width = 0;
    private int height = 0;

    public GameAdapter(Context context, int colums, List<Bitmap> img) {
        // TODO Auto-generated constructor stub
        image = img;
        this.context = context;

        //int density = (int) ScreenUtil.getScreenDensity(context);
        DisplayMetrics metric = com.chenglong.muscle.util.ScreenUtil.getScreenMetrics(context);
        width = metric.widthPixels / colums;
        height = (metric.heightPixels - ScreenUtil.dp2px(context, 170)) / colums;
        //height = (int) (0.7 * metric.heightPixels) / colums;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (null == convertView) {
            convertView = new ImageView(context);
            convertView.setLayoutParams(new GridView.LayoutParams(width, height));
            ((ImageView) convertView).setScaleType(ScaleType.FIT_XY);
        }

        ((ImageView) convertView).setImageBitmap(image.get(position));

        return convertView;
    }

}
