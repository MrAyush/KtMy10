package com.example.ayushgupta.ktmy10

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import java.io.InputStream
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

    }

    fun getImage(v: View) {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        val isConnected = networkInfo?.isConnectedOrConnecting == true
        if (isConnected!!) {
            val myTask = MyTask(this)
            myTask.execute()
        } else {
            Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show()
        }
    }

    class MyTask(private val mActivity: MainActivity) : AsyncTask<Unit, Unit, String>() {
        var _is: InputStream? = null
        @SuppressLint("StaticFieldLeak")
        var pb1: ProgressBar? = null
        override fun doInBackground(vararg params: Unit?): String? {
            val url = URL("https://picsum.photos/700/900/?random")
            _is = url.openStream()
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            pb1 = mActivity.findViewById(R.id.pb1)
            pb1?.visibility = View.VISIBLE
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val bitmap = BitmapFactory.decodeStream(_is)
            val iv1: ImageView = mActivity.findViewById(R.id.iv1)
            iv1.setImageBitmap(bitmap)
            pb1?.visibility = View.GONE
        }
    }
}