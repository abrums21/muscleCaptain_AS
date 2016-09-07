package com.chenglong.muscle.main;

import com.chenglong.muscle.R;
import com.chenglong.muscle.util.MyBitmapUtil;
import com.chenglong.muscle.util.MyPackageUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	private static final int DELAY_VALUE = 4000;   /* 3s延迟  */
	private SharedPreferences shareaPare;
	private ImageView icon;
	private ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.init);
		shareaPare = getSharedPreferences("phone", MODE_PRIVATE);
		
		icon = (ImageView) findViewById(R.id.init_img2);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.init_translation);
		icon.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				icon.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				icon.setVisibility(View.GONE);
				img = (ImageView)findViewById(R.id.init_img);
				//img.setImageBitmap(MyBitmapUtil.decodeBitmapByRes(SplashActivity.this, R.drawable.init));
				img.setImageBitmap(MyBitmapUtil.decodeBitmapByRes(SplashActivity.this, R.drawable.init));
				img.startAnimation(new MyAnimation());
				
			}
		});
		//img.startAnimation(new MyAnimation());
		
		MyTipDB.openDatabase(this);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int version = MyPackageUtil.getAppVersionCode(SplashActivity.this);
				
				Intent initIntent;				
				if (shareaPare.getBoolean("firstStart", true) || (version != shareaPare.getInt("curVersion", 0))) {
					/* 首次启动 */
					Editor editor = shareaPare.edit();
					editor.putBoolean("firstStart", false);
					editor.putInt("curVersion", version);
					editor.commit();
					initIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
					/* 临时添加  */
					//MyTipDB.stub_tipsDbStore();
					MyTipDB.openDatabase(SplashActivity.this);
				} else {
					/* 非首次启动 */
					initIntent = new Intent(SplashActivity.this, MainActivity.class);
					//initIntent = new Intent(SplashActivity.this, WelcomeActivity.class);  /* just 4 test */
				}
				
				SplashActivity.this.startActivity(initIntent);
				//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//		    	ImageLoader.getInstance().clearMemoryCache();
//		    	ImageLoader.getInstance().stop();
				SplashActivity.this.finish();
			}
		}, DELAY_VALUE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
