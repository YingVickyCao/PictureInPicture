package com.example.android.pictureinpicture._2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.android.pictureinpicture.R;

public class Test {
    public static void showADialog(Activity activity) {
        if (null == activity) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog
                .Builder(activity)
                .setTitle("AlertDialog")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("This is an AlertDialog sample")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    public static void showToast(Activity activity) {
        if (null == activity) {
            return;
        }
        Toast.makeText(activity, "This is a sample Toast", Toast.LENGTH_SHORT).show();
    }

    private static AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

    private static AlertDialog.Builder setNegativeButton(
            AlertDialog.Builder builder) {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

}
