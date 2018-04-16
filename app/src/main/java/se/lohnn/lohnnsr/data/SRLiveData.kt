package se.lohnn.lohnnsr.data

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object SRLiveData : LiveData<SRData>() {
    private const val INTERVAL = 60L
    private const val BASE_URL = "http://api.sr.se/"

    private var intervalSub: Disposable? = null

    val api: SRApi by lazy {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(SRApi::class.java)
    }

    override fun onActive() {
        intervalSub = Observable.interval(0, INTERVAL, TimeUnit.SECONDS)
                .map {
                    api.programs().execute()
                }
                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
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

data class SRData(val copyright: String)
