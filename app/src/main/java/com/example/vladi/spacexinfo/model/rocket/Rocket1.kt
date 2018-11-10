package com.example.vladi.spacexinfo.model.rocket


import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Date
import java.util.concurrent.TimeUnit

import com.bumptech.glide.Glide
import com.example.vladi.spacexinfo.adapter.RocketAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Rocket {

    @SerializedName("rocketid")
    @Expose
    var rocketid: Int = 0
    @SerializedName("id")
    @Expose
    var id: String = ""
    @SerializedName("name")
    @Expose
    var name: String = ""
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("active")
    @Expose
    var active: Boolean = true
    @SerializedName("stages")
    @Expose
    var stages: Int? = null
    @SerializedName("boosters")
    @Expose
    var boosters: Int? = null
    @SerializedName("cost_per_launch")
    @Expose
    var costPerLaunch: Int? = null
    @SerializedName("success_rate_pct")
    @Expose
    var successRatePct: Int? = null
    @SerializedName("first_flight")
    @Expose
    var firstFlight: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("company")
    @Expose
    var company: String? = null
    @SerializedName("height")
    @Expose
    var height: Size? = null
    @SerializedName("diameter")
    @Expose
    var diameter: Size? = null
    @SerializedName("mass")
    @Expose
    var mass: Weight? = null
    @SerializedName("payload_weights")
    @Expose
    var payloadWeights: List<PayloadWeight>? = null
    @SerializedName("first_stage")
    @Expose
    var firstStage: FirstStage? = null
    @SerializedName("second_stage")
    @Expose
    var secondStage: SecondStage? = null
    @SerializedName("engines")
    @Expose
    var engines: Engines? = null
    @SerializedName("landing_legs")
    @Expose
    var landingLegs: LandingLegs? = null
    @SerializedName("wikipedia")
    @Expose
    var wikipedia: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    var thumbnailUrl: String? = null

    fun loadThumbnailUrl(rocketAdapter: RocketAdapter, mContext: Context) {
        // HOLD A REFERENCE TO THE ADAPTER

        if (wikipedia != null && wikipedia != "") {

            RocketTask(rocketAdapter, mContext).execute(wikipedia)
        }
    }

    inner class RocketTask(private val adapter: RocketAdapter, private val mContext: Context) : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String): String? {
            var url: String?
            val prefs = mContext.getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE)
            url = prefs.getString(id, "")

            val date = prefs.getLong(id!! + "_date", Date().time)

            val diffInMillies = Date().time - date
            val days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)

            if (url == "" || days > 7) {
                val wiki_html = load_url(strings[0])
                val doc = Jsoup.parse(wiki_html)
                val pngs = doc.select("img[src$=.jpg]")
                url = "https:" + pngs.first().attr("src")
                val editor = prefs.edit()
                editor.putString(id, url)
                editor.putLong(id!! + "_date", date)
                editor.apply()
            }
            return url
        }

        private fun load_url(ur: String): String {
            var data = ""
            try {
                val url = URL(ur)
                val urlConnection = url.openConnection() as HttpURLConnection

                val stream = BufferedInputStream(urlConnection.inputStream)
                val bufferedReader = BufferedReader(InputStreamReader(stream))
                val builder = StringBuilder()

                var inputString: String
                var hasNext: Boolean = true
                while (hasNext) {
                    inputString = bufferedReader.readLine()
                    hasNext =  inputString != null
                    if(!hasNext) break
                    builder.append(inputString)
                }

                urlConnection.disconnect()
                data = builder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return data
        }

        override fun onPostExecute(url: String) {
            thumbnailUrl = url
            adapter.notifyDataSetChanged()
        }
    }

}
