package com.joey.gamedemo.game

import com.joey.cheetah.core.global.Global
import com.joey.cheetah.core.ktextension.screenHeight
import com.joey.cheetah.core.ktextension.screenWidth
import java.util.*

/**
 * Description:
 * author:Joey
 * date:2018/11/28
 */
class Star {
    private val maxY:Int by lazy { Global.context().screenHeight }
    private val maxX:Int by lazy { Global.context().screenWidth }
    private val random = Random()
    private var speed = 1
    var x = 0
    var y = 0

    init {
        x = random.nextInt(maxX)
        y = random.nextInt(maxY - 1)
        speed = random.nextInt(15) + 1
    }

    fun update() {
        x -= speed
        if (x < 0) {
            x = maxX
            y = random.nextInt(maxY - 1)
            speed = random.nextInt(15) + 1
        }
    }



    fun size():Float {
        val minSize = 1.0f
        val maxSize = 4.0f
        return random.nextFloat()*(maxSize - minSize) + minSize
    }
}