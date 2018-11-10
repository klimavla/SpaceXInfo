package com.example.vladi.spacexinfo.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.vladi.spacexinfo.R
import com.example.vladi.spacexinfo.interfaces.LaunchApiInterface
import com.example.vladi.spacexinfo.model.launch.Launch
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LaunchAdapter(private val mContext: Context, private val launchList: MutableList<Launch>, private var year: String?, private var rocketId: String?) : RecyclerView.Adapter<LaunchAdapter.MyViewHolder>(), Callback<List<Launch>> {
    private val utcFormat: DateFormat
    private val showFormat: DateFormat
    private var launchApiInterface: LaunchApiInterface? = null

    init {
        this.utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")//2008-09-28T23:15:00.000Z
        this.showFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        init_retrofit()
        load_data(year, rocketId)
    }


    fun setRocketId(id: String) {
        rocketId = id
        load_data(year, rocketId)
    }

    fun setYear(year: String) {
        this.year = year
        load_data(year, rocketId)
    }


    private fun init_retrofit() {
        val gson = GsonBuilder().setLenient().create()

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(mContext.cacheDir, cacheSize.toLong())
        val okHttpClient = OkHttpClient.Builder().cache(cache).build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()

        launchApiInterface = retrofit.create(LaunchApiInterface::class.java)
    }

    private fun load_data(year: String?, rocket: String?) {
        var year = year
        if (mContext?.getString(R.string.all) == year) year = ""
        if (mContext?.getString(R.string.all) == rocketId) rocketId = ""
        val url = launchApiInterface!!.loadLaunches(year, rocketId).request().url().toString()
        val call = launchApiInterface!!.loadLaunches(year, rocket)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<List<Launch>>, response: Response<List<Launch>>) {

        Log.w("LaunchAdapter", "response")
        if (response.isSuccessful) {
            launchList.clear()
            val launches = response.body()
            launchList.addAll(launches!!)

            notifyDataSetChanged()
        } else {
            println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<List<Launch>>, t: Throwable) {
        t.printStackTrace()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.launch_card, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaunchAdapter.MyViewHolder, position: Int) {
        val launch = launchList[position]
        holder.mission_name.text = launch.missionName
        var newDate: Date? = null
        var date = launch.launchDateUtc
        try {
            newDate = utcFormat.parse(launch.launchDateUtc)
            date = showFormat.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        holder.flight_number.text = "#" + launch.flightNumber.toString()
        holder.date.text = date
        holder.rocket.text = launch.rocket!!.rocketName
        Glide.with(mContext).load(launch.links!!.missionPatchSmall).into(holder.thumbnail)

    }

    override fun getItemCount(): Int {
        return launchList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mission_name: TextView
        var flight_number: TextView
        var date: TextView
        var rocket: TextView
        var thumbnail: ImageView

        init {
            mission_name = view.findViewById(R.id.mission_name)
            flight_number = view.findViewById(R.id.flight_number)
            date = view.findViewById(R.id.date)
            rocket = view.findViewById(R.id.rocket)
            thumbnail = view.findViewById(R.id.thumbnail)
            view.setOnClickListener { }
        }
    }

    companion object {
        internal val BASE_URL = "https://api.spacexdata.com/"
    }
}