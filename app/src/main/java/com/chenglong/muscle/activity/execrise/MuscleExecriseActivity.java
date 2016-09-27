package com.chenglong.muscle.activity.execrise;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.chenglong.muscle.R;
import com.chenglong.muscle.adapter.MuscleExecriseAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MuscleExecriseActivity extends Activity implements OnItemClickListener {

	private final static int idNum = 3;
	private String[] name;
	private int[] drawableIds;
	private int[] drawableGIds;
	private PopupWindow popup = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execrise);

		decodeAttributes();
		setListInfo();
	}

	void setBackgroudAlpha(float value) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = value;
		getWindow().setAttributes(lp);
	}

	private void decodeAttributes() {
		// TODO Auto-generated method stub
		int position = getIntent().getExtras().getInt("position");

		String[] textArray = getResources().getStringArray(R.array.muscle);
		TextView tv = (TextView) findViewById(R.id.muscle_list_tv);
		tv.setText(textArray[position - 1]);

		TypedArray ar = getResources().obtainTypedArray(R.array.body);
		int[] resIds = new int[idNum];
		for (int i = 0; i < idNum; i++)
			resIds[i] = ar.getResourceId((position - 1) * idNum + i, 0);
		ar.recycle();
		/* name */
		name = getResources().getStringArray(resIds[0]);
		/* drawable */
		ar = getResources().obtainTypedArray(resIds[1]);
		int length = ar.length();
		drawableIds = new int[length];
		for (int i = 0; i < length; i++)
			drawableIds[i] = ar.getResourceId(i, 0);
		ar.recycle();
		/* drawable_g */
		ar = getResources().obtainTypedArray(resIds[2]);
		length = ar.length();
		drawableGIds = new int[length];
		for (int i = 0; i < length; i++)
			drawableGIds[i] = ar.getResourceId(i, 0);
		ar.recycle();
	}

	private void setListInfo() {
		ListView lv = (ListView) findViewById(R.id.musclelist_list);
		lv.setAdapter(new MuscleExecriseAdapter(this, name, drawableIds));
		lv.setOnItemClickListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != popup)
		{
		    popup.dismiss();
		}
		ImageLoader.getInstance().clearMemoryCache();
		ImageLoader.getInstance().stop();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		View mView = LayoutInflater.from(MuscleExecriseActivity.this).inflate(R.layout.ppw_execrise, null, false);
		popup = new PopupWindow(mView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		setBackgroudAlpha(0.3f);
		LinearLayout ll = (LinearLayout) mView.findViewById(R.id.muscle_popup_ll);
		ll.setFocusable(true);
		ll.setFocusableInTouchMode(true);

		ll.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					popup.dismiss();
					return true;
				}
				return false;
			}
		});

		ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup.dismiss();
			}
		});

		TextView tv = (TextView) mView.findViewById(R.id.popup_title);
		tv.setText(name[position]);

		GifView gif = (GifView) mView.findViewById(R.id.popup_gif);
		gif.setGifImage(drawableGIds[position]);
		gif.setGifImageType(GifImageType.WAIT_FINISH);
		gif.destroyDrawingCache();
		popup.showAtLocation(view, Gravity.CENTER, 0, 0);
		popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				setBackgroudAlpha(1f);
			}
		});
	}
}
