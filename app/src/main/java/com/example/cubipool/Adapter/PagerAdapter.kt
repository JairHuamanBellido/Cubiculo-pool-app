package com.example.cubipool.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cubipool.Fragments.HomeFragment
import com.example.cubipool.Fragments.JoinGroupFragment
import com.example.cubipool.Fragments.SearchFragment

internal class PagerAdapter (fm:FragmentManager?):FragmentPagerAdapter(fm!!){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->{
                HomeFragment()
            }
            1->{
                SearchFragment()
            }
            2->{
                JoinGroupFragment()
            }

            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {

        return 3;
    }

}