package pl.holker.music_app_android.functionalities.typical_notes.model

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val _spanCount: Int,
    private val _spacing: Int,
    private val _includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % _spanCount
        if (_includeEdge) {
            outRect.left = _spacing - column * _spacing / _spanCount
            outRect.right = (column + 1) * _spacing / _spanCount
            if (position < _spanCount) {
                outRect.top = _spacing
            }
            outRect.bottom = _spacing
        } else {
            outRect.left = column * _spacing / _spanCount
            outRect.right = _spacing - (column + 1) * _spacing / _spanCount
            if (position >= _spanCount) {
                outRect.top = _spacing
            }
        }
    }
}