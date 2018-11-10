package com.example.vladi.spacexinfo.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

class RocketAdapter(private val mContext: Context, private val rocketList: MutableList<Rocket>, var clickListener: RocketAdapterClickListener) : RecyclerView.Adapter<RocketAdapter.MyViewHolder>(), Callback<List<Rocket>> {

    interface RocketAdapterClickListener {
        fun recyclerViewClick(rocketID: String?)
    }

    init {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(mContext.cacheDir, cacheSize.toLong())

        val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val rocketAPI = retrofit.create(RocketApiInterface::class.java)

        val call = rocketAPI.loadRockets()
        call.enqueue(this)
    }

    override fun onResponse(call: Call<List<Rocket>>, response: Response<List<Rocket>>) {

        if (response.isSuccessful) {
            rocketList.clear()
            val rockets = response.body()
            rocketList.addAll(rockets!!)
            for (rocket in rocketList) {
                rocket.loadThumbnailUrl(this, mContext)
            }
            notifyDataSetChanged()
        } else {
            println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<List<Rocket>>, t: Throwable) {
        t.printStackTrace()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.rocket_card, parent, false)

        return MyViewHolder(itemView, object : MyViewHolder.RocketClickListener {
            override fun rocketOnClick(rocketID: String?) {
                clickListener.recyclerViewClick(rocketID)
            }

        })
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rocket = rocketList[position]
        holder.title.text = rocket.name
        holder.count.text = rocket.type
        holder.rocketID = rocket.id
        // loading rocket cover using Glide library
        if (rocket.thumbnailUrl != null) {
            Glide.with(mContext).load(rocket.thumbnailUrl).into(holder.thumbnail)
        }
    }

    override fun getItemCount(): Int {
        return rocketList.size
    }

    class MyViewHolder(view: View, var listener: RocketClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var title: TextView
        var count: TextView
        var thumbnail: ImageView
        var rocketID: String? = null

        //listener passed to viewHolder
        interface RocketClickListener {
            fun rocketOnClick(rocketID: String?)
        }

        init {
            title = view.findViewById(R.id.title)
            count = view.findViewById(R.id.count)
            thumbnail = view.findViewById(R.id.thumbnail)
            view.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            listener.rocketOnClick(this.rocketID)
        }
    }

    companion object {
        internal val BASE_URL = "https://api.spacexdata.com/"
    }
}