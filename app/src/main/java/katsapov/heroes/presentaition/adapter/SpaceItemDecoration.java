package katsapov.heroes.presentaition.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int horizontalOffset;
    private int verticalOffset;

    public SpaceItemDecoration(int horizontalOffset, int verticalOffset) {
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = horizontalOffset;
        outRect.top = verticalOffset;
        outRect.right = horizontalOffset;
        outRect.bottom = verticalOffset;
    }
}
