package com.android.library.irregularview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.android.library.irregularview.commons.android.Dimens.dp2Px

// An android view that enable
// clip foreground and background into irregular shape
class IrregularView : View {

    private var cornerRadius = 0f
    private var topLeftRadius = 0f
    private var topRightRadius = 0f
    private var bottomLeftRadius = 0f
    private var bottomRightRadius = 0f

    private var showBorder = false
    private var borderColor = Color.BLACK
    private var borderWidth = dp2Px(1)

    private val roundRectPaint = Paint()
    private val borderPaint = Paint()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : this(context, attributeSet, defStyleAttr, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        parseAttribute(attributeSet)
        setLayoutAndPaint()
    }

    private fun parseAttribute(attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RoundImageView)
        cornerRadius = typedArray.getDimension(R.styleable.RoundImageView_cornerRadius, cornerRadius)
        topLeftRadius = cornerRadius
        topRightRadius = cornerRadius
        bottomRightRadius = cornerRadius
        bottomLeftRadius = cornerRadius
        showBorder = typedArray.getBoolean(R.styleable.RoundImageView_showBorder, showBorder)
        borderColor = typedArray.getColor(R.styleable.RoundImageView_borderColor, borderColor)
        borderWidth = typedArray.getDimension(R.styleable.RoundImageView_borderWidth, borderWidth)
        if (typedArray.hasValue(R.styleable.RoundImageView_topLeftRadius)) {
            topLeftRadius = typedArray.getDimension(R.styleable.RoundImageView_topLeftRadius, topLeftRadius)
        }
        if (typedArray.hasValue(R.styleable.RoundImageView_topRightRadius)) {
            topRightRadius = typedArray.getDimension(R.styleable.RoundImageView_topRightRadius, topRightRadius)
        }
        if (typedArray.hasValue(R.styleable.RoundImageView_bottomRightRadius)) {
            bottomRightRadius = typedArray.getDimension(R.styleable.RoundImageView_bottomRightRadius, bottomRightRadius)
        }
        if (typedArray.hasValue(R.styleable.RoundImageView_bottomLeftRadius)) {
            bottomLeftRadius = typedArray.getDimension(R.styleable.RoundImageView_bottomLeftRadius, bottomLeftRadius)
        }
        typedArray.recycle()
    }

    private fun setLayoutAndPaint() {
        roundRectPaint.isAntiAlias = true
        roundRectPaint.color = Color.BLACK
        roundRectPaint.style = Paint.Style.FILL
        borderPaint.isAntiAlias = true
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(createRoundRectPath())
        super.onDraw(canvas)
//        roundRectPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
//        canvas.drawPath(createRoundRectPath(), roundRectPaint)
//        roundRectPaint.setXfermode(null)
//        if (showBorder)
//            canvas.drawPath(createBorderPath(), borderPaint)
    }

    private fun createCornerRadius(): FloatArray {
        return floatArrayOf(
            topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomRightRadius, bottomRightRadius,
            bottomLeftRadius, bottomLeftRadius
        )
    }

    private fun createContentRect(): RectF {
        return RectF(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            measuredWidth - paddingRight.toFloat(),
            measuredHeight - paddingBottom.toFloat()
        )
    }

    private fun createRoundRectPath(): Path {
        val path = Path()
        path.addRoundRect(
            createContentRect(),
            createCornerRadius(),
            Path.Direction.CW
        )
        return path
    }

    private fun createBorderPath(): Path {
        val rect = createContentRect()
        rect.inset(borderWidth / 2, borderWidth / 2)
        val path = Path()
        path.addRoundRect(rect, createCornerRadius(), Path.Direction.CCW)
        return path
    }
}