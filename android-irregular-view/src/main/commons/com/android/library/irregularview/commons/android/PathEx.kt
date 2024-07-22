package com.android.library.irregularview.commons.android

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import com.android.library.irregularview.commons.android.MatrixEx.rotate
import com.android.library.irregularview.commons.android.MatrixEx.scale
import com.android.library.irregularview.commons.android.MatrixEx.skew
import com.android.library.irregularview.commons.android.MatrixEx.translate

internal object PathEx {

    fun Path.bound(): RectF {
        val bound = RectF()
        computeBounds(bound, true)
        return bound
    }

    fun Path.translate(dx: Number, dy: Number) = apply {
        transform(Matrix().translate(dx, dy))
    }

    fun Path.scale(sx: Number, sy: Number) = apply {
        transform(Matrix().scale(sx, sy))
    }

    fun Path.rotate(degree: Number) = apply {
        transform(Matrix().rotate(degree))
    }

    fun Path.skew(
        kx: Number,
        ky: Number,
        px: Number = 0f,
        py: Number = 0f,
    ) = apply {
        transform(Matrix().skew(kx, ky, px, py))
    }
}