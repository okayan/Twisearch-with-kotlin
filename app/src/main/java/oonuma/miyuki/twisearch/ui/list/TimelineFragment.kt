package oonuma.miyuki.twisearch.ui.list

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oonuma.miyuki.twinkle.ui.list.TimeLineAdapter
import oonuma.miyuki.twisearch.R
import oonuma.miyuki.twisearch.databinding.FragmentTimelineBinding
import oonuma.miyuki.twisearch.ui.detail.TweetFragment
import timber.log.Timber


class TimelineFragment : ListFragment() {

    private lateinit var viewDataBinding: FragmentTimelineBinding
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
        viewDataBinding = FragmentTimelineBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as TimelineActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null && context != null) {
            adapter = TimeLineAdapter(context!!, { tweetId ->
                activity?.let {
                    if (!it.isFinishing) {
                        (it as OnTimelineSelectedListener).onTweetSelected(tweetId)
                    }
                }
            })

            viewDataBinding.list.layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                    LinearLayoutManager(activity).orientation)
            viewDataBinding.list.addItemDecoration(dividerItemDecoration)
            viewDataBinding.list.adapter = adapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.loadTimeline()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()

        val tweetFragment = fragmentManager!!.findFragmentById(R.id.tweet_fragment) as? TweetFragment
        if (tweetFragment != null) {
//            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        }
    }

}
