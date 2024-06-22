package com.example.cineeaseapp.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R
import com.example.cineeaseapp.adapter.LaporanAdapter
import com.example.cineeaseapp.data.DatabaseHandlerFilm
import com.example.cineeaseapp.data.DatabaseHandlerSnack
import com.example.cineeaseapp.data.Laporan

class LaporanScreen : Fragment() {

    private lateinit var laporanAdapter: LaporanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_laporan_screen, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        laporanAdapter = LaporanAdapter(emptyList())
        recyclerView.adapter = laporanAdapter

        loadLaporan()

        return view
    }

    private fun loadLaporan() {
        val dbHandlerFilm = DatabaseHandlerFilm(requireContext())
        val dbHandlerSnack = DatabaseHandlerSnack(requireContext())
        val laporanList = dbHandlerFilm.getAllTicketSales() + dbHandlerSnack.getAllSnackSales()

        laporanAdapter.updateData(laporanList)
    }
}