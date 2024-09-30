package com.capco.widgets.flips

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.capco.support.images.shareScreenshot
import com.capco.support.permission.PermissionCallbacks
import com.capco.support.permission.hasPermissions
import com.capco.support.views.toast
import com.capco.widgets.R
import com.capco.widgets.databinding.ActivityFlipsBinding
import com.capco.widgets.webview.WebViewActivity
import se.emilsjolander.flipview.FlipView
import se.emilsjolander.flipview.OverFlipMode
import java.lang.Math.abs


class FlipsActivity : AppCompatActivity(), FlipsCallback, FlipView.OnFlipListener, FlipView.OnOverFlipListener,
    FlipsOnClickListener,
GestureDetector.OnGestureListener {

    lateinit var binding: ActivityFlipsBinding

    private lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_flips)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        create()
    }

    private fun create() {
        receive()
        init()
        set()
    }

    lateinit var receivedFlipsWidgetData: FlipsWidgetData
    var receivedFlipId : String? = null
    private fun receive() {
        receivedFlipsWidgetData = intent.getSerializableExtra("data") as FlipsWidgetData
        receivedFlipId = intent.getStringExtra("id")
    }

    private fun init() {
        gestureDetector = GestureDetector(this,this)

        binding.idBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun set() {
        flips()
    }

    private fun flips() {
        prepareFlips()
    }

    @SuppressLint("StaticFieldLeak")
    var flipView: FlipView? = null
    var flipAdapter: FlipsAdapter? = null
    @SuppressLint("ClickableViewAccessibility")
    private fun prepareFlips() {
        flipView = binding.flipView
        flipView?.overFlipMode = OverFlipMode.RUBBER_BAND
        
        flipAdapter = FlipsAdapter(this)
        flipAdapter?.setOnClickListener(this)
        flipAdapter?.setCallback(this)

        flipView?.adapter = flipAdapter
        flipView?.setOnFlipListener(this)
        flipView?.setBackgroundColor(resources.getColor(R.color.colorBlack))
        flipView?.overFlipMode = OverFlipMode.RUBBER_BAND
        flipView?.setOnOverFlipListener(this)

        receivedFlipsWidgetData.flipsItems.let {
            flipAdapter?.setItems(it)
            flipAdapter?.notifyDataSetChanged()
            if (receivedFlipId.isNullOrEmpty())
                flipView?.smoothFlipTo(0)
        }

        receivedFlipId?.let { receivedFlipId ->
            receivedFlipsWidgetData.flipsItems.forEachIndexed { index, flip ->
                if (receivedFlipId == flip.id){
                    flipView?.flipTo(index)
                }
            }
        }

//        flipView?.setOnTouchListener(OnSwipeTouchListener(this, object : OnSwipeTouchListener.OnSwipeListener{
//            override fun onSwipeLeft() {
//                toast("left")
//            }
//
//            override fun onSwipeRight() {
//                toast("right")
//            }
//        }))

//        flipView?.setOnTouchListener(object: OnSwipeTouchListenerNew() {
//            override fun onSwipeLeft(): Boolean {
//                toast("left")
//                return false
//            }
//
//            override fun onSwipeRight(): Boolean {
//                toast("right")
//                return false
//            }
//        })
    }

    override fun onPageRequested(page: Int) {
        flipView?.smoothFlipTo(page)
    }

    override fun onFlippedToPage(v: FlipView?, position: Int, id: Long) {
        Log.i("pageflip", "Page: "+position)
    }

    override fun onOverFlip(
        v: FlipView?,
        mode: OverFlipMode?,
        overFlippingPrevious: Boolean,
        overFlipDistance: Float,
        flipDistancePerPage: Float
    ) {
        Log.i("overflip", "overFlipDistance = "+overFlipDistance)
    }

    override fun onCLickScrollToTop() {
        flipView?.smoothFlipTo(0)
    }

    override fun onClickReadMore(item: FlipsItem) {
        readMore(item.url!!)
    }

    override fun onClickShare(item: FlipsItem, view : View) {
        hasPermissions(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
            object : PermissionCallbacks {
                override fun hasAllGranted() {
                    val shareMessage = item.title.toString()
                    shareScreenshot(view,"Article",shareMessage)
                }

                override fun hasRational() {
                }

                override fun hasPermissionDenied() {
                    toast("Storage permission required to share news !")
                }
            })
    }

    private fun readMore(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("data",url)
        startActivity(intent)
    }

    // Override this method to recognize touch event
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }

    // All the below methods are GestureDetector.OnGestureListener members
    // Except onFling, all must "return false" if Boolean return type
    // and "return" if no return type
    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {
        return
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        p0: MotionEvent?,
        e1: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        return
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        if (e1!=null){
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
//                        toast("left to right")
                        }
                        else {
//                        toast("right to left")
                        }
                    }
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        return true
    }
}
//fun NewsWidgetData.toFlipsWidgetData() : FlipsWidgetData {
//    return FlipsWidgetData(
//        flips = news.map {
//            Flip(
//                id = it.id,
//                image = it.cover,
//                title = it.title,
//                description = it.text,
//                url = it.uri,
//                source = it.info?.by,
//                time = it.info?.whenX
//            )
//        }.toMutableList()
//    )
//}