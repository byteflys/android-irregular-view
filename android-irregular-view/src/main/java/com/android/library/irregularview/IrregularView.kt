package com.android.library.irregularview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.PathParser
import com.android.library.irregularview.commons.android.OutlineShape
import com.android.library.irregularview.commons.android.Paths.bound
import com.android.library.irregularview.commons.kotlin.Strings.withDefault
import com.android.library.roundimage.R
import kotlin.math.min

// An android view that enable
// clip foreground and background into irregular shape
class IrregularView : View {

    private var foregroundOutline = OutlineShape.None
    private var foregroundPathData = "M 0 0 L 100 0 L 100 100 L 0 100 Z"

    private val foregroundOutlinePaint = Paint()
    private val foregroundBorderPaint = Paint()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : this(context, attributeSet, defStyleAttr, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        parseAttribute(attributeSet)
        setLayoutAndPaint()
    }

    private fun parseAttribute(attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.IrregularView)
        foregroundOutline = typedArray.getString(R.styleable.IrregularView_foregroundOutline).withDefault(foregroundOutline)
        foregroundPathData = typedArray.getString(R.styleable.IrregularView_foregroundPathData).withDefault(foregroundPathData)
        typedArray.recycle()
    }

    private fun setLayoutAndPaint() {
        foregroundOutlinePaint.isAntiAlias = true
        foregroundBorderPaint.isAntiAlias = true
        foregroundBorderPaint.style = Paint.Style.STROKE
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (foregroundOutline == OutlineShape.Path) {
            drawForegroundOutline(canvas)
        }
    }

    private fun drawForegroundOutline(canvas: Canvas) {
        val path = PathParser.createPathFromPathData(foregroundPathData)
        val pathBound = path.bound()
        val scaleX = measuredWidth / pathBound.width()
        val scaleY = measuredHeight / pathBound.height()
    }

    private fun createContentRect(): RectF {
        return RectF(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            measuredWidth - paddingRight.toFloat(),
            measuredHeight - paddingBottom.toFloat()
        )
    }
}