package com.chenglong.muscle.entity;

import android.graphics.Bitmap;

public class PuzzleBlock {
    private int itemId;
    private int bitmapId;
    private Bitmap bpBlock;

    public PuzzleBlock(int itemId, int bitmapId, Bitmap bpBlock) {
        this.itemId = itemId;        /* 序号从1开始  */
        this.bitmapId = bitmapId;    /* 序号从1开始  */
        this.bpBlock = bpBlock;
    }

    public int getBitmapId() {
        return bitmapId;
    }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }

    public int getItemId() {
        return itemId;
    }

    public Bitmap getBitmap() {
        return bpBlock;
    }

    public void setBpBlock(Bitmap bpBlock) {
        this.bpBlock = bpBlock;
    }

    public void swapBlock(PuzzleBlock blankItems) {
        // TODO Auto-generated method stub
        int id = getBitmapId();
        setBitmapId(blankItems.getBitmapId());
        blankItems.setBitmapId(id);

        Bitmap bitmap = getBitmap();
        setBpBlock(blankItems.getBitmap());
        blankItems.setBpBlock(bitmap);
    }
}
