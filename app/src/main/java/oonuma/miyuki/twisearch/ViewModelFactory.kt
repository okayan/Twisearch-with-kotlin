package oonuma.miyuki.twisearch

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import oonuma.miyuki.twisearch.data.source.TweetsRepository
import oonuma.miyuki.twisearch.data.source.remote.TweetsRemoteDataSource
import oonuma.miyuki.twisearch.ui.list.TimelineViewModel


class ViewModelFactory private constructor(
        private val application: Application,
        private val tweetsRepository: TweetsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(TimelineViewModel::class.java) ->
                        TimelineViewModel(application, tweetsRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

                }
            } as T


    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {

                    INSTANCE ?: ViewModelFactory(application,
                            TweetsRepository.getInstance(TweetsRemoteDataSource)!!)
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
