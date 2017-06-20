package com.galleryviewdemo;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> imageUrls;
    final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
    final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

    TextView txt_Title;
    ImageView iv_Back;
    GridView gridView;
    Cursor imagecursor;
    SingleImageAdapter imageAdapter;

    TextView btn_Select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idMapping();
        setGallaryImages();
        setOnclick();
    }
    private void idMapping() {

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        btn_Select = (TextView) findViewById(R.id.btn_proceed);
    }
    private void setOnclick() {

        btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//
                int selectedpos = imageAdapter.getSelectedIndex();
                Log.e("selected pos","" + selectedpos);


                if (selectedpos == -1) {
                    Toast.makeText(MainActivity.this, "Please select one photo ", Toast.LENGTH_SHORT).show();
                }

                else{
                    imageUrls.get(selectedpos);
                    Log.e("selected image", "" + imageUrls.get(selectedpos));

                    Intent i = new Intent(MainActivity.this,EditPhoto.class);
                    i.putExtra("image", imageUrls.get(selectedpos));

                    startActivity(i);

                }


            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (imageAdapter.getSelectedIndex() != position) {
                    imageAdapter.setSelectedIndex(position);
                } else {
                    imageAdapter.setSelectedIndex(-1);
                }
                imageAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setGallaryImages() {
        imagecursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");
        imageUrls = new ArrayList<String>();
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
        }

        imageAdapter = new SingleImageAdapter(this, imageUrls);

        gridView.setAdapter(imageAdapter);
        imagecursor.close();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (imagecursor != null) {
            imagecursor.close();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (imagecursor != null) {
            imagecursor.close();
        }
    }
}
