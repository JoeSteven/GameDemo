package com.joey.gamedemo.game

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import com.joey.cheetah.core.utils.UIUtil


/**
 * Description:
 * author:Joey
 * date:2018/11/28
 */
class GameView(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet),Runnable, LifecycleObserver {
    private lateinit var gameThread: Thread
    private  val player = Player()
    private  val paint = Paint()
    private var isPlaying = false
    private val enemy = mutableListOf<SpaceJet>()//ArrayList<SpaceJet>(6)
    private val stars = mutableListOf<Star>()
    private var gameOver:Boolean = false
    private var score:Int = 0
    private val gameOverSize = 35
    private val scoreSize = 25
    private var addEnemyInterval = 0
    private var quit = false

    var onGameOver:(score:Int) -> Unit = {}

    init {
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(this)
        }
        enemy.add(SpaceJet())

        while (stars.size < 100) {
            stars.add(Star())
        }
    }

    override fun run() {
        while (true) {
            if (isPlaying) {
                update()

                draw()

                control()
            }
            if (quit) break
        }
    }


    private fun update() {
        player.update()
        stars.forEach {
            it.update()
        }
        val level = getLevel()
        enemy.forEach {
            it.update(player.x, player.y, player.bitmap.width, player.bitmap.height, level)
            if (it.boom) {
                gameOver = it.boom
            }
            if (it.miss) {
                score += 1
            }
        }
    }

    private fun getLevel(): Int {
        return when {
            score <= 30 -> 1
            score in 31..60 -> 2
            score in 61..91 -> 3
            score in 91..121 -> 4
            score in 121..151 -> 5
            score in 151..181 -> 6
            else -> 7
        }
    }

    private fun draw() {
        if (!holder.surface.isValid) return
        val canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        //draw star
        paint.color = Color.WHITE
        stars.forEach {
            paint.strokeWidth = it.size()
            canvas.drawPoint(it.x.toFloat(), it.y.toFloat() ,paint)
        }

        // drawing player
        canvas.drawBitmap(player.bitmap,
                player.x.toFloat(),
                player.y.toFloat(),
                paint)

        // draw space jet
        enemy.forEach {
            canvas.drawBitmap(it.bitmap,
                    it.x.toFloat(),
                    it.y.toFloat(),
                    paint)
        }

        if (gameOver) {
            //draw game over
            paint.textSize = UIUtil.sp2px(gameOverSize).toFloat()
            paint.textAlign = Paint.Align.CENTER

            val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 2)
            canvas.drawText("Game Over", canvas.width*1.0F / 2, yPos, paint)

            paint.textSize = UIUtil.sp2px(scoreSize).toFloat()
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("$score", canvas.width*1.0F / 2, (yPos + UIUtil.sp2px(gameOverSize) + 10), paint)
        } else {
            // draw score
            paint.textSize = UIUtil.sp2px(scoreSize).toFloat()
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("Score:$score Level:${getLevel()}", canvas.width*1.0F/2, UIUtil.sp2px(scoreSize).toFloat(), paint)

        }
        holder.unlockCanvasAndPost(canvas)
    }


    private fun control() {
        if (gameOver) {
            isPlaying = false
            onGameOver(score)
        }

        if (enemy.size < 6) {
            if (addEnemyInterval > 30) {
                enemy.add(SpaceJet())
                addEnemyInterval = 0
            } else {
                addEnemyInterval += 1
            }
        }

        Thread.sleep(17L)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when((event.action.and(MotionEvent.ACTION_MASK))) {
            MotionEvent.ACTION_UP -> {player.jump = false}
            MotionEvent.ACTION_DOWN -> {player.jump = true}
        }
        return true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        isPlaying = false
        quit = true
        gameThread.join()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        isPlaying = true
        quit = false
        gameThread = Thread(this)
        gameThread.start()
    }

    fun replay() {
        addEnemyInterval = 0
        player.reset()
        enemy.clear()
        enemy.add(SpaceJet())
        score = 0
        isPlaying = true
        gameOver = false
    }
}