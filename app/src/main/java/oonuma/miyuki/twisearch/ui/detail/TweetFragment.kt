package oonuma.miyuki.twisearch.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetUtils
import com.twitter.sdk.android.tweetui.TweetView
import oonuma.miyuki.twisearch.R


class TweetFragment : Fragment() {

    var currentTweetId = -1L

    companion object {
        private const val ARG_TWEET_ID = "tweet_id"
        fun newInstance(tweetId :Long): TweetFragment {
            val newFragment = TweetFragment()
            newFragment.arguments = Bundle().apply {
                putLong(ARG_TWEET_ID, tweetId)
            }
            return newFragment
        }
    }

    private lateinit var tweetLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tweet_view, container, false)
        tweetLayout = view.findViewById(R.id.tweet_container)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentTweetId = arguments?.getLong(ARG_TWEET_ID) ?: 0

        updateTweetView(currentTweetId)
    }

    private fun updateTweetView(tweetId: Long) {
        TweetUtils.loadTweet(tweetId, object : Callback<Tweet>(){
            override fun success(result: Result<Tweet>) {
                tweetLayout.addView(TweetView(activity, result.data))
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(context, R.string.message_network_error, Toast.LENGTH_LONG).show()
            }

        })

    }

    // 画面回転時にも値を保持し続ける
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(ARG_TWEET_ID, currentTweetId)
    }
}


