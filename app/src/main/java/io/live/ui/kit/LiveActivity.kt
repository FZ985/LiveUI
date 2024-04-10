package io.live.ui.kit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.live.ui.databinding.LiveActivityLivePageBinding


/**
 * by JFZ
 * 2024/4/10
 * desc：
 **/
class LiveActivity : AppCompatActivity() {

    private val binding: LiveActivityLivePageBinding by lazy {
        LiveActivityLivePageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}