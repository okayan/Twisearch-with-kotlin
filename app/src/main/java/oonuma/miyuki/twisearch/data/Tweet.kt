package oonuma.miyuki.twisearch.data

import android.content.Context
import android.content.res.Resources
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.twitter.sdk.android.core.models.Card
import oonuma.miyuki.twisearch.R
import java.lang.Exception

data class Tweet(val id: Long, val card: Card?, val text: String, var profileImageUrl: String) {
    override fun toString(): String = text
    val profileImage : ObservableField<Drawable> = ObservableField()
    private lateinit var bindableFieldTarget: BindableFieldTarget
    private lateinit var picasso: Picasso

    fun loadProfileImage(context: Context) {
        // Picassoはターゲットへの参照持続が弱いため
        bindableFieldTarget = BindableFieldTarget(profileImage, context.resources)
        picasso = Picasso.Builder(context).build()
        picasso.load(profileImageUrl).placeholder(R.drawable.profile_image_placeholder).into(bindableFieldTarget)
    }

    class BindableFieldTarget(val observableField: ObservableField<Drawable>, var resources: Resources) : Target {

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            observableField.set(placeHolderDrawable)
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            observableField.set(errorDrawable)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            observableField.set(BitmapDrawable(resources, bitmap))
        }

    }
}