package com.chenglong.muscle.activity.other;

import com.chenglong.muscle.R;
import com.chenglong.muscle.activity.main.MainActivity;
import com.chenglong.muscle.activity.toolbox.ToolGymActivity;
import com.chenglong.muscle.db.TipDBHelper;
import com.chenglong.muscle.engine.SplashAnimation;
import com.chenglong.muscle.util.BitmapUtil;
import com.chenglong.muscle.util.ShortCutUtil;
import com.chenglong.muscle.util.PackageUtil;

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

    private final static int DELAY_VALUE = 4000;   /* 3s延迟  */
    private ImageView icon;
    private final static String action = "activity_tool_gym";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        icon = (ImageView) findViewById(R.id.init_img2);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_over_horizontal);
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
                ImageView img = (ImageView) findViewById(R.id.init_img);
                img.setImageBitmap(BitmapUtil.decodeBitmapByRes(SplashActivity.this, R.drawable.bg_splash));
                img.startAnimation(new SplashAnimation());
            }
        });

        TipDBHelper.openDatabase(this);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int version = PackageUtil.getAppVersionCode(SplashActivity.this);
                SharedPreferences shareaPare = getSharedPreferences("phone", MODE_PRIVATE);
                Intent initIntent;

                if (shareaPare.getBoolean("firstStart", true) || (version != shareaPare.getInt("curVersion", 0))) {
                    /* 首次启动 */
                    Editor editor = shareaPare.edit();
                    editor.putBoolean("firstStart", false);
                    editor.putInt("curVersion", version);
                    editor.commit();
                    initIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    /* 临时添加  */
                    //TipDBHelper.stub_tipsDbStore();
                    //TipDBHelper.openDatabase(SplashActivity.this);
                    ShortCutUtil.addShortcut(SplashActivity.this, "附近健身房", R.drawable.ic_gym, ToolGymActivity.class, action);
                } else {
					/* 非首次启动 */
                    //initIntent = new Intent(SplashActivity.this, MainActivity.class);
                    initIntent = new Intent(SplashActivity.this, WelcomeActivity.class);  /* just 4 test */
                }

                SplashActivity.this.startActivity(initIntent);
                SplashActivity.this.finish();
            }
        }, DELAY_VALUE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
