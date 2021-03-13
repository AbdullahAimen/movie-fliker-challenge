package com.challenge.movie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.challenge.movie.R
import com.challenge.movie.dataModel.MovieInfo


class ExpandableListAdapter(private val mContext: Context, private val datMap: Map<Int, List<MovieInfo>>) :
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return datMap.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return datMap[getGroup(groupPosition)]?.size!!
    }

    override fun getGroup(groupPosition: Int): Int {
        return datMap.keys.elementAt(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): MovieInfo {
        return datMap[getGroup(groupPosition)]?.get(childPosition)!!
    }

    override fun getGroupId(groupPosition: Int): Long {
        return getGroup(groupPosition).toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return (datMap[getGroup(groupPosition)]?.get(childPosition)?.id!!).toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val groupTitle = getGroup(groupPosition)
        val headerView =
            LayoutInflater.from(mContext).inflate(R.layout.item_expandable_header, parent, false)
        (headerView.findViewById(R.id.expandable_tv_header) as TextView).text =
            groupTitle.toString()
        return headerView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val movieTitle = getChild(groupPosition, childPosition).title
        val childView =
            LayoutInflater.from(mContext).inflate(R.layout.item_expandable_child, parent, false)
        (childView.findViewById(R.id.expandable_tv_movieTitle) as TextView).text = movieTitle
        return childView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}