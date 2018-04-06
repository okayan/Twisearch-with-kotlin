package oonuma.miyuki.twisearch.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentActivity
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import oonuma.miyuki.twisearch.LoginActivity
import oonuma.miyuki.twisearch.R
import oonuma.miyuki.twisearch.ViewModelFactory
import oonuma.miyuki.twisearch.ui.detail.TweetFragment

class TimelineActivity : AppCompatActivity(),
        TimelineFragment.OnTimelineSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, TimelineActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nav)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setNavigationDrawer()

        val sessionManager = TwitterCore.getInstance().sessionManager
        if (sessionManager.activeSession == null) {
            startActivity(LoginActivity.newInstance(this))
            finish()

        } else {

            // 実行環境が通常レイアウトかラージ端末用レイアウトか確認
            if (findViewById<View>(R.id.content) != null) {
                if (savedInstanceState != null) {
                    return
                }
                supportFragmentManager.beginTransaction()
                        .add(R.id.content, TimelineFragment.newInstance())
                        .commit()
            }

            Log.d(TimelineActivity.toString(), "なまえ　　　:" + sessionManager.activeSession.userName)
            Log.d(TimelineActivity.toString(), "ユーザーID  :" + sessionManager.activeSession.userId)
        }

    }

    private fun setNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile_navigation_menu_item -> {
                    // TODO プロフィール画面起動
                }
            }
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    drawerLayout.openDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }


    override fun onTweetSelected(tweetId: Long) {
        val tweetFragment = supportFragmentManager.findFragmentById(R.id.tweet_fragment) as? TweetFragment
        if (tweetFragment != null) {
            // 2ペインの場合
            tweetFragment.updateTweetView(tweetId)
        } else {
            // 1ペインの場合
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TweetFragment.newInstance(tweetId))
                    .addToBackStack(null)
                    .commit()
        }
    }

    fun obtainViewModel(): TimelineViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(TimelineViewModel::class.java)

}
