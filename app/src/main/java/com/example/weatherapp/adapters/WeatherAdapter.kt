package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class WeatherAdapter(private val listener: Listener?) : ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,
            false
        )
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(view: View, private val listener: Listener?) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)
        private var itemTemp: WeatherModel? = null
        init {
            itemView.setOnClickListener {
                itemTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }

        fun bind(item: WeatherModel) = with(binding) {
            itemTemp = item
            tvDate.text = item.time
            tvCondition.text = item.condition
            tvTemp.text = if (item.currentTemp.isEmpty()) {
                "${item.tempMax}°C/${item.tempMin}°C"
            } else {
                "${item.currentTemp}°C"
            }
            Picasso.get().load("https:" + item.imgUrl).into(iv)
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClick(item: WeatherModel)
    }
}
