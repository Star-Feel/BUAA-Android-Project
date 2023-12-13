package com.example.buaaexercise.group;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;
    private final int cornerRadius;

    public SpaceItemDecoration(Context context, @DimenRes int spacingDimen, @DimenRes int cornerRadiusDimen) {
        this.spacing = context.getResources().getDimensionPixelSize(spacingDimen);
        this.cornerRadius = context.getResources().getDimensionPixelSize(cornerRadiusDimen);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;

        // 设置圆角
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), cornerRadius);
            }

        });
    }
}
