package com.balatro.gameoflife

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.balatro.gameoflife.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lifeGrid: LifeGrid
    private lateinit var gameManager: GameManager

    private val numberFormat = NumberFormat.getNumberInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize grid (36 rows x 24 columns for ideal vertical mobile aspect ratio)
        lifeGrid = LifeGrid(rows = 36, cols = 24)
        gameManager = GameManager(this, lifeGrid)

        binding.lifeGridView.lifeGrid = lifeGrid

        // Real-time grid interaction update (when drawing / tapping)
        binding.lifeGridView.onGridChanged = {
            updateHud(
                score = gameManager.score,
                alive = lifeGrid.getAliveCount(),
                gen = gameManager.generation,
                highScore = gameManager.highScore
            )
        }

        // Setup Manager callbacks
        gameManager.onUpdate = { score, alive, gen, high ->
            updateHud(score, alive, gen, high)
            binding.lifeGridView.invalidate()
        }

        gameManager.onPlayStateChanged = { isPlaying ->
            updatePlayPauseButton(isPlaying)
        }

        setupButtons()
        updateHud(0, 0, 0, gameManager.highScore)
    }

    private fun setupButtons() {
        // Play / Pause
        binding.btnPlayPause.setOnClickListener {
            gameManager.togglePlayPause()
        }

        // Step one generation
        binding.btnStep.setOnClickListener {
            if (gameManager.isRunning) {
                gameManager.pause()
            }
            gameManager.step()
        }

        // Speed toggle
        binding.btnSpeed.setOnClickListener {
            val nextSpeed = gameManager.cycleSpeed()
            binding.btnSpeed.text = "$nextSpeed SPEED"
        }

        // Clear board
        binding.btnClear.setOnClickListener {
            gameManager.clearGrid()
            binding.lifeGridView.invalidate()
        }

        // Randomize
        binding.btnRandom.setOnClickListener {
            gameManager.randomize()
            binding.lifeGridView.invalidate()
        }

        // Presets selector dialog
        binding.btnPresets.setOnClickListener {
            showPresetsDialog()
        }
    }

    private fun showPresetsDialog() {
        val presetNames = Presets.list.map { "${it.name} (${it.category})" }.toTypedArray()

        MaterialAlertDialogBuilder(this)
            .setTitle("Spawn Preset Pattern")
            .setItems(presetNames) { _, which ->
                val selected = Presets.list[which]
                gameManager.pause()
                // Center the pattern on the grid
                val centerRow = lifeGrid.rows / 2
                val centerCol = lifeGrid.cols / 2
                lifeGrid.placePattern(selected.coordinates, centerRow, centerCol)
                gameManager.resetStats()
                binding.lifeGridView.invalidate()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updatePlayPauseButton(isPlaying: Boolean) {
        if (isPlaying) {
            binding.btnPlayPause.text = getString(R.string.pause)
            val redColor = ContextCompat.getColor(this, R.color.btn_pause)
            binding.btnPlayPause.backgroundTintList = ColorStateList.valueOf(redColor)
            binding.btnPlayPause.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        } else {
            binding.btnPlayPause.text = getString(R.string.play)
            val greenColor = ContextCompat.getColor(this, R.color.btn_play)
            binding.btnPlayPause.backgroundTintList = ColorStateList.valueOf(greenColor)
            binding.btnPlayPause.setTextColor(ContextCompat.getColor(this, R.color.bg_dark))
        }
    }

    private fun updateHud(score: Long, alive: Int, gen: Long, highScore: Long) {
        binding.tvScore.text = numberFormat.format(score)
        binding.tvAliveCells.text = numberFormat.format(alive)
        binding.tvGeneration.text = numberFormat.format(gen)
        binding.tvHighScore.text = numberFormat.format(highScore)
    }

    override fun onPause() {
        super.onPause()
        gameManager.pause()
    }
}
