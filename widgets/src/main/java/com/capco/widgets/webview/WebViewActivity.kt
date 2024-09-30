package com.capco.widgets.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capco.support.display.lightForeground
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.R
import com.capco.widgets.databinding.ActivityWebviewBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce

class WebViewActivity : AppCompatActivity() {

    private var currentUrl : String = ""

    private lateinit var binding : ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        create()
    }

    private fun create() {
        receive()
        init()
        set()
    }

    private var receivedUrl : String? = null
    private fun receive() {
        receivedUrl = intent.getStringExtra("data")
    }

    private fun init(){
        lightForeground()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        progressWebView(true)
        Handler().postDelayed({
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            progressWebView(false)
        }, 1500)

        receivedUrl?.let {
            currentUrl = it
        }
        binding.idSwipeRefreshLayout.setOnRefreshListener {
            binding.idWebView.loadUrl(currentUrl)
        }

        val wave : Sprite = DoubleBounce()
        wave.color = resources.getColor(R.color.overlay_dark_70)
        binding.progress.indeterminateDrawable = wave

    }

    private fun set() {
        webView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webView() {
        binding.idWebView.settings.javaScriptEnabled = true
        binding.idWebView.settings.domStorageEnabled = true
        binding.idWebView.settings.saveFormData = true

        binding.idWebView.settings.databaseEnabled = true

        binding.idWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.idWebView.settings.useWideViewPort = true
        binding.idWebView.settings.loadWithOverviewMode =true
        binding.idWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.idWebView.settings.allowContentAccess = true
        binding.idWebView.settings.allowFileAccess = true
        binding.idWebView.settings.allowFileAccessFromFileURLs = true
        binding.idWebView.settings.allowUniversalAccessFromFileURLs = true

        binding.idWebView.settings.loadsImagesAutomatically = true

        binding.idWebView.isClickable = true
        binding.idWebView.settings.setSupportZoom(true)
        binding.idWebView.settings.builtInZoomControls = true
        binding.idWebView.isHorizontalScrollBarEnabled = true
        binding.idWebView.isVerticalScrollBarEnabled = true
        binding.idWebView.settings.setEnableSmoothTransition(true)
        binding.idWebView.settings.useWideViewPort = true

//        binding.idWebView.settings.setPluginsEnabled(true);
        binding.idWebView.settings.pluginState = WebSettings.PluginState.ON

        progressWebView(true)
        object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressWebView(false)
                binding.idSwipeRefreshLayout.isRefreshing = false
                currentUrl = url
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }.also { binding.idWebView.webViewClient = it }

        binding.idWebView.isClickable = true
        binding.idWebView.webChromeClient = object : WebChromeClient(){
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Toast.makeText(this@WebViewActivity, url.toString(), Toast.LENGTH_SHORT).show()
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsPrompt(
                view: WebView?,
                url: String?,
                message: String?,
                defaultValue: String?,
                result: JsPromptResult?
            ): Boolean {
                Toast.makeText(this@WebViewActivity, url.toString(), Toast.LENGTH_SHORT).show()
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }
        }

        binding.idWebView.loadUrl(currentUrl)
        binding.idWebView.requestFocus()

    }

    private fun progressWebView(isLoading: Boolean) {
        if (isLoading){
            binding.progress.show()
            binding.idProgressLayout.show()
            binding.idContent.hide()
        } else {
            binding.progress.hide()
            binding.idProgressLayout.hide()
            binding.idContent.show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.idWebView.canGoBack()) {
                        binding.idWebView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}