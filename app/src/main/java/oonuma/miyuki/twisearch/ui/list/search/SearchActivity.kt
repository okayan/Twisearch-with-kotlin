package oonuma.miyuki.twisearch.ui.list.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.twitter.sdk.android.tweetui.SearchTimeline
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import oonuma.miyuki.twisearch.R


class SearchActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private lateinit var toolbar: Toolbar
    private lateinit var searchEditText :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchEditText = findViewById(R.id.et_search)

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_search -> {
                setAdapter()
                true
            }
            else -> false
        }

    private fun setAdapter() {
        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)
        var word = searchEditText.text.toString()
        val searchTimeline = SearchTimeline.Builder()
                .query(word)
                .maxItemsPerRequest(50)
                .build()

        val adapter = TweetTimelineRecyclerViewAdapter.Builder(this)
                .setTimeline(searchTimeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build()

        recyclerView.adapter = adapter
        recyclerView.adapter.notifyDataSetChanged()
    }

}
