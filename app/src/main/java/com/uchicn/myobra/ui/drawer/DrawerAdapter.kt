package com.uchicn.myobra.ui.drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uchicn.myobra.R

class DrawerAdapter(
    private val items: MutableList<DrawerItem>,
    private val onNavigate: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_GROUP = 0
        private const val TYPE_CHILD = 1
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is DrawerItem.Group -> TYPE_GROUP
            is DrawerItem.Child -> TYPE_CHILD
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_GROUP) {
            GroupVH(inflater.inflate(R.layout.item_drawer_group, parent, false))
        } else {
            ChildVH(inflater.inflate(R.layout.item_drawer_child, parent, false))
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is DrawerItem.Group -> (holder as GroupVH).bind(item, position)
            is DrawerItem.Child -> (holder as ChildVH).bind(item)
        }
    }

    inner class GroupVH(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.findViewById<TextView>(R.id.tvTitle)
        private val icon = view.findViewById<ImageView>(R.id.ivIcon)
        private val arrow = view.findViewById<ImageView>(R.id.ivArrow)

        fun bind(group: DrawerItem.Group, pos: Int) {
            title.text = group.title

            if (group.iconRes != null) {
                icon.setImageResource(group.iconRes)
                icon.imageTintList = null   // 👈 AQUÍ VA
                icon.visibility = View.VISIBLE
            } else {
                icon.visibility = View.GONE
            }

            arrow.rotation = if (group.expanded) 180f else 0f

            itemView.setOnClickListener {
                toggleGroup(adapterPosition, group)
            }
        }

    }

    inner class ChildVH(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.findViewById<TextView>(R.id.tvChild)
        private val icon = view.findViewById<ImageView>(R.id.ivIcon)

        fun bind(child: DrawerItem.Child) {
            title.text = child.title

            if (child.iconRes != null) {
                icon.setImageResource(child.iconRes)
                icon.imageTintList = null   // 👈 AQUÍ VA
                icon.visibility = View.VISIBLE
            } else {
                icon.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onNavigate(child.destinationId)
            }
        }
    }



    private fun toggleGroup(position: Int, group: DrawerItem.Group) {

        if (group.expanded) {
            // 🔽 CERRAR: eliminar SOLO los hijos del grupo
            group.expanded = false
            repeat(group.children.size) {
                items.removeAt(position + 1)
            }

        } else {
            // 🔼 ABRIR: insertar los hijos del grupo
            group.expanded = true
            items.addAll(position + 1, group.children)
        }

        notifyDataSetChanged()
    }
}
