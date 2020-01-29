package com.example.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

const val URL = "https://www.google.com"

class MainActivity : AppCompatActivity() {

    private var cleanHistory = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (cleanHistory) {
                    cleanHistory = false
                    web_view.clearHistory()
                }
                super.onPageFinished(view, url)

                if (web_view.canGoBack())
                    ivBack.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorBlack, null))
                else
                    ivBack.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorGray, null))

                if (web_view.canGoForward())
                    ivFrwrd.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorBlack, null))
                else
                    ivFrwrd.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorGray, null))
            }
        }

        web_view.webViewClient = webClient
        web_view.loadUrl(URL)

        ivBack.setOnClickListener{
            if (web_view.canGoBack())
                web_view.goBack()
        }

        ivFrwrd.setOnClickListener{
            if (web_view.canGoForward())
                web_view.goForward()
        }

        ivHome.setOnClickListener{
            web_view.loadUrl(URL)
            cleanHistory = true
        }

        btnGO.setOnClickListener {
            if (etURL.text.toString() != "")
                web_view.loadUrl(etURL.text.toString())
            else
                Toast.makeText(this, "EMPTY URL!", Toast.LENGTH_SHORT).show()
        }
    }
}
