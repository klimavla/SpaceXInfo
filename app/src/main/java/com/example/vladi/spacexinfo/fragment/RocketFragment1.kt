package com.example.vladi.spacexinfo.fragment

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vladi.spacexinfo.R
import com.example.vladi.spacexinfo.adapter.RocketAdapter
import com.example.vladi.spacexinfo.model.rocket.Rocket

import java.util.ArrayList

class RocketFragment : Fragment() {

    private var mRecycler: RecyclerView? = null

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecycler = view.findViewById(R.id.recycler_view)

        val rocketList = ArrayList<Rocket>()
        val adapter = RocketAdapter(context!!, rocketList, object : RocketAdapter.RocketAdapterClickListener {
            override fun recyclerViewClick(rocketID: String?) {
                val frag = RocketDetailFragment.newInstance(rocketID)
                val ft = activity!!.supportFragmentManager.beginTransaction()
                ft.replace(R.id.frame_container, frag, frag.tag).addToBackStack("detail")
                ft.commit()
            }
        })

        val mLayoutManager = GridLayoutManager(context, 2)
        mRecycler!!.layoutManager = mLayoutManager
        mRecycler!!.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        mRecycler!!.itemAnimator = DefaultItemAnimator()
        mRecycler!!.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

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

    companion object {

        fun newInstance(): Fragment {
            return RocketFragment()
        }
    }
}
