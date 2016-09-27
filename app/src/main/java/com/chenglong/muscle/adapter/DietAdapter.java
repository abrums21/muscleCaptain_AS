package com.chenglong.muscle.adapter;

import com.chenglong.muscle.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DietAdapter extends BaseAdapter {

    private final int[] imgs = {R.drawable.tool_diet_time_1,
            R.drawable.tool_diet_time_2,
            R.drawable.tool_diet_time_3,
            R.drawable.tool_diet_time_4,
            R.drawable.tool_diet_time_5,
            R.drawable.tool_diet_time_6,
            R.drawable.tool_diet_time_7,
            R.drawable.tool_diet_time_8};
    private String[] dinners;
    private String[] foods;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private class ViewHolder {
        public ImageView image;
        public TextView dinner;
        public TextView food;
    }

    public DietAdapter(Context context) {
        this.context = context;
        dinners = context.getResources().getStringArray(R.array.dinners);
        foods = context.getResources().getStringArray(R.array.foods);
        
		/* 设置选项  */
        options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_launcher)//设置图片在下载期间显示的图片  
                .showImageOnFail(R.drawable.ic_launcher)//设置图片加载/解码过程中错误时候显示的图片
                .delayBeforeLoading(0)//设置延时多少时间后开始下载
                .cacheInMemory(true)// 设置下载的资源是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以何种编码方式显示
//				.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
                .build();
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imgs.length;
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
        ViewHolder viewHolder;

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_diet, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.diet_listitem_iv);
            viewHolder.dinner = (TextView) convertView.findViewById(R.id.diet_listitem_tv);
            viewHolder.food = (TextView) convertView.findViewById(R.id.diet_listitem_tv2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage("drawable://" + imgs[position], viewHolder.image, options);
        viewHolder.dinner.setText(dinners[position]);
        viewHolder.food.setText(foods[position]);

        return convertView;
    }
}
