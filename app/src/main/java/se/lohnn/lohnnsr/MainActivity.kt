package se.lohnn.lohnnsr

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import se.lohnn.lohnnsr.data.SRLiveData
import se.lohnn.lohnnsr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rv.layoutManager = LinearLayoutManager(this)
        val adapter = SRAdapter { program ->
            startActivity(DetailActivity.createIntent(this, program))
        }
        binding.rv.adapter = adapter

        SRLiveData.observe(this, Observer { data ->
            adapter.updateItems(data?.programs ?: emptyList())
        })
    }
}
