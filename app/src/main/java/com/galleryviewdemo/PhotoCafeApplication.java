package com.galleryviewdemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;


public class PhotoCafeApplication extends Application {
    private static DisplayImageOptions displayImageOptions, displayImageOptions1,displayImageOptions2;
    private static ImageLoaderConfiguration config;
    private ImageLoader imageLoader;
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static DisplayImageOptions getProductImageDisplayOption(Context context) {

        if (displayImageOptions == null) {
            displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();


            return displayImageOptions;
        } else {
            return displayImageOptions;
        }
    }



    public static void initImageLoader(Context context) {
        if (config == null) {
            config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCacheSize(100 * 1024 * 1024) // 100 Mb

                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs() // Remove for release app
                    .build();
        }
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        System.gc();

    }
}
