package oonuma.miyuki.twisearch.ui.list

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.ListView
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import oonuma.miyuki.twinkle.ui.list.TimeLineAdapter
import oonuma.miyuki.twisearch.R
import oonuma.miyuki.twisearch.data.source.TweetsDataSource
import oonuma.miyuki.twisearch.databinding.TweetListFragmentBinding
import oonuma.miyuki.twisearch.ui.detail.TweetFragment
import timber.log.Timber


class TimelineFragment : ListFragment(), TweetsDataSource.LoadTimelineCallback {
    override fun onFailure(e: TwitterException) {

    }

    override fun onSuccessLoaded(result: Result<List<Tweet>>) {
        val tweetList = mutableListOf<oonuma.miyuki.twisearch.data.Tweet>()
        result.data.forEach {
            tweetList.add(oonuma.miyuki.twisearch.data.Tweet(it.id, it.card, it.text))
        }
        adapter.replaceData(tweetList)
    }

    private lateinit var viewDataBinding: TweetListFragmentBinding
    private lateinit var adapter: TimeLineAdapter

    interface OnTimelineSelectedListener {
        fun onTweetSelected(tweetId: Long)
    }

    companion object {
        // nullable
        fun newInstance(): TimelineFragment {
            return TimelineFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = TweetListFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as TimelineActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {


                else -> false
            }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
//        inflater.inflate(R.menu.timeline_fragment_menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = TimeLineAdapter({ tweetId ->
                activity?.let {
                    if (!it.isFinishing) {
                        (it as OnTimelineSelectedListener).onTweetSelected(tweetId)
                    }
                }
            })

            viewDataBinding.list.layoutManager = LinearLayoutManager(context)
            viewDataBinding.list.adapter = adapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.loadTimeline(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()

        val tweetFragment = fragmentManager!!.findFragmentById(R.id.tweet_fragment) as? TweetFragment
        if (tweetFragment != null) {
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        }
    }

}
