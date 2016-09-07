package com.chenglong.muscle.util;

/**
 * Created by 20 on 2016/9/2.
 */
public class MyPatchUtil {

    static{
        System.loadLibrary("bsdiffjni");
    }

    public static native String bsdiff(String oldFilePath, String newFilePath, String patchFilePath);

    public static native String bspatch(String oldFilePath, String newFilePath, String patchFilePath);

}
