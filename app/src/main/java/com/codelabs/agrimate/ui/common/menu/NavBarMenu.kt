package com.codelabs.agrimate.ui.common.menu

import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.components.model.NavbarMenuModel
import com.codelabs.agrimate.ui.navigation.AGRoute

val farmerNavBarMenu: List<NavbarMenuModel> by lazy {
    listOf(
        NavbarMenuModel(R.drawable.icon_home, "Beranda", AGRoute.Farmer.Main.Home.route),
        NavbarMenuModel(R.drawable.icon_land_report, "Lahan", AGRoute.Farmer.Main.Land.route),
        NavbarMenuModel(R.drawable.icon_chat, "Chat", AGRoute.Farmer.Main.Chat.route),
        NavbarMenuModel(R.drawable.icon_profile, "Profil", AGRoute.Farmer.Main.Profile.route),
    )
}