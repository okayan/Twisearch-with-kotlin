package oonuma.miyuki.twisearch.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tweet_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentTweetId = arguments?.getLong(ARG_TWEET_ID) ?: 0

        updateTweetView(currentTweetId)
    }

    fun updateTweetView(tweetId: Long) {
//        viewModel.cheeses.observe(this, Observer { adapter.setCheeses(it) })
//        val article = activity!!.findViewById(R.id.tweet) as TextView
//        article.text = Ipsum.Tweets[position]
//        currentPosition = position
    }

    // 画面回転時にも値を保持し続ける
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(ARG_TWEET_ID, currentTweetId)
    }
}


