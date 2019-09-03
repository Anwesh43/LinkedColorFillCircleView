package com.anwesh.uiprojects.colorfillcircleview

/**
 * Created by anweshmishra on 03/09/19.
 */

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Color
import android.graphics.Canvas
import android.graphics.Path
import android.view.View
import android.view.MotionEvent

val colors : Array<String> = arrayOf("#880E4F", "#0D47A1", "#00C853", "#f44336", "#006064")
val scGap : Float = 0.02f
val rFactor : Float = 3.4f
val backColor : Int = Color.parseColor("#BDBDBD")
val lines : Int = 4
val lSizeFactor : Float = 3f
val sizeFactor : Float = 3f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n

fun Canvas.drawLine(i : Int, size : Float, sc : Float, paint : Paint) {
    save()
    rotate(90f * i)
    drawLine(0f, 0f, 0f, -size * sc.divideScale(i, lines), paint)
    restore()
}

fun Canvas.drawFourLine(sc : Float, size : Float, paint : Paint) {
    paint.color = Color.WHITE
    for (j in 0..(lines - 1)) {
        drawLine(j, size / lSizeFactor, sc, paint)
    }
}

fun Canvas.drawColorFillCircle(i : Int, size : Float, sc : Float, y : Float, paint : Paint) {
    save()
    val path : Path = Path()
    path.addCircle(0f, 0f, size, Path.Direction.CW)
    clipPath(path)
    paint.color = Color.parseColor(colors[i])
    translate(0f, -size - 2 * size * sc + y)
    drawRect(RectF(-size, 0f, size, 2 * size), paint)
    restore()
}

fun Canvas.drawCFCNode(i : Int, scale : Float, sc : Float, paint : Paint) : Float {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val size : Float = Math.min(w, h) / sizeFactor
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    var y : Float = 0f
    if (sc > 0f) {
        y = 2 * size * (1 - sc)
    }
    save()
    translate(w / 2, h / 2)
    drawColorFillCircle(i, size, sc2, y, paint)
    drawFourLine(sc1, size, paint)
    restore()
    return sc2 
}

class ColorFillCircleView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1f) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}