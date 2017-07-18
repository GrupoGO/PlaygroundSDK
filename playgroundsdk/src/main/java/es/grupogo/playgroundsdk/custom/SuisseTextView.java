package es.grupogo.playgroundsdk.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import es.grupogo.playgroundsdk2.R;

/**
 * Created by carlosolmedo on 18/7/17.
 */

public class SuisseTextView extends AppCompatTextView {

    private static final int REGULAR = 0;
    private static final int BOLD = 1;
    private static final int BLACK = 2;

    private int fontType = BOLD;

    public SuisseTextView(Context context) {
        super(context);

        init(context, null);
    }

    public SuisseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public SuisseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        getAttributes(attrs);
        applyCustomFont(context);

    }

    private void getAttributes(AttributeSet attrs) {

        if (attrs!=null) {

            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.SuisseTextView);
            fontType = attrsArray.getInt(R.styleable.SuisseTextView_type, BOLD);
            attrsArray.recycle();
        }
    }

    private void applyCustomFont(Context context) {
        String font = "";

        switch (fontType) {
            case REGULAR:
                font = ("fonts/Suisse Regular.otf");
                break;
            case BOLD:
                font = ("fonts/Suisse Bold.otf");
                break;
            case BLACK:
                font = ("fonts/Suisse Black.otf");
                break;
        }

        Log.i("typeface", font);

        Typeface customFont = FontCache.getTypeface(font, context);
        setTypeface(customFont);
    }
}
