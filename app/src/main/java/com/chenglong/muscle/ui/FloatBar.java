package com.chenglong.muscle.ui;


import com.chenglong.muscle.R;
import com.chenglong.muscle.engine.FloatViewManager;

import android.content.Context;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatBar extends LinearLayout implements OnClickListener{

	private TranslateAnimation animationIn;
	private TranslateAnimation animationOut;
	private int counter = 0;
	private TextView text;
	private View root;
	private Context context;
	private LinearLayout ll;
	private Vibrator vibrator;
//	private final static long [] vibratePattern = {100, 500};
	private final static long setTime = 400;
    private final static long resetTime = 600;
	
	public FloatBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
		root = View.inflate(getContext(), R.layout.ui_floatball, null);
		ll = (LinearLayout) root.findViewById(R.id.floatball_bar_ll);
		animationIn = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
		animationIn.setDuration(500);
		animationIn.setFillAfter(true);
		animationOut = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f);
		animationOut.setDuration(500);
		animationOut.setFillAfter(true);
		animationOut.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				FloatViewManager manager = FloatViewManager.getInstance(getContext());
				manager.hideFloatBar();
				manager.showFloatBall();
			}
		});
		//an.setFillAfter(true);
		//an.setFillBefore(true);
		//ll.setAnimation(an);
		addView(root);
		
		TextView clearButton = (TextView) root.findViewById(R.id.floatball_bar_tv2);
		text = (TextView) root.findViewById(R.id.floatball_bar_tv3);
		clearButton.setOnClickListener(this);
		text.setOnClickListener(this);
		
		root.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				hideView();
				return false;
			}
		});
	}
	
	public void startAnimation()
	{
//		an = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
//		an.setDuration(500);
//		an.setRepeatCount(2);
		//an.setFillAfter(true);
		//an.setFillBefore(true);
		ll.startAnimation(animationIn);
		//an.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.floatball_bar_tv2:
			counter = 0;
			setCounter();
            vibrator.vibrate(resetTime);
			break;
        case R.id.floatball_bar_tv3:
        	counter++;
        	setCounter();
            vibrator.vibrate(setTime);
			//vibrator.vibrate(vibratePattern, -1);
			break;
        case R.id.floatball_bar_img:
        	hideView();
			break;
		default:
			break;
		}
	}
	
	private void setCounter()
	{
		text.setText(""+counter);
	}
	
	private void hideView()
	{
		ll.startAnimation(animationOut);
//		FloatViewManager manager = FloatViewManager.getInstance(context);
//		manager.hideFloatBar();
//		manager.showFloatBall();
	}

}
