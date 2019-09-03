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
import android.view.View
import android.view.MotionEvent

val colors : Array<String> = arrayOf("#880E4F", "#0D47A1", "#00C853", "#f44336", "#006064")
val scGap : Float = 0.05f
val rFactor : Float = 3.4f
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
