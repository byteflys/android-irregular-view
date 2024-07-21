package com.android.library.irregularview.commons.android

import android.graphics.Path
import android.graphics.RectF

internal object PathEx {

    fun Path.bound(): RectF {
        val bound = RectF()
        computeBounds(bound, true)
        return bound
    }
}