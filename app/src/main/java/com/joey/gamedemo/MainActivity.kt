package com.joey.gamedemo

import com.joey.cheetah.core.ktextension.jump
import com.joey.cheetah.mvp.AbsActivity
import com.joey.gamedemo.game.GameActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbsActivity() {
    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        btnPlay.setOnClickListener { jump(GameActivity::class.java) }
        btnScore.setOnClickListener {  }
    }

}
