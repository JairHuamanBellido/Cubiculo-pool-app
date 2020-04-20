package com.example.cubipool

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.example.cubipool.Adapter.PagerAdapter
import com.example.cubipool.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var myViewPager: ViewPager
    private lateinit var homeBtn: ImageButton
    private lateinit var searchBtn:ImageButton
    private lateinit var joinGroupBtn: ImageButton
    private lateinit var mPagerAdapter:PagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        myViewPager =  findViewById(R.id.mViewPager)

        homeBtn =  findViewById(R.id.homeBtn)
        searchBtn =  findViewById(R.id.searchBtn)
        joinGroupBtn =  findViewById(R.id.joingroupBtn)

        mPagerAdapter =  PagerAdapter(supportFragmentManager)

        mViewPager.adapter =  mPagerAdapter;
        mViewPager.offscreenPageLimit = 3

        homeBtn.setOnClickListener(View.OnClickListener { mViewPager.setCurrentItem(0) });
        searchBtn.setOnClickListener(View.OnClickListener { mViewPager.setCurrentItem(1) });
        joinGroupBtn.setOnClickListener(View.OnClickListener { mViewPager.setCurrentItem(2) });




        mViewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
               ;
            }


            override fun onPageSelected(position: Int) {
                changingTabs(position);
                println("asd")
            }


        })
        mViewPager.currentItem = 0;
        homeBtn.setImageResource(R.drawable.ic_home_selected);

    /*    var logoutButton:Button =  findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener{logout()}*/

    }
    private fun changingTabs(position: Int) {
        if(position == 0 ){
            homeBtn.setImageResource(R.drawable.ic_home_selected);
            searchBtn.setImageResource(R.drawable.ic_search);
            joinGroupBtn.setImageResource(R.drawable.ic_notification);
        }

        if(position == 1 ){
            homeBtn.setImageResource(R.drawable.ic_home);
            searchBtn.setImageResource(R.drawable.ic_search_selected);
            joinGroupBtn.setImageResource(R.drawable.ic_notification);
        }
        if(position == 2 ){
            homeBtn.setImageResource(R.drawable.ic_home);
            searchBtn.setImageResource(R.drawable.ic_search);
            joinGroupBtn.setImageResource(R.drawable.ic_notification_selected);
        }
    }


    /*    private fun logout(){
        val sharedPreferences:SharedPreferences =  getSharedPreferences("db_local",0);
        val editor = sharedPreferences.edit()
        editor.remove("code")
        editor.apply()

        val intent  = Intent(getApplicationContext(),LoginActivity::class.java);
        startActivity(intent);
    }*/

    @Override
    fun ga(){
        println("asdqweqweqw")
    }





}
