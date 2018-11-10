package com.example.vladi.spacexinfo.fragment

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.vladi.spacexinfo.R
import com.example.vladi.spacexinfo.interfaces.InfoApiInterface
import com.example.vladi.spacexinfo.model.info.Info
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), Callback<Info> {

    private var mContent: View? = null
    private var containerLayout: LinearLayout? = null

    private fun add_row(title: String, value: String?) {
        val child = layoutInflater.inflate(R.layout.item_card, null)
        (child.findViewById<View>(R.id.item_name) as TextView).text = title
        (child.findViewById<View>(R.id.item_value) as TextView).text = value
        containerLayout!!.addView(child)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContent = view
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(activity!!.cacheDir, cacheSize.toLong())

        val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val rocketAPI = retrofit.create(InfoApiInterface::class.java)

        val call = rocketAPI.loadInfo()
        call.enqueue(this)

        Glide.with(context!!).load(IMG_URL).into(mContent!!.findViewById<View>(R.id.backdrop) as ImageView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onResponse(call: Call<Info>, response: Response<Info>) {
        val info = response.body()
        val collapsingToolbar = mContent!!.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbar.title = info!!.name

        containerLayout = mContent!!.findViewById(R.id.linear_container)

        add_row(context!!.getString(R.string.ceo), info.ceo)
        add_row(context!!.getString(R.string.coo), info.coo)
        add_row(context!!.getString(R.string.cto), info.cto)
        add_row(context!!.getString(R.string.cto_propulsion), info.ctoPropulsion)
        add_row(context!!.getString(R.string.employees), info.employees!!.toString())
        add_row(context!!.getString(R.string.vehicles), info.vehicles!!.toString())
        add_row(context!!.getString(R.string.launch_sites), info.launchSites!!.toString())
        add_row(context!!.getString(R.string.test_sites), info.testSites!!.toString())
        add_row(context!!.getString(R.string.founder), info.founder!!.toString())
        add_row(context!!.getString(R.string.founded), info.founded!!.toString())
        add_row(context!!.getString(R.string.valuation), info.valuation!!.toString())
        add_row(context!!.getString(R.string.headquarters), info.headquarters!!.address + ", " + info.headquarters!!.city + ", " + info.headquarters!!.state)


    }

    override fun onFailure(call: Call<Info>, t: Throwable) {

    }

    companion object {
        private val BASE_URL = "https://api.spacexdata.com/"
        private val IMG_URL = "https://www.spacex.com/sites/spacex/files/styles/new_gallery_large/public/2016_-_11-its.jpg?itok=yTl_JcuG"

        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }
}
