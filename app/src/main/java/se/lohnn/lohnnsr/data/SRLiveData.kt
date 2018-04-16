package se.lohnn.lohnnsr.data

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object SRLiveData : LiveData<SRData>() {
    private val INTERVAL = TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS)
    private const val BASE_URL = "http://api.sr.se/"

    private var lastUpdated = 0L
    private var intervalSub: Disposable? = null

    val api: SRApi by lazy {
        val builder = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(SRApi::class.java)
    }

    override fun onActive() {
        val initialDelay = Math.max(0, lastUpdated + TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS) - System.currentTimeMillis())
        intervalSub = Observable.interval(initialDelay, INTERVAL, TimeUnit.MILLISECONDS)
                .map {
                    api.programs().execute()
                }
                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        lastUpdated = System.currentTimeMillis()
                        value = response.body()
                    }
                }, {
                    Timber.e(it, "Something went wrong when trying to get SR data")
                })
        Timber.d("Activating API")
    }

    override fun onInactive() {
        intervalSub?.dispose()
        intervalSub = null
        Timber.d("Deactivating API")
    }
}