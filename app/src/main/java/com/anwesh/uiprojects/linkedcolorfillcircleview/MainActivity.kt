package com.anwesh.uiprojects.linkedcolorfillcircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.colorfillcircleview.ColorFillCircleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ColorFillCircleView.create(this)
    }
}