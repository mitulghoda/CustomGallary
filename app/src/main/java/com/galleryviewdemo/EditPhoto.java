package com.galleryviewdemo;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditPhoto extends AppCompatActivity {

    ImageView iv_EditPhoto;

    String imagePath ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        idMapping();


    }
    private void idMapping() {

        iv_EditPhoto =  (ImageView) findViewById(R.id.iv_EditPhoto);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        Bundle extras = getIntent().getExtras();
//
//
//        if (extras != null) {
//             imagePath = extras.getString("image");

//            imagePath = Constants.selectedImages.get(0);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                imagePath = extras.getString("image");
            }
            Log.e("imagePath", "" + imagePath);
            setFullImageFromFilePath(iv_EditPhoto, imagePath);






    }
    private Bitmap setFullImageFromFilePath(ImageView imageView, String imageSelectedPath) {
        Bitmap bitmap = BitmapLoader.downSampleBitmap(imageSelectedPath, imageView);
        int imageAngle = UIUtil.getImageAngle(imageSelectedPath);
        Bitmap rotateBitMap = UIUtil.rotateImage(bitmap, imageAngle);
        imageView.setImageBitmap(rotateBitMap);
        return bitmap;
    }

}
