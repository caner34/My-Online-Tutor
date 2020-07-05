package com.onlinehocam.ozel.ders.okul;

import android.content.Context;
import android.widget.LinearLayout;

public class CustomTutorLinearLayout extends LinearLayout {

    int tutorID;
    boolean isShowCommentTriggered;
    CustomSolutionCommentInjector customSolutionCommentInjector;

    public CustomTutorLinearLayout(Context context, LinearLayout child, int tutorID) {
        super(context);
        this.tutorID = tutorID;
        isShowCommentTriggered = false;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        child.setLayoutParams(lp);
        lp.bottomMargin = 10;
        this.setLayoutParams(lp);

        this.addView(child);
    }
}
