package com.anthony.weatherparsing

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView

class PaddingDividerItemDecoration : RecyclerView.ItemDecoration {
    private var divider: Drawable? = null
    private var paddingLeft = 0
    private var paddingRight = 0

    constructor(context: Context, drawableResourceId: Int) {
        divider = AppCompatResources.getDrawable(context, drawableResourceId)
    }

    constructor(context: Context, drawableResourceId: Int, paddingLeft: Int, paddingRight: Int) {
        divider = AppCompatResources.getDrawable(context, drawableResourceId)
        this.paddingLeft = paddingLeft
        this.paddingRight = paddingRight
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        divider?.let {
            val left = parent.paddingLeft + paddingLeft
            val right = parent.width - parent.paddingRight - paddingRight

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + it.intrinsicHeight

                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}