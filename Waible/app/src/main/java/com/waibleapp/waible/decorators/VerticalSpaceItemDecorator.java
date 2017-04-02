package com.waibleapp.waible.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public class VerticalSpaceItemDecorator extends RecyclerView.ItemDecoration {

    private  final int spacer;

    public VerticalSpaceItemDecorator(int spacer) {
        this.spacer = spacer;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = spacer;
    }
}
