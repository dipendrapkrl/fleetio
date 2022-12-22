package np.pro.dipendra.interview.uilayer.vehiclelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import np.pro.dipendra.interview.R
import np.pro.dipendra.interview.uilayer.displayImageFrom

class VehicleListAdapter(private val onClick: (VehicleItem) -> Unit) :
    PagingDataAdapter<VehicleItem, VehicleListViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {
        return VehicleItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.vehicle_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {
        when (holder) {
            is VehicleItemViewHolder -> {
                holder.bind(getItem(position) as VehicleItem, onClick)
            }
            else -> throw IllegalStateException("unexpected viewType")
        }
    }
}

abstract class VehicleListViewHolder(itemView: View) : ViewHolder(itemView)

class VehicleItemViewHolder(itemView: View) : VehicleListViewHolder(itemView) {
    private val name = itemView.findViewById<TextView>(R.id.name)
    private val make = itemView.findViewById<TextView>(R.id.makeAndModel)
    private val extra = itemView.findViewById<TextView>(R.id.extra)
    private val icon = itemView.findViewById<ImageView>(R.id.icon)
    fun bind(
        info: VehicleItem,
        onClick: (VehicleItem) -> Unit
    ) {
        name.text = info.name
        make.text = info.makeAndModel
        extra.text = info.extra
        icon.displayImageFrom(info.imageUrl)
        itemView.setOnClickListener {
            onClick.invoke(info)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<VehicleItem>() {
    override fun areItemsTheSame(
        oldItem: VehicleItem,
        newItem: VehicleItem
    ): Boolean {
        return oldItem.vehiclesInfo.id == newItem.vehiclesInfo.id
    }

    override fun areContentsTheSame(
        oldItem: VehicleItem,
        newItem: VehicleItem
    ): Boolean {
        return oldItem == newItem
    }

}