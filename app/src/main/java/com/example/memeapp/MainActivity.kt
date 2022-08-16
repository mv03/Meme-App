package com.example.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImageURL:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    private fun loadmeme(){
        progressbar.visibility=View.VISIBLE

        val url = "https://meme-api.herokuapp.com/gimme"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                currentImageURL=response.getString("url")
                Glide.with(this).load(currentImageURL).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ) :Boolean{
                        progressbar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }
                }).into(imageView)
            },
            Response.ErrorListener { error ->

            }
        )
        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/palin"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey! checkout this cool meme $currentImageURL")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadmeme()
    }
}