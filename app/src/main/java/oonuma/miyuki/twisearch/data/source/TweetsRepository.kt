package oonuma.miyuki.twisearch.data.source

import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet

class TweetsRepository(val tweetsDataSource: TweetsDataSource) : TweetsDataSource{

    //static methods
    companion object {

        @Volatile
        private var instance: TweetsRepository? = null

        fun getInstance(tweetsDataSource: TweetsDataSource): TweetsRepository? {
            if (instance == null) {
                synchronized(TweetsRepository::class.java) {
                    if (instance == null) {
                        instance = TweetsRepository(tweetsDataSource)
                    }
                }
            }
            return instance
        }

        fun destroyInstance() { instance = null }
    }

    override fun getTimeline(callback: TweetsDataSource.LoadTimelineCallback) {
        tweetsDataSource.getTimeline(object :TweetsDataSource.LoadTimelineCallback {
            override fun onSuccessLoaded(result: Result<List<Tweet>>) {
                callback.onSuccessLoaded(result)
            }

            override fun onFailure(e: TwitterException) { callback.onFailure(e) }
        })

    }
}
