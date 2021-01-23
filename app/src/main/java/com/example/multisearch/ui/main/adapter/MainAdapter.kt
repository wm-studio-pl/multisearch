package com.example.multisearch.ui.main.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.multisearch.R
import com.example.multisearch.data.model.Offer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*


class MainAdapter(private val onDeleteClick: (Offer) -> Unit) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {
    private var offerList = mutableListOf<Offer>()

    fun setData(list: List<Offer>) {
        offerList.clear()
        offerList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        ) {
            onDeleteClick.invoke(it)
        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setData(offerList[position])
    }

    inner class DataViewHolder(
        itemView: View, val onDeleteClick: (Offer) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun setData(Offer: Offer) {
            itemView.apply {
                textViewOfferName.text = "${Offer.name}"
                val price = String.format("%.2f", Offer.price) + " " + Offer.currency
                textViewPrice.text = price
                if (!Offer.icon.isNullOrBlank())
                {
                    Picasso.get().load(Offer.icon).into(imageViewIcon)
                }
                if (Offer.site!!.startsWith("Allegro", true)) {
                    imageViewSite.setImageResource(R.drawable.allegro_icon)
                } else
                {
                    imageViewSite.setImageResource(R.drawable.olx_icon)
                }
               /* imageViewIcon.setOnClickListener use (Offer.link) {

                }*/
            }

        }

    }
/*
    private fun setOnClick(btn: ImageView, link: String) {
        btn.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(v: View?) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        })
    }*/
}