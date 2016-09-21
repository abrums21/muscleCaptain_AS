package com.chenglong.muscle.custom;

import android.content.Context;
import android.content.Intent;

import com.chenglong.muscle.R;
import com.chenglong.muscle.main.MainActivity;
import com.chenglong.muscle.main.SplashActivity;
import com.chenglong.muscle.tool.MapActivity;

/**
 * Created by 20 on 2016/9/21.
 */

public class MyShortCut {

    private static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String ACTION_MY_SHORTCUT = "android.intent.action.muscle.shortcut.";
    private static final String ACTION_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    public static void addShortcut(Context mContext, String label, int resId, Class mClass, String action) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(mContext, resId));

        Intent launcherIntent = new Intent(mContext, mClass);
        launcherIntent.setAction(ACTION_MY_SHORTCUT + action);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        mContext.sendBroadcast(addShortcutIntent);
    }

    private static void removeShortcut(Context mContext, String label, Class mClass, String action) {

        Intent intent = new Intent(ACTION_REMOVE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);

        Intent launcherIntent = new Intent(mContext, mClass).setAction(ACTION_MY_SHORTCUT + action);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        mContext.sendBroadcast(intent);
    }

}
