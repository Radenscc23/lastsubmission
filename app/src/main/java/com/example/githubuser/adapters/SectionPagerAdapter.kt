package com.example.githubuser.adapters
import com.example.githubuser.api.ClickUser
import com.example.githubuser.application.sections.fragmentsection.FollowsFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import android.os.Build
import androidx.annotation.RequiresApi



class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var model: ClickUser? = null
    override fun getItemCount(): Int {
        return 2
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun createFragment(position: Int): Fragment {
        return FollowsFragment.newInstance(position + 1, model)
    }

}