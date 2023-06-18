package net.panacota.app.ui.listeners

import androidx.recyclerview.widget.RecyclerView

class EndlessRecyclerOnScrollListener(private val onScrollEnd: () -> Unit)
    : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(1)) {
            onScrollEnd()
        }
    }
}