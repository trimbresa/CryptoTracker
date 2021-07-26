package com.example.cryptotracker.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptotracker.CryptoApp
import com.example.cryptotracker.adapters.CryptoAdapter
import com.example.cryptotracker.databinding.FragmentDashboardBinding
import com.example.cryptotracker.models.CryptoCoinModel
import com.example.cryptotracker.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: CryptoAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val client by lazy {
        OkHttpClient()
    }

    private val request by lazy {
        Request.Builder()
            .url(Constants.apiUrl)
            .cacheControl(
                CacheControl.Builder()
                    .maxAge(1, TimeUnit.DAYS)
                    .build()
            )
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentDashboardBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.cryptoRv.layoutManager = LinearLayoutManager(activity)
        adapter = CryptoAdapter(activity)
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
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getCoins() {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.println(Log.ERROR, "MainActivity.getCoins", "${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = Gson()

                val jsonRes = JSONObject(body)
                val coinsList = jsonRes.getJSONArray("data").toString();
                val cryptoCoinModels: List<CryptoCoinModel> =
                    gson.fromJson(coinsList, object : TypeToken<List<CryptoCoinModel>>() {}.type)

                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                Log.d("sharedPrefsss", "${sharedPref?.all?.keys}")

                val cryptoCoinsList: MutableList<CryptoCoinModel> = mutableListOf()

                cryptoCoinModels.forEach { item ->
                    run {
                        if (sharedPref?.all?.keys?.contains(item.id) == true) {
                            item.isFavorited = true
                            cryptoCoinsList.add(item)
                        }
                    }
                }
                (activity?.application as CryptoApp).cryptoCoinModels = cryptoCoinsList

                activity?.runOnUiThread(Runnable {
                    adapter.updateData(cryptoCoinModels)
                })
            }
        })
    }
}