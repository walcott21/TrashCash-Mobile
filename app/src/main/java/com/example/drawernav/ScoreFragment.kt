package com.example.drawernav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.drawernav.models.Rank
import com.example.drawernav.models.ScoreboardEntry
import com.example.trashcash_mobile.network.ApiClient
import com.example.trashcash_mobile.network.ApiInterface
import com.example.trashcash_mobile.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ScoreFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score, container, false)

        fetchScoreboardData()

        return view
    }

    private fun fetchScoreboardData() {
        val apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        apiInterface.getScoreboardData().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                if (response.isSuccessful) {
//                    val apiResponse = response.body()
                    var rank = Rank("Name",222,1)
                    var listRank = listOf(rank, rank, rank)
                    val apiResponse = ScoreboardEntry(323,77,listRank)
                    if (apiResponse != null) {
                        val scoreboard = apiResponse as ScoreboardEntry
                        displayScoreboard(scoreboard)
                    } else {
                        displayNoScoreboard()
                    }
//                } else {
//                    Log.e("ScoreFragment", "API Error: ${response.message()}")
//                    displayError()
//                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("ScoreFragment", "API Failure: ${t.message}")
                displayError()
            }
        })
    }

    private fun displayScoreboard(scoreboard: ScoreboardEntry?) {
        val yourPointsTextView: TextView? = view?.findViewById(R.id.your_points)
        val rank1NameTextView: TextView? = view?.findViewById(R.id.rank1_name)
        val rank1PointsTextView: TextView? = view?.findViewById(R.id.rank1_points)
        val rank1PositionTextView: TextView? = view?.findViewById(R.id.rank1_position)
        val rank2NameTextView: TextView? = view?.findViewById(R.id.rank2_name)
        val rank2PointsTextView: TextView? = view?.findViewById(R.id.rank2_points)
        val rank2PositionTextView: TextView? = view?.findViewById(R.id.rank2_position)
        val rank3NameTextView: TextView? = view?.findViewById(R.id.rank3_name)
        val rank3PointsTextView: TextView? = view?.findViewById(R.id.rank3_points)
        val rank3PositionTextView: TextView? = view?.findViewById(R.id.rank3_position)
        val noScoreboardTextView: TextView? = view?.findViewById(R.id.noScoreboardTextView)
        val errorTextView: TextView? = view?.findViewById(R.id.errorTextView)

        if (scoreboard != null) {
            yourPointsTextView?.text = "Your Points: ${scoreboard.yourPoints}"

            if (scoreboard.globalRanking.isNotEmpty()) {
                val rank1 = scoreboard.globalRanking[0]
                rank1NameTextView?.text = rank1.name
                rank1PointsTextView?.text = "Points: ${rank1.points}"
                rank1PositionTextView?.text = "Position: ${rank1.position}"
            }

            if (scoreboard.globalRanking.size >= 2) {
                val rank2 = scoreboard.globalRanking[1]
                rank2NameTextView?.text = rank2.name
                rank2PointsTextView?.text = "Points: ${rank2.points}"
                rank2PositionTextView?.text = "Position: ${rank2.position}"
            }

            if (scoreboard.globalRanking.size >= 3) {
                val rank3 = scoreboard.globalRanking[2]
                rank3NameTextView?.text = rank3.name
                rank3PointsTextView?.text = "Points: ${rank3.points}"
                rank3PositionTextView?.text = "Position: ${rank3.position}"
            }

            // Hide the "No Scoreboard" TextView and error TextView
            noScoreboardTextView?.visibility = View.GONE
            errorTextView?.visibility = View.GONE
        } else {
            // Display the "No Scoreboard" TextView and hide other TextViews
            noScoreboardTextView?.visibility = View.VISIBLE
            yourPointsTextView?.visibility = View.GONE
            rank1NameTextView?.visibility = View.GONE
            rank1PointsTextView?.visibility = View.GONE
            rank1PositionTextView?.visibility = View.GONE
            rank2NameTextView?.visibility = View.GONE
            rank2PointsTextView?.visibility = View.GONE
            rank2PositionTextView?.visibility = View.GONE
            rank3NameTextView?.visibility = View.GONE
            rank3PointsTextView?.visibility = View.GONE
            rank3PositionTextView?.visibility = View.GONE

            // Hide the error TextView
            errorTextView?.visibility = View.GONE
        }
    }

    private fun displayNoScoreboard() {
        val noScoreboardTextView: TextView? = view?.findViewById(R.id.noScoreboardTextView)
        val yourPointsTextView: TextView? = view?.findViewById(R.id.your_points)
        val rank1NameTextView: TextView? = view?.findViewById(R.id.rank1_name)
        val rank1PointsTextView: TextView? = view?.findViewById(R.id.rank1_points)
        val rank1PositionTextView: TextView? = view?.findViewById(R.id.rank1_position)
        val rank2NameTextView: TextView? = view?.findViewById(R.id.rank2_name)
        val rank2PointsTextView: TextView? = view?.findViewById(R.id.rank2_points)
        val rank2PositionTextView: TextView? = view?.findViewById(R.id.rank2_position)
        val rank3NameTextView: TextView? = view?.findViewById(R.id.rank3_name)
        val rank3PointsTextView: TextView? = view?.findViewById(R.id.rank3_points)
        val rank3PositionTextView: TextView? = view?.findViewById(R.id.rank3_position)
        val errorTextView: TextView? = view?.findViewById(R.id.errorTextView)

        // Display the "No Scoreboard" TextView and hide other TextViews
        noScoreboardTextView?.visibility = View.VISIBLE
        yourPointsTextView?.visibility = View.GONE
        rank1NameTextView?.visibility = View.GONE
        rank1PointsTextView?.visibility = View.GONE
        rank1PositionTextView?.visibility = View.GONE
        rank2NameTextView?.visibility = View.GONE
        rank2PointsTextView?.visibility = View.GONE
        rank2PositionTextView?.visibility = View.GONE
        rank3NameTextView?.visibility = View.GONE
        rank3PointsTextView?.visibility = View.GONE
        rank3PositionTextView?.visibility = View.GONE
        // Hide the error TextView
        errorTextView?.visibility = View.GONE
    }

    private fun displayError() {
        val errorTextView: TextView? = view?.findViewById(R.id.errorTextView)
        val noScoreboardTextView: TextView? = view?.findViewById(R.id.noScoreboardTextView)
        val yourPointsTextView: TextView? = view?.findViewById(R.id.your_points)
        val rank1NameTextView: TextView? = view?.findViewById(R.id.rank1_name)
        val rank1PointsTextView: TextView? = view?.findViewById(R.id.rank1_points)
        val rank1PositionTextView: TextView? = view?.findViewById(R.id.rank1_position)
        val rank2NameTextView: TextView? = view?.findViewById(R.id.rank2_name)
        val rank2PointsTextView: TextView? = view?.findViewById(R.id.rank2_points)
        val rank2PositionTextView: TextView? = view?.findViewById(R.id.rank2_position)
        val rank3NameTextView: TextView? = view?.findViewById(R.id.rank3_name)
        val rank3PointsTextView: TextView? = view?.findViewById(R.id.rank3_points)
        val rank3PositionTextView: TextView? = view?.findViewById(R.id.rank3_position)

        // Hide other TextViews
        yourPointsTextView?.visibility = View.GONE
        rank1NameTextView?.visibility = View.GONE
        rank1PointsTextView?.visibility = View.GONE
        rank1PositionTextView?.visibility = View.GONE
        rank2NameTextView?.visibility = View.GONE
        rank2PointsTextView?.visibility = View.GONE
        rank2PositionTextView?.visibility = View.GONE
        rank3NameTextView?.visibility = View.GONE
        rank3PointsTextView?.visibility = View.GONE
        rank3PositionTextView?.visibility = View.GONE

        // Hide the "No Scoreboard" TextView
        noScoreboardTextView?.visibility = View.GONE

        // Display the error TextView
        errorTextView?.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

