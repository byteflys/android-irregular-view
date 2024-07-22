package com.android.library.irregularview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.PathParser
import com.android.library.irregularview.commons.android.MatrixEx.scale
import com.android.library.irregularview.commons.android.MatrixEx.translate
import com.android.library.irregularview.commons.android.ObjectEx.ifNotNull
import com.android.library.irregularview.commons.android.OutlineShape
import com.android.library.irregularview.commons.android.PathEx.bound
import com.android.library.irregularview.commons.android.RectEx.toRect
import com.android.library.irregularview.commons.android.ViewEx.createCornerRadius
import com.android.library.irregularview.commons.android.ViewEx.getAvailableRectF
import com.android.library.irregularview.commons.kotlin.StringEx.withDefault
import com.android.library.roundimage.R

// An android view that enable
// clip foreground and background into irregular shape
class IrregularView : View {

    private var foreground: Drawable? = null
    private var foregroundOutline = OutlineShape.None
    private var foregroundPathData = "M 0 0 L 100 0 L 100 100 L 0 100 Z"
    private var foregroundCorner = 0f
    private var foregroundCornerTL = 0f
    private var foregroundCornerTR = 0f
    private var foregroundCornerBL = 0f
    private var foregroundCornerBR = 0f
    private val foregroundOutlinePaint = Paint()
    private val foregroundBorderPaint = Paint()

    private var background: Drawable? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : this(context, attributeSet, defStyleAttr, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        parseAttribute(attributeSet)
        setLayoutAndPaint()
    }

    private fun parseAttribute(attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.IrregularView)
        foreground = typedArray.getDrawable(R.styleable.IrregularView_foreground)
        foregroundOutline = typedArray.getInt(R.styleable.IrregularView_foregroundOutline, foregroundOutline)
        foregroundPathData = typedArray.getString(R.styleable.IrregularView_foregroundPathData).withDefault(foregroundPathData)
        foregroundCorner = typedArray.getDimension(R.styleable.IrregularView_foregroundCornerRadius, 0f)
        foregroundCornerTL = typedArray.getDimension(R.styleable.IrregularView_foregroundTopLeftRadius, foregroundCorner)
        foregroundCornerTR = typedArray.getDimension(R.styleable.IrregularView_foregroundTopRightRadius, foregroundCorner)
        foregroundCornerBL = typedArray.getDimension(R.styleable.IrregularView_foregroundBottomLeftRadius, foregroundCorner)
        foregroundCornerBR = typedArray.getDimension(R.styleable.IrregularView_foregroundBottomRightRadius, foregroundCorner)
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
        foreground.ifNotNull {
            drawLayer(
                canvas, it,
                foregroundOutline, foregroundPathData,
                foregroundCornerTL, foregroundCornerTR, foregroundCornerBL, foregroundCornerBR
            )
        }
    }

    private fun drawLayer(
        canvas: Canvas, drawable: Drawable,
        outline: Int, pathData: String,
        cornerTL: Number, cornerTR: Number, cornerBL: Number, cornerBR: Number
    ) {
        if (outline == OutlineShape.Path)
            drawPathOutline(canvas, drawable, pathData)
        if (outline == OutlineShape.RoundRect)
            drawRoundRectOutline(canvas, drawable, cornerTL, cornerTR, cornerBL, cornerBR)
    }

    private fun drawPathOutline(
        canvas: Canvas, drawable: Drawable, pathData: String
    ) {
        val path = PathParser.createPathFromPathData(pathData)
        val pathBound = path.bound()
        val contentRect = getAvailableRectF()
        val scaleX = contentRect.width() / pathBound.width()
        val scaleY = contentRect.height() / pathBound.height()
        canvas.save()
        val matrix = Matrix().scale(scaleX, scaleY).translate(paddingLeft, paddingTop)
        canvas.setMatrix(matrix)
        canvas.clipPath(path)
        drawable?.bounds = pathBound.toRect()
        drawable?.draw(canvas)
        canvas.restore()
    }

    private fun drawRoundRectOutline(
        canvas: Canvas, drawable: Drawable,
        cornerTL: Number, cornerTR: Number, cornerBL: Number, cornerBR: Number
    ) {
        val path = Path()
        val contentRect = getAvailableRectF()
        path.addRoundRect(
            contentRect,
            createCornerRadius(
                cornerTL.toFloat() * measuredWidth, cornerTL.toFloat() * measuredHeight,
                cornerTR.toFloat() * measuredWidth, cornerTR.toFloat() * measuredHeight,
                cornerBL.toFloat() * measuredWidth, cornerBL.toFloat() * measuredHeight,
                cornerBR.toFloat() * measuredWidth, cornerBR.toFloat() * measuredHeight
            ),
            Path.Direction.CW
        )
        canvas.save()
        canvas.clipPath(path)
        drawable?.bounds = contentRect.toRect()
        drawable?.draw(canvas)
        canvas.restore()
    }
}