package se.lohnn.lohnnsr

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import se.lohnn.lohnnsr.data.Program
import se.lohnn.lohnnsr.data.SocialMedia
import se.lohnn.lohnnsr.databinding.ActivityDetailBinding
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val PROGRAM_KEY: String = "programKey"
        fun createIntent(context: Context, program: Program): Intent =
                Intent(context, DetailActivity::class.java).apply {
                    putExtra(PROGRAM_KEY, program)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail).also { binding ->
            binding.data = intent.getSerializableExtra(PROGRAM_KEY) as Program

            binding.handler = object : SocialMediaHandler {
                override fun onClick(socialMedia: SocialMedia) {
                    startActivity(
                            Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(socialMedia.platformurl)
                            })
                    Timber.d("Opening social media $socialMedia")
                }
            }
        }
    }
}

interface SocialMediaHandler {
    fun onClick(socialMedia: SocialMedia)
}
