package oonuma.miyuki.twisearch.ui.list

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import oonuma.miyuki.twinkle.ui.list.TimeLineAdapter
import oonuma.miyuki.twisearch.data.Tweet


object TimeLineBindings {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: RecyclerView, items: List<Tweet>) {
        with(listView.adapter as TimeLineAdapter) {
            replaceData(items)
        }
    }
}