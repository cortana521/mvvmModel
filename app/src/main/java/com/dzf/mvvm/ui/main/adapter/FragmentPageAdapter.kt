package com.dzf.mvvm.ui.main.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentPageAdapter(
    private val mContext: Context,
    fm: FragmentManager?,
    private val mFragments: List<Fragment>,
    private val mFragmentTitles: Array<String>?
) : FragmentStatePagerAdapter(
    fm!!
) {
    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitles?.get(position)
    }
}