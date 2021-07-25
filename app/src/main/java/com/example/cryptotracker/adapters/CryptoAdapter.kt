package com.example.cryptotracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R
import com.example.cryptotracker.models.CryptoCoinModel
import com.example.cryptotracker.utils.Utils
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.*


class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    private var cryptoCoinModels: List<CryptoCoinModel> = Collections.emptyList()

    /**
    * Provide a reference to the type of views that you are using
    * (custom ViewHolder).
    */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var coinName: TextView = view.findViewById(R.id.coinName)
        var coinSymbol: TextView = view.findViewById(R.id.coinSymbol)
        var coinIcon: ImageView = view.findViewById(R.id.coinIcon)
        var priceUsdText: TextView = view.findViewById(R.id.priceUsdText)
        var priceChangeOneHour: TextView = view.findViewById(R.id.percentChange1hText)
        var priceChange24H: TextView = view.findViewById(R.id.percentChange24hText)
        var priceChange7Day: TextView = view.findViewById(R.id.percentChange7dayText)
        var favoriteCheckbox: CheckBox = view.findViewById(R.id.favorite_checkbox)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.coin_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val coin = cryptoCoinModels.get(position)

        viewHolder.coinName.text = coin.name
        viewHolder.coinSymbol.text = coin.symbol
        viewHolder.priceUsdText.text = Utils.roundTrailingZeros(coin.quote.USD.price)
        viewHolder.priceChangeOneHour.text = Utils.roundTrailingZeros(coin.quote.USD.percent_change_1h)
        viewHolder.priceChange24H.text = Utils.roundTrailingZeros(coin.quote.USD.percent_change_24h)
        viewHolder.priceChange7Day.text = Utils.roundTrailingZeros(coin.quote.USD.percent_change_7d)
        viewHolder.favoriteCheckbox.setOnClickListener{
            onClick(it, coin.id)
        }

        Picasso.get().load(Utils.buildImageUrl(coin.id))
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher)
            .into(viewHolder.coinIcon)
    }

    fun updateData(cryptoCoinModels: List<CryptoCoinModel>) {
        this.cryptoCoinModels = cryptoCoinModels
        notifyDataSetChanged()
    }

    private fun onClick(view: View?, id: String) {
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("message")

//        myRef.setValue("Hello, World!");

        Toast.makeText(view?.context, "Pressed on coin: ${id}", Toast.LENGTH_SHORT).show()
    }

    /**
     *  lets the Adapter know how many items to display
     */
    override fun getItemCount(): Int = cryptoCoinModels.size
}

