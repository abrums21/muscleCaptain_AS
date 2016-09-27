package com.chenglong.muscle.engine;

import android.content.Context;
import android.widget.Toast;

import com.chenglong.muscle.util.CryptUtil;
import com.chenglong.muscle.util.PatchUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 20 on 2016/9/5.
 */
public class ApkManager {

    private final String APK_SYS_PATH = "/data/app/";
    //    private final String APK_SYS_PREFIX = "com.chenglong.muscle-";
    private String APK_SYS_NAME;
    //private final String MERGE_PATH;
    private Context context;


    public ApkManager(Context context) {
        //MERGE_PATH = context.getExternalCacheDir().getAbsolutePath() + "/update/";
        this.context = context;
    }

    public boolean apkIsExist(String md5) {
        APK_SYS_NAME = getApkPath();
        if (!APK_SYS_NAME.isEmpty()) {
            String md5Sum = CryptUtil.md5sum(APK_SYS_NAME);
            if (md5Sum.equals(md5)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void copyApk(String apkPath) throws IOException {

        File dest = new File(apkPath);
        if (dest.exists()) {
            dest.delete();
        }

        File src = new File(APK_SYS_NAME);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
        } finally {
            fis.close();
            fos.close();
        }
    }

    public boolean mergeApk(String oldApkName, String newApkName, String patchName) {

        try {
            copyApk(oldApkName);
        } catch (IOException e) {
            return false;
        }
        String show = PatchUtil.bspatch(oldApkName, newApkName, patchName);
//        Toast.makeText(context, show, Toast.LENGTH_LONG).show();
        return true;
    }

    private String getApkPath() {
        File dir = new File(APK_SYS_PATH);
        String filename = "";
        if (!dir.exists()) {
            Toast.makeText(context, "file empty", Toast.LENGTH_LONG).show();
            return filename;
        }

//        File[] subFile = dir.listFiles();
//        String filename = "";
//        if ( (subFile != null ) && (0 != subFile.length)) {
//            for (File iv : subFile) {
//                if (iv.isDirectory()) {
//                    continue;
//                }
//                if (iv.getName().toLowerCase().startsWith(APK_SYS_PREFIX) &&
//                        iv.getName().toLowerCase().endsWith(".apk")) {
//                    filename = iv.getAbsolutePath();
//                }
//            }
//        }

        try {
            filename = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
        } catch (Exception e) {
            ;
        }

        return filename;
    }
}
