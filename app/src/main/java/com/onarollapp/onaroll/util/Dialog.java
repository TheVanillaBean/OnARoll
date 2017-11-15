package com.onarollapp.onaroll.util;

import android.content.Context;
import android.icu.text.NumberFormat;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

public class Dialog {

    public static void showDialog(Context context, String title, String content, String positiveText){
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .autoDismiss(true)
                .typeface("Roboto-Regular.ttf", "Roboto-Light.ttf")
                .show();
    }


    public static MaterialDialog showProgressIndeterminateDialog(Context context, String title, String content, boolean horizontal){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .autoDismiss(false)
                .progressIndeterminateStyle(horizontal);

        return builder.build();
    }

    public static MaterialDialog showProgressDeterminateDialog(Context context, String title, String content, boolean horizontal, boolean showMinMax, int max){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(false, max, showMinMax);

        return builder.build();
    }
}
