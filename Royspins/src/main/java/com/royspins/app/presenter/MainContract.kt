package com.royspins.app.presenter

import android.content.Context
import com.walhalla.landing.pagination.CatItem


interface MainContract {
    interface View {
        fun showSideNavigationMenu()

        fun hideSideNavigationMenu()

        fun showBottomNavigationMenu()

        fun hideBottomNavigationMenu()

        fun showToolbar()

        fun hideToolbar()

        fun loadUrlInWebView(url: String)

        fun setupNavigationMenus(navMenuItems: List<CatItem>, bottomNavMenuItems: List<CatItem>)
    }

    interface Presenter {
        fun onNavigationItemSelected(itemId: String?)

        fun onBottomNavigationItemSelected(itemId: String?)

        fun fetchRemoteConfig(context: Context)
        fun destroy()
    }
}
