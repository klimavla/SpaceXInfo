package com.example.vladi.spacexinfo.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.vladi.spacexinfo.R
import com.example.vladi.spacexinfo.interfaces.RocketApiInterface
import com.example.vladi.spacexinfo.model.rocket.Rocket
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RocketDetailFragment : Fragment(), Callback<Rocket> {


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
        return inflater.inflate(R.layout.fragment_rocket_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var rocketId: String? = ""
        if (savedInstanceState == null) {
            val args = arguments
            rocketId = args!!.getString(KEY_ROCKETID)
        } else {
        }

        // initialize views
        mContent = view

        containerLayout = mContent!!.findViewById(R.id.linear_container)
        val gson = GsonBuilder().setLenient().create()

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(context!!.cacheDir, cacheSize.toLong())

        val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val rocketAPI = retrofit.create(RocketApiInterface::class.java)

        val call = rocketAPI.loadRocket(rocketId)
        call.enqueue(this)


        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                fragmentManager!!.popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onResponse(call: Call<Rocket>, response: Response<Rocket>) {
        val rocket = response.body()
        val collapsingToolbar = mContent!!.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbar.title = rocket!!.name
        val prefs = context!!.getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE)
        Glide.with(context!!).load(prefs.getString(rocket.id, "")).into(mContent!!.findViewById<View>(R.id.backdrop) as ImageView)


        val countryCode = resources.configuration.locale.country.toUpperCase()
        var imperial = false
        when (countryCode) {
            "US", "LR", "MM" -> imperial = true
        }
        add_row(getString(R.string.type), rocket.type)
        add_row(getString(R.string.height), if (imperial) rocket.height!!.feet!!.toString() + getString(R.string.feet) else rocket.height!!.meters!!.toString() + getString(R.string.meter))
        add_row(getString(R.string.diameter), if (imperial) rocket.diameter!!.feet!!.toString() + getString(R.string.feet) else rocket.diameter!!.meters!!.toString() + getString(R.string.meter))
        add_row(getString(R.string.active), if (rocket.active) getString(R.string.active) else getString(R.string.inactive))
        add_row(getString(R.string.description), rocket.description)
        add_row(getString(R.string.success_rate), rocket.successRatePct!!.toString())
        add_row(getString(R.string.first_flight), rocket.firstFlight)
        add_row(getString(R.string.country), rocket.country)
        add_row(getString(R.string.company), rocket.company)
    }

    override fun onFailure(call: Call<Rocket>, t: Throwable) {

    }

    companion object {
        internal val BASE_URL = "https://api.spacexdata.com/"
        internal val KEY_ROCKETID = "rocket_id"

        fun newInstance(rocketId: String?): Fragment {
            val frag = RocketDetailFragment()
            val args = Bundle()
            args.putString(KEY_ROCKETID, rocketId)
            frag.arguments = args
            return frag
        }
    }
}