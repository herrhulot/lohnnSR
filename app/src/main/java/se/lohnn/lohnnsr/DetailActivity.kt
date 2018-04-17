package se.lohnn.lohnnsr

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import se.lohnn.lohnnsr.data.Program

class DetailActivity : AppCompatActivity() {

    companion object {
        private const  val PROGRAM_KEY: String = "programKey"
        fun createIntent(context: Context, program: Program): Intent =
                Intent(context, DetailActivity::class.java).apply {
                    putExtra(PROGRAM_KEY, program)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}
