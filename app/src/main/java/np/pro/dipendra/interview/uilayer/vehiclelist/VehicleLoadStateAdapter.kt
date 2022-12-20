package np.pro.dipendra.interview.uilayer.vehiclelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import np.pro.dipendra.interview.R
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleLoadStateAdapter.NextPageButtonViewHolder

class VehicleLoadStateAdapter(private val onClick: () -> Unit) :
    LoadStateAdapter<NextPageButtonViewHolder>() {

    override fun onBindViewHolder(
        holder: NextPageButtonViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState, onClick)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NextPageButtonViewHolder {
        return NextPageButtonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.vehicle_paginate_item, parent, false)
        )
    }

    class NextPageButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.text)
        fun bind(loadState: LoadState, onClick: () -> Unit) {
            when (loadState) {
                is LoadState.Loading -> {
                    name.isVisible = true
                    name.text = itemView.context.getString(R.string.please_wait)
                    name.setOnClickListener(null)
                }
                is LoadState.Error -> {
                    name.isVisible = true
                    name.text = itemView.context.getString(R.string.next_page_load_failure_msg)
                    name.setOnClickListener { onClick.invoke() }
                }
                else -> {
                    name.isVisible = false
                    name.setOnClickListener(null)
                }
            }
        }
    }


}