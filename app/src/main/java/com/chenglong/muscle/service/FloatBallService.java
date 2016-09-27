package com.chenglong.muscle.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.chenglong.muscle.engine.FloatViewManager;

public class FloatBallService extends Service{

	private SharedPreferences sharedPre;
	private FloatViewManager manager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sharedPre = getSharedPreferences("phone", MODE_PRIVATE);
		manager = FloatViewManager.getInstance(this);
		showFloatBall();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//onCreate();
		showFloatBall();
		super.onDestroy();
	}

	private void showFloatBall()
	{
		if (1 == sharedPre.getInt("floatBallShow", 1))
		{
			manager.showFloatBall();
		}
	}

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//    	// TODO Auto-generated method stub
//    	Bundle bundle = intent.getExtras();
//    	floatBallShow = bundle.getInt("floatBallShow", 1);
//    	return super.onStartCommand(intent, flags, startId);
//    }
}
