package oonuma.miyuki.twisearch.data.source.remote

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import oonuma.miyuki.twisearch.data.source.TweetsDataSource
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet

object TweetsRemoteDataSource: TweetsDataSource {

    override fun getTimeline(callback: TweetsDataSource.LoadTimelineCallback) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        // statusAPI用のserviceクラス
        val statusesService = twitterApiClient.statusesService
        val call = statusesService.homeTimeline(100, null, null, false, false, false, false)
        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>) {
                callback.onSuccessLoaded(result)
            }

            override fun failure(exception: TwitterException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}