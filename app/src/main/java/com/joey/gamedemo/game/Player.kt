package com.joey.gamedemo.game

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.joey.cheetah.core.global.Global
import com.joey.cheetah.core.ktextension.screenHeight
import com.joey.cheetah.core.utils.ResGetter
import com.joey.gamedemo.R

/**
 * Description:
 * author:Joey
 * date:2018/11/28
 */
class Player(var x: Int = 150,
                  var y: Int = 50,
                  var jump:Boolean = false,
                  val bitmap: Bitmap = BitmapFactory.decodeResource(ResGetter.resources(), R.drawable.player)) {
    private val offsetY = 30
    private val lostY = 15
    private val maxY:Int by lazy { Global.context().screenHeight - bitmap.height }

    init {
        y = maxY
    }

    fun update() {
        if (jump) {
            // 跳
            y -= offsetY
        } else {
            // 下落
            y += lostY
        }

        if (y < 0) {
            y = 0
        } else if (y > maxY) {
            y = maxY
        }
    }

    fun reset() {
        x = 150
        y = maxY
    }
}