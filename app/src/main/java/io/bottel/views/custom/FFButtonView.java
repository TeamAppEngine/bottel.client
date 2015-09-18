package io.bottel.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import io.bottel.utils.FontHelper;

/**
 * Created by Omid on 9/18/2015.
 */
public class FFButtonView extends Button {
    public FFButtonView(Context context) {
        super(context);
        fixFont();
    }

    public FFButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fixFont();
    }

    public FFButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fixFont();
    }

    private void fixFont() {
        setTypeface(FontHelper.getDefault(getContext()));
    }
}
