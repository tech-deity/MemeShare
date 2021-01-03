package com.ofw.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
var imgurl:String ?= null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme(){
        val img=findViewById<ImageView>(R.id.img)
        val pb=findViewById<ProgressBar>(R.id.pb)
        pb.visibility=View.VISIBLE
        val url="https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest= JsonObjectRequest(
                                Request.Method.GET, url,null,
                                 {
                                    response -> imgurl=response.getString("url")
                                                Glide.with(this)
                                                .load(imgurl)
                                                .listener(object:RequestListener<Drawable>{

                                                 override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                                        pb.visibility=View.GONE

                                                     return false
                                }

                                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                                                      pb.visibility=View.GONE
                                                        return false
                                }

                            }).into(img)

                },
                {
                    Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_LONG).toString()
                }
        )
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun shareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check this out our cool memes i got from redidt $imgurl")
        val chooser=Intent.createChooser(intent,"Share thos meme to your friends")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}