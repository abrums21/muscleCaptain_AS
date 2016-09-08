package com.chenglong.muscle.update;

/**
 * Created by 20 on 2016/9/5.
 */
public class PatchCfg {

    private String name;
    private String url;
    private String md5;

    public PatchCfg(String name, String url, String md5) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.url = url;
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getMd5() {
        return md5;
    }
}
