package io.bottel.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Omid on 9/18/2015.
 */
public class FontHelper {
    private static final String APP_DEFAULT_FONT_PATH = "Fonts/ubuntuTitle.ttf";

    private static Typeface defaultFont = null;

    public static Typeface getDefault(Context context) {
        if (defaultFont == null)
            defaultFont = Typeface.createFromAsset(context.getAssets(), APP_DEFAULT_FONT_PATH);
        return defaultFont;
    }

    public static void setDefaultFor(Context context, TextView... views) {
        Typeface koodak = getDefault(context);
        for (TextView view : views) {
            view.setTypeface(koodak);
        }
    }
}
