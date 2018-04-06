package oonuma.miyuki.twisearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.activity_login.login_button
import com.twitter.sdk.android.core.TwitterException
import oonuma.miyuki.twisearch.ui.list.TimelineActivity
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context : Context) : Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val context = this
        login_button.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                val userId = result.data.userId
                val userName = result.data.userName

                //ユーザ情報取得
                Timber.d("なまえ　　　:$userName")
                Timber.d("ユーザーID  :$userId")
                startActivity(TimelineActivity.newInstance(context))
                finish()
            }

            override fun failure(exception: TwitterException) {
                // 公式Twitterクライアントなどでログインしてない
                Toast.makeText(context, R.string.message_login_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login_button.onActivityResult(requestCode, resultCode, data)
    }
}