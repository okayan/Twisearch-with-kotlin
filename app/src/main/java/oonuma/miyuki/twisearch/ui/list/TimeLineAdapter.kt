package oonuma.miyuki.twinkle.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import oonuma.miyuki.twisearch.R
import oonuma.miyuki.twisearch.data.Tweet
import oonuma.miyuki.twisearch.databinding.TweetListItemBinding


internal class TimeLineAdapter(
        private val context: Context,
        private val onTimelineSelected: (Long) -> Unit)
    : RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder>() {

    private var tweets: List<Tweet> = ArrayList()

    private val onClickListener = View.OnClickListener {
        onTimelineSelected(it.getTag(R.id.tweet_id) as Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TweetListItemBinding.inflate(layoutInflater, parent, false)
        return TimeLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.binding.root.setTag(R.id.tweet_id, tweet.id)
        holder.binding.tweet = tweet
        holder.binding.tweet!!.loadProfileImage(context)
        holder.binding.originalLinearLayout.setOnClickListener({
            onClickListener.onClick(it)
        })
    }

    override fun getItemCount() = tweets.size

    /**
     * タイムラインを更新
     */
    fun replaceData(tweets: List<Tweet>) {
        this.tweets = tweets
        notifyDataSetChanged()
    }

    class TimeLineViewHolder(var binding: TweetListItemBinding) : RecyclerView.ViewHolder(binding.root)
}