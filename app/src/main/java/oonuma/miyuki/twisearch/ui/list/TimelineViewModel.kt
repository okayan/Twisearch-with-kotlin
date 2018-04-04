package oonuma.miyuki.twisearch.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import oonuma.miyuki.twisearch.data.source.TweetsDataSource
import oonuma.miyuki.twisearch.data.source.TweetsRepository
import android.support.v7.widget.RecyclerView
import android.databinding.BindingAdapter



class TimelineViewModel(context: Application, private val repository: TweetsRepository) : AndroidViewModel(context) {

    val tweets: MutableList<oonuma.miyuki.twisearch.data.Tweet> = ArrayList()

    @BindingAdapter("bind:items")
    fun entries(recyclerView: RecyclerView, array: Array<String>) {
        //write your code to set RecyclerView adapter.
    }

    fun loadTimeline(callback: TweetsDataSource.LoadTimelineCallback) {
        repository.getTimeline(object : TweetsDataSource.LoadTimelineCallback{
            override fun onSuccessLoaded(result: Result<List<Tweet>>) {
                result.data.forEach {
                    val myTweet = oonuma.miyuki.twisearch.data.Tweet(it.id, it.card, it.text)
                    tweets.add(myTweet)
                }
                callback.onSuccessLoaded(result)
            }

            override fun onFailure(e: TwitterException) {

            }
        })
    }
}
