package com.mobileprojectestimator.mobileprojectestimator.Util;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class LruBitmapCache extends LruCache<String, Bitmap>{
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }


    public Bitmap getBitmap(String url) {
        return get(url);
    }


    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}