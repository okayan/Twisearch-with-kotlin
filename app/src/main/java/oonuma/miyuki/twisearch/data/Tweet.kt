package oonuma.miyuki.twisearch.data

import com.twitter.sdk.android.core.models.Card

data class Tweet(val id: Long, val card: Card?, val text: String) {
    override fun toString(): String = text
}