package com.example.cineeaseapp.screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cineeaseapp.R
import com.example.cineeaseapp.adapter.BannerAdapter
import com.example.cineeaseapp.data.FilmData
import com.example.cineeaseapp.adapter.ListFilmAdapter
import com.example.cineeaseapp.data.Film
import com.example.cineeaseapp.style.SpaceItemDecoration
import me.relex.circleindicator.CircleIndicator3

class FilmScreen : Fragment() {

    private lateinit var bannerViewPager: ViewPager2
    private lateinit var bannerIndicator: CircleIndicator3
    private val banners = listOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            bannerViewPager.currentItem = (bannerViewPager.currentItem + 1) % banners.size
            handler.postDelayed(this, 3000)
        }
    }

    private lateinit var rvFilm: RecyclerView
    private lateinit var filmAdapter: ListFilmAdapter
    private lateinit var filmData: FilmData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_film_screen, container, false)

        bannerViewPager = view.findViewById(R.id.viewPager)
        bannerIndicator = view.findViewById(R.id.indicator)
        bannerViewPager.adapter = BannerAdapter(banners)
        bannerIndicator.setViewPager(bannerViewPager)
        handler.postDelayed(runnable, 2000)

        rvFilm = view.findViewById(R.id.rv_film)
        filmData = FilmData(requireContext())
        filmAdapter = ListFilmAdapter(filmData.listFilm)
        rvFilm.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvFilm.adapter = filmAdapter

        filmAdapter.setOnItemClickCallback(object : ListFilmAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Film) {
                val intent = Intent(context, DetailFilmScreen::class.java)
                intent.putExtra(DetailFilmScreen.EXTRA_FILM, data)
                startActivity(intent)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvFilms: RecyclerView = view.findViewById(R.id.rv_film)

        val spaceInPixels = 10
        rvFilms.addItemDecoration(SpaceItemDecoration(spaceInPixels))

        rvFilms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val childCount = rvFilms.childCount
                val width = rvFilms.getChildAt(childCount / 2).width
                val padding = (rvFilms.width - width) / 2

                for (i in 0 until childCount) {
                    val child = rvFilms.getChildAt(i)
                    val childMid = (child.left + child.right) / 2
                    val expectedMid = padding + rvFilms.scrollX
                    val scale = 1f + 0.25f * (1 - Math.abs(expectedMid - childMid) / padding.toFloat())
                    child.scaleX = scale
                    child.scaleY = scale
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
}