package io.live.ui.kit

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.leading.LeadingLoadStateAdapter
import io.live.ui.LiveTest
import io.live.ui.databinding.LiveActivityLivePageBinding
import io.live.ui.kit.adapter.LiveMessageDiffAdapter
import io.live.ui.kit.entry.LiveUiMessage
import io.live.ui.kit.utils.LivePageHelper
import io.live.ui.kit.widgets.LiveFixedLinearLayoutManager
import io.uicomponents.systemui.StatusBarHelper


/**
 * by JFZ
 * 2024/4/10
 * desc：
 **/
class LiveActivity : AppCompatActivity() {

    private val liveHelper = LivePageHelper()

    private var isLoopAnim = true


//    private val adapter: LiveMessageAdapter by lazy {
//        LiveMessageAdapter()
//    }

    private val adapter: LiveMessageDiffAdapter by lazy {
        LiveMessageDiffAdapter()
    }

    private lateinit var adapterHelper: QuickAdapterHelper

    private val layoutManager: LiveFixedLinearLayoutManager by lazy {
        LiveFixedLinearLayoutManager(this).apply {
            stackFromEnd = true
        }
    }

    private val binding: LiveActivityLivePageBinding by lazy {
        LiveActivityLivePageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarHelper.setStatusBarColor(this, Color.TRANSPARENT)
        binding.liveRecycler.layoutManager = layoutManager
        binding.liveRecycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 10.dp
            }
        })
        adapterHelper = QuickAdapterHelper.Builder(adapter)
            .setLeadingLoadStateAdapter(object : LeadingLoadStateAdapter.OnLeadingListener {
                override fun onLoad() {
                    loadMore()
                }

                override fun isAllowLoading(): Boolean {
                    return true
                }
            })
            .build()
        binding.liveRecycler.adapter = adapterHelper.adapter
        binding.liveRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                binding.liveRecycler.removeCallbacks(mRunnable)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //停止
                    val position = layoutManager.findLastVisibleItemPosition()
                    if (position == adapter.items.size - 1) {
                        isLoopAnim = true
                        run()
                    } else {
                        isLoopAnim = false
                    }
                } else {
                    isLoopAnim = false
                }
            }
        })
        adapter.setOnItemClickListener { _, _, position ->
            Toast.makeText(this, "pos:$position", Toast.LENGTH_SHORT).show()
        }

        liveHelper.bindPage(this)
        adapter.submitList(LiveTest.msgs())
        scrollToEnd()
        adapterHelper.leadingLoadState = LoadState.NotLoading(false)
    }

    override fun onStart() {
        super.onStart()
        liveHelper.onStart()
    }

    override fun onBackPressed() {
        if (!liveHelper.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        liveHelper.onDestroy()
        super.onDestroy()
    }

    fun getPageBinding(): LiveActivityLivePageBinding {
        return binding
    }


    private fun scrollToEnd() {
        layoutManager.scrollToPosition(adapter.itemCount - 1)
    }

    private fun scrollToEndWithOffset() {
        layoutManager.scrollToPositionWithOffset(adapter.itemCount - 1, 0)
    }

    //  ------------------------------------------ test start ------------------------------------------

    private val mRunnable = {
        if (isLoopAnim) {
            adapter.add(LiveTest.randomText(LiveTest.randomNumber(100, 200).toString()))
            scrollToEndWithOffset()
            run()
        }
    }

    private fun run() {
        if (isLoopAnim) {
            binding.liveRecycler.removeCallbacks(mRunnable)
            binding.liveRecycler.postDelayed(mRunnable, 1000)
        }
    }

    private fun loadMore() {
        adapterHelper.leadingLoadState = LoadState.Loading
        binding.liveRecycler.postDelayed({
            adapterHelper.leadingLoadState = LoadState.NotLoading(false)
            val list = mutableListOf<LiveUiMessage>()
            for (i in 0 until 10) {
                list.add(LiveTest.randomText("lo"))
            }
            adapter.addAll(0, list)
        }, 600)
    }

//  ------------------------------------------ test end ------------------------------------------
}