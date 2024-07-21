package com.android.library.irregularview.commons.android

import android.graphics.Rect
import android.graphics.RectF

object RectEx {

    fun RectF.toRect(): Rect {
        val src = this
        val dst = Rect()
        dst.left = src.left.toInt()
        dst.right = src.right.toInt()
        dst.top = src.top.toInt()
        dst.bottom = src.bottom.toInt()
        return dst
    }
}