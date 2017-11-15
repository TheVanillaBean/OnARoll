package com.onarollapp.onaroll.util;

import android.view.View;

import java.util.List;

/**
 * Created by Alex on 2/18/2017.
 */

public class RecyclerUtil {

    public static void showViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
}
