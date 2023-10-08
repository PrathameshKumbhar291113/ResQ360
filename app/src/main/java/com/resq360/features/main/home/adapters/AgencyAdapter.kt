package com.resq360.features.main.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.resq360.R
import com.resq360.databinding.AgencyCardBinding
import com.resq360.features.main.home.models.Agency

class AgencyAdapter(
    private val context: Context,
    private val agencyList: List<Agency>,
    private val onItemClick: (Agency) -> Unit,
) : BaseAdapter() {

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
        val binding: AgencyCardBinding
        val view: View

        if (convertView == null) {
            binding = AgencyCardBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as AgencyCardBinding
            view = convertView
        }

        val agency = getItem(position) as Agency

        binding.agencyName.text = agency.agencyName
        binding.agencyNumber.text = agency.agencyNumber
        binding.distance.text = agency.distance

        val strDist = binding.distance.text.toString().substring(0, 2).trim()
        val intDist = strDist.toInt()

        when {
            intDist <= 5 -> binding.distance.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.strong_green
                )
            )

            intDist in 6..10 -> binding.distance.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.yellow
                )
            )

            else -> binding.distance.setTextColor(ContextCompat.getColor(context, R.color.orange))
        }

        binding.contactNowButton.setOnClickListener {
            onItemClick(agency)
        }

        return view
    }


}
