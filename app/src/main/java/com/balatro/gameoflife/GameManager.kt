package com.balatro.gameoflife

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper

class GameManager(
    context: Context,
    val grid: LifeGrid
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("game_of_life_prefs", Context.MODE_PRIVATE)

    var isRunning: Boolean = false
        private set

    var generation: Long = 0
        private set

    var score: Long = 0
        private set

    var highScore: Long = prefs.getLong("key_high_score", 0L)
        private set

    // Speeds: 1x (300ms), 2x (150ms), 5x (60ms), 10x (30ms)
    val speedIntervals = listOf(300L, 150L, 60L, 30L)
    val speedLabels = listOf("1X", "2X", "5X", "10X")
    var currentSpeedIndex = 0
        private set

    private val handler = Handler(Looper.getMainLooper())
    private val tickRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                step()
                handler.postDelayed(this, speedIntervals[currentSpeedIndex])
            }
        }
    }

    var onUpdate: ((score: Long, aliveCount: Int, generation: Long, highScore: Long) -> Unit)? = null
    var onPlayStateChanged: ((isPlaying: Boolean) -> Unit)? = null

    fun play() {
        if (!isRunning) {
            isRunning = true
            onPlayStateChanged?.invoke(true)
            handler.post(tickRunnable)
        }
    }

    fun pause() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(tickRunnable)
            onPlayStateChanged?.invoke(false)
        }
    }

    fun togglePlayPause() {
        if (isRunning) pause() else play()
    }

    /**
     * Executes one generation step and calculates Balatro-inspired score.
     */
    fun step() {
        val result = grid.nextGeneration()
        generation++

        // Balatro-style Chips & Mult scoring:
        // Chips = living cells + newly born cells * 2
        // Mult = scaling multiplier based on generation survival streak
        val chips = result.aliveCount + (result.birthCount * 2L)
        val mult = (1 + (generation / 20)).coerceAtMost(25)
        val gainedScore = chips * mult

        score += gainedScore

        if (score > highScore) {
            highScore = score
            prefs.edit().putLong("key_high_score", highScore).apply()
        }

        notifyUpdate(result.aliveCount)
    }

    fun cycleSpeed(): String {
        currentSpeedIndex = (currentSpeedIndex + 1) % speedIntervals.size
        return speedLabels[currentSpeedIndex]
    }

    fun resetStats() {
        generation = 0
        score = 0
        notifyUpdate(grid.getAliveCount())
    }

    fun clearGrid() {
        pause()
        grid.clear()
        resetStats()
    }

    fun randomize() {
        pause()
        grid.randomize()
        resetStats()
    }

    fun notifyUpdate(aliveCount: Int = grid.getAliveCount()) {
        onUpdate?.invoke(score, aliveCount, generation, highScore)
    }
}
