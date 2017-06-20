package com.galleryviewdemo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;


public class SingleImageAdapter extends BaseAdapter {

    ArrayList<String> mList;
    LayoutInflater mInflater;
    Context mContext;
    SparseBooleanArray mSparseBooleanArray;
    ImageLoader imageLoader;

    public SingleImageAdapter(Context context, ArrayList<String> imageList) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mSparseBooleanArray = new SparseBooleanArray();
        mList = new ArrayList<String>();
        this.mList = imageList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for (int i = 0; i < mList.size(); i++) {
            if (mSparseBooleanArray.get(i)) {
                mTempArry.add(mList.get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int selectedIndex = -1;

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_singlephoto_item, null);
        }
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = mContext.getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density - 20;
        int pixels = (int) (dpWidth * density + 0.5f);
        FrameLayout frameLayout = (FrameLayout) convertView.findViewById(R.id.row_main);
        frameLayout.setLayoutParams(new AbsListView.LayoutParams(pixels / 4, pixels / 4));

        ImageView view = (ImageView) convertView.findViewById(R.id.iv_selected);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        imageLoader.displayImage("file://" + mList.get(position), imageView, PhotoCafeApplication.getProductImageDisplayOption(mContext));
        view.setTag(position);


        if (selectedIndex == position) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

        return convertView;

    }

}
