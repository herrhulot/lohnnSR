package se.lohnn.lohnnsr

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import se.lohnn.lohnnsr.data.SRLiveData
import se.lohnn.lohnnsr.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        SRLiveData.observe(this, Observer { data ->
            binding.setVariable(BR.data, data)
            binding.executePendingBindings()
            Timber.d(data.toString())
        })
    }
}
