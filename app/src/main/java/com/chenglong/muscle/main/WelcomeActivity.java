package com.chenglong.muscle.main;

import java.util.Timer;
import java.util.TimerTask;

import com.chenglong.muscle.R;
import com.chenglong.muscle.custom.MyCircleProgressBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

    private static final int[] pics = {R.drawable.intro_1, R.drawable.intro_2, R.drawable.intro_3,
            R.drawable.intro_4};
    private ViewPager vp;
    private ImageView[] dots;
    private int curIndex;
    private TextView enter;
    private MyCircleProgressBar myCircleProgressBar;
    private long firstTime = 0;
    private final static int TIME_INTERVAL = 500;
    private final static int TIME_START = 500;
    private final static int TIME_COUNT = 16;
    private final static int SECONDS = 1;
    private int mCount = 0;
    private Handler mHandler;
    private Timer timer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);

        ImageSetting();
        DotsSetting();
        switchSetting();
        progressSetting();
    }

    private void switchSetting() {
        // TODO Auto-generated method stub
        enter = (TextView) findViewById(R.id.welocme_enter);
        enter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                jumpActivity();
            }
        });
    }

    private void progressSetting() {
        // TODO Auto-generated method stub
        myCircleProgressBar = (MyCircleProgressBar) findViewById(R.id.welcome_progress);
        myCircleProgressBar.setText("跳过：" + TIME_COUNT + "s");
        myCircleProgressBar.setProgress(0);
        myCircleProgressBar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                jumpActivity();
            }
        });
        initTimerHandle();
    }

    private void ImageSetting() {
        // TODO Auto-generated method stub
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(new WelcomeAdapter(pics, this));
        vp.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                setCurDot(position);
                if (position == pics.length - 1) {
                    enter.setVisibility(View.VISIBLE);
                    //enter.setAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.welcome_button));
//                    View view = getLayoutInflater().inflate(R.layout.welcome_click, null);
//                    Toast toast = new Toast(WelcomeActivity.this);
//                    toast.setView(view);
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.show();
//                    Toast.makeText(WelcomeActivity.this, "点击盾牌进入",Toast.LENGTH_SHORT).show();
                } else {
                    enter.setVisibility(View.GONE);
                    enter.clearAnimation();
                }
            }
        });
    }

    private void DotsSetting() {
        // TODO Auto-generated method stub
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];

        for (int tmp = 0; tmp < dots.length; tmp++) {
            dots[tmp] = (ImageView) ll.getChildAt(tmp);
            dots[tmp].setEnabled(true);
            dots[tmp].setTag(tmp);
            dots[tmp].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    setCurViewPager((Integer) v.getTag());
                }
            });
        }

        curIndex = 0;
        dots[curIndex].setEnabled(false); // 初始被选中
    }

    private void setCurDot(int position) {
        if ((position < 0) || (position > pics.length - 1) || (curIndex == position)) {
            return;
        }

		/* 设置dots标记 */
        dots[position].setEnabled(false);
        dots[curIndex].setEnabled(true);
        curIndex = position;
    }

    private void setCurViewPager(int position) {
        if ((position < 0) || (position > pics.length - 1) || (curIndex == position)) {
            return;
        }
        /* 设置图像 */
        vp.setCurrentItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            } else {
                timer.cancel();
                WelcomeActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTimerHandle() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case SECONDS: {
                        mCount++;
                        myCircleProgressBar.setProgress((float)mCount / TIME_COUNT);
//                            timeText.setText("跳过：" + show + "s");
                        if (TIME_COUNT == mCount) {
                            timer.cancel();
                            jumpActivity();
                        }
                        break;
                    }
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(SECONDS);
            }
        }, TIME_START, TIME_INTERVAL);
    }

    private void jumpActivity() {
        Intent nextIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        WelcomeActivity.this.startActivity(nextIntent);
        WelcomeActivity.this.finish();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        timer.cancel();
    }

}
