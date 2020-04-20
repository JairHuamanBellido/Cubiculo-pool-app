package com.example.cubipool.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cubipool.R
import com.example.cubipool.SearchCubicleActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)




    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchCubicleBtn.setOnClickListener{ga()}
        println("Actividad creada desde el Fragment");
    }
    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }

    }
        public fun ga() {
            println("asdasdadsad")

            val intent =  Intent(context,SearchCubicleActivity::class.java)

            startActivity(intent);
        }
    }

