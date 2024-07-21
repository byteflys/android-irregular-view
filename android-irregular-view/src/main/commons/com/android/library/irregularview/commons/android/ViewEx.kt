package com.android.library.irregularview.commons.android

import android.graphics.Rect
import android.view.View

internal object ViewEx {

    fun View.layout(rect: Rect) = layout(rect.left, rect.top, rect.right, rect.bottom)
}