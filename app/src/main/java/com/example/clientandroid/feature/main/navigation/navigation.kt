package com.example.clientandroid.feature.main.navigation;

import com.example.clientandroid.R
import com.example.clientandroid.feature.guide.navigation.DISCOVERY_ROUTE
import com.example.clientandroid.feature.guide.navigation.Me_ROUTE
import com.example.clientandroid.feature.guide.navigation.NOTE_ROUTE


enum class TopLevelDestination(
        val selectedIcon : Int,
        val unselectedIcon : Int,
        val titleTextId : Int,
        val route : String
){
        DISCOVERY(
                selectedIcon = R.drawable.home_selected,
                unselectedIcon = R.drawable.home,
                titleTextId = R.string.discovery,
                route = DISCOVERY_ROUTE
        ),
        SHORTVIDEO(
                selectedIcon = R.drawable.home_selected,
                unselectedIcon = R.drawable.home,
                titleTextId = R.string.shortVideo,
                route = NOTE_ROUTE
        ),
        ME(
                selectedIcon = R.drawable.home_selected,
                unselectedIcon = R.drawable.home,
                titleTextId= R.string.me,
                route = Me_ROUTE
        )

}



