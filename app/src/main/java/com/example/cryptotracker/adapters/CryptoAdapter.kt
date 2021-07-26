package com.example.cryptotracker.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R
import com.example.cryptotracker.models.CryptoCoinModel
import com.example.cryptotracker.utils.Utils
import com.squareup.picasso.Picasso
import java.util.*


class CryptoAdapter(val activity: FragmentActivity?) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
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
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.coin_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val coin = cryptoCoinModels.get(position)

        viewHolder.coinName.text = coin.name
        viewHolder.coinSymbol.text = coin.symbol
        viewHolder.priceUsdText.text = Utils.roundTrailingZeros(coin.quote.USD.price)
        viewHolder.priceChangeOneHour.text =
            Utils.roundTrailingZeros(coin.quote.USD.percent_change_1h)
        viewHolder.priceChange24H.text = Utils.roundTrailingZeros(coin.quote.USD.percent_change_24h)
        viewHolder.priceChange7Day.text = Utils.roundTrailingZeros(coin.quote.USD.percent_change_7d)

        viewHolder.favoriteCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            onChangedListener(buttonView, isChecked, coin.id)
        }

        viewHolder.favoriteCheckbox.isChecked = coin.isFavorited

        Picasso.get().load(Utils.buildImageUrl(coin.id))
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher)
            .into(viewHolder.coinIcon)
    }

    private fun onChangedListener(view: CompoundButton, checked: Boolean, coinId: String) {
        cryptoCoinModels[0].isFavorited = checked

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        with(sharedPref.edit()) {
            if (checked) {
                Log.d("put",  coinId)
                putString(coinId, coinId)
            } else {
                Log.d("removed",  coinId)

                remove(coinId)
            }
            apply()
        }

    }

    fun updateData(cryptoCoinModels: List<CryptoCoinModel>) {
        this.cryptoCoinModels = cryptoCoinModels
        notifyDataSetChanged()
    }

    /**
     *  lets the Adapter know how many items to display
     */
    override fun getItemCount(): Int = cryptoCoinModels.size
}

