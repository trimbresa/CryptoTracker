package com.example.cryptotracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptotracker.R
import com.example.cryptotracker.adapters.CryptoAdapter
import com.example.cryptotracker.adapters.LocalStorageCryptoAdapater
import com.example.cryptotracker.databinding.FragmentDashboardBinding
import com.example.cryptotracker.databinding.FragmentFavoritesBinding
import com.example.cryptotracker.models.CryptoCoinModel
import com.example.cryptotracker.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: LocalStorageCryptoAdapater

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val client by lazy {
        OkHttpClient()
    }

    private val request by lazy {
        Request.Builder()
            .url(Constants.apiUrl)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentFavoritesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.cryptoRv.layoutManager = LinearLayoutManager(activity)
        adapter = LocalStorageCryptoAdapater()
        binding.cryptoRv.adapter = adapter

        getCoins()

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getCoins() {
        // TODO - Retreive favorite coins from FireBase or any other DB
//        client.newCall(request).enqueue(object: Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.println(Log.ERROR, "MainActivity.getCoins","${e.message}")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val body = response.body?.string()
//                val gson = Gson()
//
//                val jsonRes = JSONObject(body)
//                val coinsList = jsonRes.getJSONArray("data").toString();
//                val cryptoCoinModels: List<CryptoCoinModel> = gson.fromJson(coinsList, object : TypeToken<List<CryptoCoinModel>>() {}.type)
//
//                activity?.runOnUiThread(Runnable {
//                    adapter.updateData(cryptoCoinModels)
//                })
//            }
//        })
    }
}