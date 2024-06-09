package com.example.cineeaseapp.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R
import com.example.cineeaseapp.adapter.ListSnackAdapter
import com.example.cineeaseapp.data.Snack
import com.example.cineeaseapp.data.SnackData

class SnackScreen : Fragment() {

    private lateinit var rvSnacks: RecyclerView
    private var list: ArrayList<Snack> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_snack_screen, container, false)

        rvSnacks = view.findViewById(R.id.rv_snacks)
        rvSnacks.setHasFixedSize(true)

        val snackData = SnackData(requireContext())
        list.addAll(snackData.listSnack)
        showRecyclerList()

        return view
    }

    private fun showRecyclerList() {
        rvSnacks.layoutManager = LinearLayoutManager(this.context)
        val listSnackAdapter = ListSnackAdapter(list)
        rvSnacks.adapter = listSnackAdapter
    }
}
