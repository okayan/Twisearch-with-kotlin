package oonuma.miyuki.twisearch.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.util.Log
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import oonuma.miyuki.twisearch.data.source.TweetsDataSource
import oonuma.miyuki.twisearch.data.source.TweetsRepository


class TimelineViewModel(context: Application, private val repository: TweetsRepository) : AndroidViewModel(context) {

    val tweets: ObservableList<oonuma.miyuki.twisearch.data.Tweet> = ObservableArrayList()

    fun loadTimeline() {
        repository.getTimeline(object : TweetsDataSource.LoadTimelineCallback{
            override fun onSuccessLoaded(result: Result<List<Tweet>>) {
                result.data.forEach {
                    val myTweet = oonuma.miyuki.twisearch.data.Tweet(it.id, it.text, it.user.profileImageUrl)
                    tweets.add(myTweet)
                }
            }

            override fun onFailure(e: TwitterException) {

            }
        })
    }
}
