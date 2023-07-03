package com.example.drawernav

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RewardsFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_rewards, container, false)

        // Handle the click event for reward boxes
        val reward1Box: View = view.findViewById(R.id.reward1Box)
        reward1Box.setOnClickListener {
            showConfirmationDialog()
        }

        val reward2Box: View = view.findViewById(R.id.reward2Box)
        reward2Box.setOnClickListener {
            showConfirmationDialog()
        }

        val reward3Box: View = view.findViewById(R.id.reward3Box)
        reward3Box.setOnClickListener {
            showConfirmationDialog()
        }

        val reward4Box: View = view.findViewById(R.id.reward4Box)
        reward4Box.setOnClickListener {
            showConfirmationDialog()
        }

        return view
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
