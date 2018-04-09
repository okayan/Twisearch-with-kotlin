package oonuma.miyuki.twisearch.data

import android.content.Context
import android.content.res.Resources
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.twitter.sdk.android.core.models.Card
import com.twitter.sdk.android.core.models.TweetEntities
import oonuma.miyuki.twisearch.R
import java.lang.Exception

data class Tweet(val id: Long, val text: String, var profileImageUrl: String) {
    override fun toString(): String = text

    val profileImage: ObservableField<Drawable> = ObservableField()
    private lateinit var bindableFieldTarget: BindableFieldTarget

    fun loadProfileImage(context: Context) {
        bindableFieldTarget = BindableFieldTarget(profileImage, context.resources)
        Picasso.with(context).load(profileImageUrl).placeholder(R.drawable.profile_image_placeholder).into(bindableFieldTarget)
    }

    class BindableFieldTarget(val observableField: ObservableField<Drawable>, var resources: Resources) : Target {
        override fun onBitmapFailed(errorDrawable: Drawable?) {
            observableField.set(errorDrawable)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            observableField.set(placeHolderDrawable)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            observableField.set(BitmapDrawable(resources, bitmap))
        }

    }
}