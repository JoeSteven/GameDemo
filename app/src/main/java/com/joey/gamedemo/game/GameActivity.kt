package com.joey.gamedemo.game

import com.joey.cheetah.core.ktextension.gone
import com.joey.cheetah.core.ktextension.visible
import com.joey.cheetah.mvp.AbsActivity
import com.joey.gamedemo.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AbsActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_game
    }

    override fun initView() {
        btnReplay.setOnClickListener {
            gameView.replay()
            btnReplay.gone()
        }
        gameView.onGameOver = {runOnUiThread {
            btnReplay.visible()
        }}
    }
}
