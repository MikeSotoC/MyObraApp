package com.uchicn.myobra.ui.drawer

sealed class DrawerItem {

    data class Group(
        val title: String,
        val iconRes: Int?,
        val children: List<Child>
    ) : DrawerItem() {
        var expanded: Boolean = false
    }

    data class Child(
        val title: String,
        val iconRes: Int?,
        val destinationId: Int
    ) : DrawerItem()
}

