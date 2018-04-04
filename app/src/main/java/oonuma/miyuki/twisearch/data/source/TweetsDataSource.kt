package oonuma.miyuki.twisearch.data.source

import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet


interface TweetsDataSource {
    interface LoadTimelineCallback {
        fun onSuccessLoaded(result : Result<List<Tweet>>)

        fun onFailure(e : TwitterException)
    }

    fun getTimeline(callback: LoadTimelineCallback)
}