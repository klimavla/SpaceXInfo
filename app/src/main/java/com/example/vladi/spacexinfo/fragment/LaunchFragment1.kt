package com.example.vladi.spacexinfo.fragment

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.vladi.spacexinfo.R
import com.example.vladi.spacexinfo.adapter.LaunchAdapter
import com.example.vladi.spacexinfo.interfaces.RocketApiInterface
import com.example.vladi.spacexinfo.model.ServiceGenerator
import com.example.vladi.spacexinfo.model.launch.Launch
import com.example.vladi.spacexinfo.model.rocket.Rocket
import com.google.gson.Gson

import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Calendar

import retrofit2.Call

class LaunchFragment : Fragment() {


    private var mRecycler: RecyclerView? = null
    private var rocketAdapter: ArrayAdapter<String>? = null
    private var ids: ArrayList<String>? = null
    private var init = true

    /**
     * Converting dp to pixel
     */
    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        ids = ArrayList()
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecycler = view.findViewById(R.id.recycler_view)
        val prefs = context!!.getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE)

        val launchList = ArrayList<Launch>()
        val adapter = LaunchAdapter(context!!, launchList, "", "")//launchList,prefs.getString("filter_year",""),prefs.getString("filter_rocket_id",""));

        val mLayoutManager = GridLayoutManager(context, 2)
        mRecycler!!.layoutManager = mLayoutManager
        mRecycler!!.addItemDecoration(LaunchFragment.GridSpacingItemDecoration(2, dpToPx(10), true))
        mRecycler!!.itemAnimator = DefaultItemAnimator()
        mRecycler!!.adapter = adapter
        val spinnerYear = view.findViewById<Spinner>(R.id.yearSpinner)
        val spinnerRocket = view.findViewById<Spinner>(R.id.rocketSpinner)
        val list = ArrayList<String>()

        val max_year = Calendar.getInstance().get(Calendar.YEAR)
        list.add(getString(R.string.all))

        for (year in max_year downTo 2006) {
            list.add("" + year)
        }

        val yearAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, list)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter

        rocketAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, ArrayList())
        rocketAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRocket.adapter = rocketAdapter

        val gson = Gson()

        val ids_string = prefs.getString("rocket_ids", "")
        val ids_helper = gson.fromJson(ids_string, Array<String>::class.java)
        val names = gson.fromJson(prefs.getString("rocket_names", ""), Array<String>::class.java)


        if (ids_helper == null) {
            val t = Thread(RocketTask())
            t.start()
        } else {
            ids!!.addAll(Arrays.asList(*ids_helper))
            rocketAdapter!!.clear()
            rocketAdapter!!.addAll(*names)
            rocketAdapter!!.notifyDataSetChanged()
        }
        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (!init)
                    adapter.setYear(yearAdapter.getItem(i))
                //prefs.edit().putString("filter_year",yearAdapter.getItem(i)).apply();
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        spinnerRocket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (!init)
                    adapter.setRocketId(ids!![i])
                init = false
                //prefs.edit().putString("filter_rocket_id",rocketAdapter.getItem(i)).apply();
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
     class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }


    internal inner class RocketTask : Runnable {

        override fun run() {
            val taskService = ServiceGenerator.createService(RocketApiInterface::class.java)
            val call = taskService.loadRockets()
            try {
                val rocketList = call.execute().body()
                val names = ArrayList<String>()
                ids!!.clear()
                ids!!.add(activity!!.getString(R.string.all))
                names.add(activity!!.getString(R.string.all))
                if (rocketList != null) {
                    for (rocket in rocketList) {
                        ids!!.add(rocket.id)
                        names.add(rocket.name)
                    }
                    val prefs = context!!.getSharedPreferences("com.example.vladi", Context.MODE_PRIVATE)
                    val gson = Gson()
                    prefs.edit().putString("rocket_ids", gson.toJson(ids)).apply()
                    prefs.edit().putString("rocket_names", gson.toJson(names)).apply()
                    if (activity != null) {
                        activity!!.runOnUiThread {
                            rocketAdapter!!.clear()
                            rocketAdapter!!.addAll(names)
                            rocketAdapter!!.notifyDataSetChanged()
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    companion object {

        fun newInstance(): Fragment {
            return LaunchFragment()
        }
    }
}
