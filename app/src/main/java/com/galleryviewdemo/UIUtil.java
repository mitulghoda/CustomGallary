package com.galleryviewdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Manthan on 02/09/2015.
 */
public class UIUtil {
    public static Dialog dialog;

    private static ProgressDialog progressDialog;

    public static void log(String message) {
        Log.e("PhotoBook", message);
    }

    public static void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void showDialogCustomString(Context context, String str) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(str);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static int dptopx(Context context, int dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

    public static void changeDialogString(String str) {
        progressDialog.setMessage(str);
    }

    public static void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static ArrayList fetchIdFromJson(String s) {
        ArrayList list = new ArrayList();
        try {

            JSONObject jsonObj = new JSONObject(s);
            JSONArray data = jsonObj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject innerObj = data.getJSONObject(i);
                String id = innerObj.getString("id");
                list.add(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList fetchIdFromJsonFlickr(String s) {
        ArrayList list = new ArrayList();
        try {

            JSONObject jsonObjMain = new JSONObject(s);
            JSONObject jsonObj = jsonObjMain.getJSONObject("photos");
            JSONArray data = jsonObj.getJSONArray("photo");
            for (int i = 0; i < data.length(); i++) {
                JSONObject innerObj = data.getJSONObject(i);
                String id = innerObj.getString("id");
                UIUtil.log("id" + id);
                list.add(id);
            }
        } catch (JSONException e) {
            UIUtil.log("" + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList fetchUrlFromJsonFlickr(String s) {
        ArrayList list = new ArrayList();
        try {

            JSONObject jsonObjMain = new JSONObject(s);
            JSONObject jsonObj = jsonObjMain.getJSONObject("sizes");
            JSONArray data = jsonObj.getJSONArray("size");
            for (int i = 0; i < data.length(); i++) {
                JSONObject innerObj = data.getJSONObject(i);
                if (innerObj.getString("label") != null) {
                    if (innerObj.getString("label").equals("Original")) {
                        String source = innerObj.getString("source");
                        UIUtil.log("source" + source);
                        list.add(source);
                    }
                }
            }
        } catch (JSONException e) {
            UIUtil.log("" + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public static File createImageFile() throws IOException {
        File myDir = new File(Environment.getExternalStorageDirectory() + "/" + "PhotoBook");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(myDir, "photocafe_" + System.currentTimeMillis() + ".jpg");
        return file;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }

    /**
     * @param picturePath
     * @return
     */
    public static int getImageAngle(String picturePath) {
        try {
            ExifInterface ei = new ExifInterface(picturePath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    Log.i("TEST", "orientation : " + 90);
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    Log.i("TEST", "orientation : " + 180);
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    Log.i("TEST", "orientation : " + 270);
                    return 270;
                default:
                    Log.i("TEST", "orientation : " + 0);
                    return 0;
            }

        } catch (IOException e) {
            Log.e("TEST", "" + e.getMessage());
            return 0;
        }

    }



    public static void hideDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    public static String convertDateTime(String fromFormat, String toFormat, String dateOriginalGot) {

        try {
            //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //// Getting Source format here
            SimpleDateFormat fmt = new SimpleDateFormat(fromFormat);

            fmt.setTimeZone(TimeZone.getDefault());

            Date date = fmt.parse(dateOriginalGot);

            //SimpleDateFormat fmtOut = new SimpleDateFormat("EEE, MMM d, ''yyyy");

            //// Setting Destination format here
            SimpleDateFormat fmtOut = new SimpleDateFormat(toFormat);

            return fmtOut.format(date);

        } catch (Exception e) {

            e.printStackTrace();

            e.getMessage();

        }

        return "";

    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }



    public static String getTrimmedStringFromAPI(String value) {

        if (value == null) {
            return "";
        } else if (value.equals("null") || value.equals("Null") || value.equals("NULL")) {

            return "";
        } else if (value.equals("")) {

            return "";
        } else {
            return value;
        }
    }

}
