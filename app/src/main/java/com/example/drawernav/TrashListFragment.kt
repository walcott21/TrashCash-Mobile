package com.example.drawernav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drawernav.components.MyAdapter
import com.example.drawernav.components.TrashItemAdapter
import com.example.drawernav.models.RewardsModel
import com.example.drawernav.models.TrashList
import com.example.trashcash_mobile.network.ApiClient
import com.example.trashcash_mobile.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrashListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrashListFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
        loadData()
        rootView = inflater.inflate(R.layout.fragment_trash_list, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        return rootView
    }

    private fun loadData(){
        apiInterface.getTrashList().enqueue(
            object : Callback<List<TrashList>> {
                override fun onResponse(call: Call<List<TrashList>>, response: Response<List<TrashList>>) {
                    if (response.isSuccessful) {
                        val trashList = response.body()
                        showData(trashList)
                    }
                }

                override fun onFailure(call: Call<List<TrashList>>, t: Throwable) {
//                    Toast.makeText(applicationContext, "Failed to communicate with the server", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun showData(trashList:List<TrashList>?){
        if (trashList!== null){
            val adapter = TrashItemAdapter(trashList)
            recyclerView.adapter = adapter
            val context = requireContext()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrashListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrashListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}