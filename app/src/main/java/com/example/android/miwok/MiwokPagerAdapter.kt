package com.example.android.miwok

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by jefftulley on 2/7/18.
 */
class MiwokPagerAdapter(sm: FragmentManager, private val context: Context) : FragmentPagerAdapter(sm)
{
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return NumbersFragment()
            1 -> return FamilyFragment()
            2 -> return ColorsFragment()
        }
        return PhrasesFragment()
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.category_numbers)
            1 -> return context.getString(R.string.category_family)
            2 -> return context.getString(R.string.category_colors)
            3 -> return context.getString(R.string.category_phrases)
        }
        return "unknown"
    }
}