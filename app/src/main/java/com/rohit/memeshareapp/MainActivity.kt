package com.rohit.memeshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageUrl:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        progressBar.visibility=View.VISIBLE
        // Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            Response.Listener { response ->
                progressBar.visibility=View.GONE
                // Display the first 500 characters of the response string.
                currentImageUrl=response.getString("url")
                Glide.with(this).load(currentImageUrl).into(nameImageView)


            },
            Response.ErrorListener {
                Log.d("error",it.localizedMessage)
            })

// Add the request to the RequestQueue.
 MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun sharememe(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey CheckOut This Cool Meme  $currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share This Meme Using...")
        startActivity(chooser)

    }
    fun nextmeme(view: View) {
        loadMeme()
    }
}
