package com.example.rito.sitaca;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by rito on 4/18/2015.
 */

public class RobotoThinTextView extends TextView {
    public RobotoThinTextView (Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/roboto_thin.ttf"));
    }
}
