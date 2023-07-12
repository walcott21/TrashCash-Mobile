package com.example.drawernav

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drawernav.components.MyAdapter
import com.example.drawernav.components.RewardItemAdpater
import com.example.drawernav.models.RewardsModel
import com.example.trashcash_mobile.network.ApiClient
import com.example.trashcash_mobile.network.ApiInterface
import com.example.trashcash_mobile.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RewardsFragment : Fragment() {
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_rewards, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        loadData()
        return rootView
    }

    private fun loadData(){
        apiInterface.getRewards().enqueue(
            object : Callback<List<RewardsModel>> {
                override fun onResponse(call: Call<List<RewardsModel>>, response: Response<List<RewardsModel>>) {
                    if (response.isSuccessful) {
                        val rewardsList = response.body()
                        showData(rewardsList)
                    }
                }

                override fun onFailure(call: Call<List<RewardsModel>>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Failed to communicate with the server", Toast.LENGTH_SHORT).show()
                }
            }

        )
    }

    private fun showData(rewardsList:List<RewardsModel>?){
        if (rewardsList!== null){
            val adapter = RewardItemAdpater(rewardsList)
            recyclerView.adapter = adapter
            val context = requireContext()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to exchange your points for this reward?")
            .setPositiveButton("Yes") { dialog, which ->
                // Handle the positive button click (e.g., perform the reward exchange)
            }
            .setNegativeButton("No") { dialog, which ->
                // Handle the negative button click (e.g., do nothing)
            }
            .show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RewardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
