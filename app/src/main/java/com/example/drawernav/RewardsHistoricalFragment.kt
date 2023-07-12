package com.example.drawernav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drawernav.components.MyAdapter
import com.example.drawernav.models.RewardHistory
import com.example.drawernav.models.RewardsModel
import com.example.trashcash_mobile.network.ApiClient
import com.example.trashcash_mobile.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RewardsHistoricalFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var apiInterface: ApiInterface
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_rewards_historical, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RewardsHistoricalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadData(){
        apiInterface.getHistoricalRewards().enqueue(
            object : Callback<List<RewardHistory>> {
                override fun onResponse(call: Call<List<RewardHistory>>, response: Response<List<RewardHistory>>) {
                    if (response.isSuccessful) {
                        val rewardsHistoricalList = response.body()
                        showData(rewardsHistoricalList)
                    }
                }
                override fun onFailure(call: Call<List<RewardHistory>>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Failed to communicate with the server", Toast.LENGTH_SHORT).show()
                }
            }

        )
    }

    private fun showData(rewardsHistoricalList:List<RewardHistory>?){
        if (rewardsHistoricalList!== null){
            val adapter = MyAdapter(rewardsHistoricalList)
            recyclerView.adapter = adapter
            val context = requireContext()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }
}