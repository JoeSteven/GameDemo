package com.joey.gamedemo.game

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.joey.cheetah.core.global.Global
import com.joey.cheetah.core.ktextension.logD
import com.joey.cheetah.core.ktextension.screenHeight
import com.joey.cheetah.core.ktextension.screenWidth
import com.joey.cheetah.core.utils.ResGetter
import com.joey.gamedemo.R
import java.util.*

/**
 * Description:
 * author:Joey
 * date:2018/11/28
 */
class SpaceJet(var bitmap: Bitmap = BitmapFactory.decodeResource(ResGetter.resources(), R.drawable.enemy)) {
    private val maxY:Int by lazy { Global.context().screenHeight - bitmap.height }
    private val maxX:Int by lazy { Global.context().screenWidth }
    var x = 0
    var y = 0
    var speed:Int = 1
    private val random = Random()
    var boom:Boolean = false
    var miss:Boolean = false
    private var missCounted:Boolean = false
    private var level = 1

    init {
        reset()
    }

    fun update(playerX: Int, playerY: Int, playerWidth: Int, playerHeight: Int, level:Int) {
        if (countOffend(playerX, playerY, playerWidth, playerHeight)) {
            logD("countOffend", "boom:dx->${Math.abs(x - playerX)}, dy->${Math.abs(y - playerY)}, playerH:$playerHeight, playerW:$playerWidth,jetW:${bitmap.width}, jetH:${bitmap.height}")
            bitmap = BitmapFactory.decodeResource(ResGetter.resources(), R.drawable.boom)
            boom = true
            return
        }

        miss = false
        if (!missCounted) {
            miss = countMiss(playerX)
            if (miss) {
                missCounted = true
            }
        }

        if (this.level != level) {
            countSpeed(level)
            this.level = level
        }

        x -= speed
        if (x < -bitmap.width) {
            x = maxX
            y = random.nextInt(maxY)
            countSpeed(level)
            missCounted = false
        }
        boom = false
    }
    private fun countOffend(playerX: Int, playerY: Int, playerWidth: Int, playerHeight: Int): Boolean {
        val dX = Math.abs(x - playerX)
        val dY = Math.abs(y - playerY)
        return when {
            y > playerY -> dX < playerWidth && dY < playerHeight - 30
            y < playerY -> dX < playerWidth && dY < bitmap.height - 20
            else -> dX < playerWidth
        }
    }

    private fun countMiss(playerX: Int):Boolean {
        val dX = x - playerX
        return dX < bitmap.width
    }

    private fun countSpeed(level:Int) {
        when (level) {
            1 -> speed = random.nextInt(5) + 10
            2 -> speed = random.nextInt(5) + 15
            3 -> speed = random.nextInt(5) + 20
            4 -> speed = random.nextInt(5) + 25
            5 -> speed = random.nextInt(5) + 30
            6 -> speed = random.nextInt(5) + 35
            7 -> speed = random.nextInt(5) + 40
        }
    }

    fun reset() {
        level = 1
        boom = false
        miss = false
        missCounted = false
        x = maxX
        y = random.nextInt(maxY)
        countSpeed(level)
        bitmap = BitmapFactory.decodeResource(ResGetter.resources(), R.drawable.enemy)
    }
}