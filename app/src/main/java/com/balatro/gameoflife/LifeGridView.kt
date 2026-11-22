package com.balatro.gameoflife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class LifeGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var lifeGrid: LifeGrid = LifeGrid()
        set(value) {
            field = value
            invalidate()
        }

    var onGridChanged: (() -> Unit)? = null

    // Drawing paints
    private val alivePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF00FFA3.toInt() // Neon Emerald
        style = Paint.Style.FILL
    }

    private val maturePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF2BD88B.toInt() // Mature Green
        style = Paint.Style.FILL
    }

    private val deadPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF17201C.toInt() // Dark Background
        style = Paint.Style.FILL
    }

    private val gridLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF212E28.toInt()
        strokeWidth = 1.5f
        style = Paint.Style.STROKE
    }

    private val cellRect = RectF()
    private var cellWidth = 0f
    private var cellHeight = 0f

    // Touch interaction tracking
    private var lastTouchedRow = -1
    private var lastTouchedCol = -1
    private var paintTargetState = true // true = drawing alive, false = erasing

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateCellSize(w, h)
    }

    private fun calculateCellSize(w: Int, h: Int) {
        if (lifeGrid.cols > 0 && lifeGrid.rows > 0) {
            cellWidth = w.toFloat() / lifeGrid.cols
            cellHeight = h.toFloat() / lifeGrid.rows
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (cellWidth <= 0 || cellHeight <= 0) {
            calculateCellSize(width, height)
            if (cellWidth <= 0 || cellHeight <= 0) return
        }

        val rows = lifeGrid.rows
        val cols = lifeGrid.cols
        val cellPadding = 1.2f
        val cornerRadius = (cellWidth * 0.25f).coerceAtMost(6f)

        for (r in 0 until rows) {
            val top = r * cellHeight
            val bottom = top + cellHeight

            for (c in 0 until cols) {
                val left = c * cellWidth
                val right = left + cellWidth

                cellRect.set(
                    left + cellPadding,
                    top + cellPadding,
                    right - cellPadding,
                    bottom - cellPadding
                )

                if (lifeGrid.isAlive(r, c)) {
                    val age = lifeGrid.getAge(r, c)
                    val paint = if (age <= 1) alivePaint else maturePaint
                    canvas.drawRoundRect(cellRect, cornerRadius, cornerRadius, paint)
                } else {
                    canvas.drawRoundRect(cellRect, cornerRadius, cornerRadius, deadPaint)
                }
            }
        }

        // Draw horizontal grid lines
        for (r in 0..rows) {
            val y = r * cellHeight
            canvas.drawLine(0f, y, width.toFloat(), y, gridLinePaint)
        }

        // Draw vertical grid lines
        for (c in 0..cols) {
            val x = c * cellWidth
            canvas.drawLine(x, 0f, x, height.toFloat(), gridLinePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (cellWidth <= 0 || cellHeight <= 0) return false

        val col = (event.x / cellWidth).toInt().coerceIn(0, lifeGrid.cols - 1)
        val row = (event.y / cellHeight).toInt().coerceIn(0, lifeGrid.rows - 1)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchedRow = row
                lastTouchedCol = col
                // Determine whether we are painting or erasing based on current touched cell
                paintTargetState = !lifeGrid.isAlive(row, col)
                lifeGrid.setCell(row, col, paintTargetState)
                invalidate()
                onGridChanged?.invoke()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (row != lastTouchedRow || col != lastTouchedCol) {
                    lastTouchedRow = row
                    lastTouchedCol = col
                    lifeGrid.setCell(row, col, paintTargetState)
                    invalidate()
                    onGridChanged?.invoke()
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                lastTouchedRow = -1
                lastTouchedCol = -1
                return true
            }
        }

        return super.onTouchEvent(event)
    }
}
