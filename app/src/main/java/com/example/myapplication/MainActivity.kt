package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.Fragments.ChatsFragment
import com.example.myapplication.Fragments.SearchFragment
import com.example.myapplication.Fragments.SettingsFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var refUser: DatabaseReference?=null
    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main as Toolbar?)
        supportActionBar!!.title=""




        val tabLayout: TabLayout = findViewById(R.id.tablayout)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(ChatsFragment(), "Chats" )
        viewPagerAdapter.addFragment(SearchFragment(), "Search" )
        viewPagerAdapter.addFragment(SettingsFragment(), "Settings" )
        viewPager.adapter=viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
    internal class ViewPagerAdapter(fragmentsManager: FragmentManager):
        FragmentPagerAdapter(fragmentsManager) {
        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>

        init{
            fragments=ArrayList<Fragment>()
            titles=ArrayList<String>()
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String)
        {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
