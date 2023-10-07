package com.resq360.features.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.resq360.R
import com.resq360.features.utils.Agency

class AgencyAdapter(private val context: Context, private val agencyList: List<Agency>) : BaseAdapter() {

    override fun getCount(): Int {
        return agencyList.size
    }

    override fun getItem(position: Int): Any {
        return agencyList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.agency_card, parent, false)

        val agency = getItem(position) as Agency

        val agencyNameTV = view.findViewById<TextView>(R.id.agencyName)
        val agencyNumberTV = view.findViewById<TextView>(R.id.agencyNumber)
        val distanceTV = view.findViewById<TextView>(R.id.distance)

        agencyNameTV.text = agency.agencyName
        agencyNumberTV.text = agency.agencyNumber
        distanceTV.text = agency.distance

        val strDist = distanceTV.text.toString().substring(0, 2).trim()
        val intDist = strDist.toInt()

        if(intDist <= 5) {
            distanceTV.setTextColor(ContextCompat.getColor(context, R.color.strong_green))
        } else if(intDist in 6..10) {
            distanceTV.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        } else if(intDist > 10) {
            distanceTV.setTextColor(ContextCompat.getColor(context, R.color.orange))
        }

        return view
    }
}
