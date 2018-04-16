package se.lohnn.lohnnsr.data

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

object SRLiveData : LiveData<SRData>() {
    private val INTERVAL = 60L
    private var intervalSub: Disposable? = null

    private val tjosan = AtomicInteger(0)

    override fun onActive() {
        //Should not be needed, but w/e
        intervalSub?.dispose()
        intervalSub = Observable.interval(0, INTERVAL, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe({
                    value = SRData("Yoyoyo ${tjosan.getAndIncrement()}")
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

data class SRData(val name: String)
