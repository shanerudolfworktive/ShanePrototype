package com.prototype.shane.shaneprototype.view.kotlinFun

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prototype.shane.shaneprototype.R
import com.prototype.shane.shaneprototype.view.BaseFragment
import kotlin.properties.Delegates

/**
 * Created by shane1 on 6/26/17.
 */
class FragmentDetailKotlin : BaseFragment() {
    val kotlinFunAdapter : KotlinFunAdapter = KotlinFunAdapter(ArrayList())
    var recyclerView : RecyclerView by Delegates.notNull()
    var linearLayoutManager : LinearLayoutManager by Delegates.notNull()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_kotlin_fun, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerViewKotlinFun) as RecyclerView
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = kotlinFunAdapter

        kotlinFunAdapter.addMovie("wonder women", "http://www.dccomics.com/sites/default/files/styles/covers192x291/public/gn-covers/2016/02/WW_%2777_v1_56bb921c91b492.66227262.jpg?itok=h8SOTvT6")
        kotlinFunAdapter.addMovie("super man", "https://vignette3.wikia.nocookie.net/superman/images/2/27/Superman-dcuo.jpg/revision/latest?cb=20110901025125")
        kotlinFunAdapter.notifyDataSetChanged()

        return rootView
    }
}