package com.example.cubipool.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cubipool.JoinCubiclesActivity

import com.example.cubipool.R
import kotlinx.android.synthetic.main.fragment_join_group.*

/**
 * A simple [Fragment] subclass.
 */
class JoinGroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_join.setOnClickListener { goToSearchCubicle() }
    }
    private fun goToSearchCubicle() {

        val intent = Intent(context, JoinCubiclesActivity::class.java)

        startActivity(intent);
    }


}
