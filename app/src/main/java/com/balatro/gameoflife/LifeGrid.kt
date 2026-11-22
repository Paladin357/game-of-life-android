package com.balatro.gameoflife

import kotlin.random.Random

data class StepResult(
    val aliveCount: Int,
    val birthCount: Int,
    val deathCount: Int
)

class LifeGrid(
    val rows: Int = 36,
    val cols: Int = 24
) {
    // Current grid and next-generation buffer
    private var grid = Array(rows) { BooleanArray(cols) }
    private var nextGrid = Array(rows) { BooleanArray(cols) }

    // Track cell maturity for visual styling (0 = dead, 1 = newborn, >1 = mature)
    private var ageMatrix = Array(rows) { IntArray(cols) }

    /**
     * Compute next generation following Conway's Game of Life rules.
     * With toroidal (wrap-around) boundaries to avoid border death.
     */
    fun nextGeneration(): StepResult {
        var alive = 0
        var births = 0
        var deaths = 0

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val neighbors = countNeighbors(r, c)
                val isCurrentlyAlive = grid[r][c]

                if (isCurrentlyAlive) {
                    if (neighbors < 2 || neighbors > 3) {
                        // Underpopulation or Overpopulation
                        nextGrid[r][c] = false
                        deaths++
                    } else {
                        // Survival (2 or 3 neighbors)
                        nextGrid[r][c] = true
                        alive++
                    }
                } else {
                    if (neighbors == 3) {
                        // Reproduction
                        nextGrid[r][c] = true
                        births++
                        alive++
                    } else {
                        nextGrid[r][c] = false
                    }
                }
            }
        }

        // Swap buffers and update age
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val isAlive = nextGrid[r][c]
                grid[r][c] = isAlive
                if (isAlive) {
                    ageMatrix[r][c] = (ageMatrix[r][c] + 1).coerceAtMost(10)
                } else {
                    ageMatrix[r][c] = 0
                }
            }
        }

        return StepResult(aliveCount = alive, birthCount = births, deathCount = deaths)
    }

    private fun countNeighbors(row: Int, col: Int): Int {
        var count = 0
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val r = (row + dr + rows) % rows
                val c = (col + dc + cols) % cols
                if (grid[r][c]) count++
            }
        }
        return count
    }

    fun isAlive(row: Int, col: Int): Boolean {
        if (row !in 0 until rows || col !in 0 until cols) return false
        return grid[row][col]
    }

    fun getAge(row: Int, col: Int): Int {
        if (row !in 0 until rows || col !in 0 until cols) return 0
        return ageMatrix[row][col]
    }

    fun setCell(row: Int, col: Int, alive: Boolean) {
        if (row in 0 until rows && col in 0 until cols) {
            grid[row][col] = alive
            ageMatrix[row][col] = if (alive) 1 else 0
        }
    }

    fun toggleCell(row: Int, col: Int): Boolean {
        if (row in 0 until rows && col in 0 until cols) {
            val newState = !grid[row][col]
            grid[row][col] = newState
            ageMatrix[row][col] = if (newState) 1 else 0
            return newState
        }
        return false
    }

    fun getAliveCount(): Int {
        var count = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c]) count++
            }
        }
        return count
    }

    fun clear() {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                grid[r][c] = false
                ageMatrix[r][c] = 0
            }
        }
    }

    fun randomize(aliveProbability: Float = 0.22f) {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val alive = Random.nextFloat() < aliveProbability
                grid[r][c] = alive
                ageMatrix[r][c] = if (alive) 1 else 0
            }
        }
    }

    fun placePattern(pattern: List<Pair<Int, Int>>, centerRow: Int, centerCol: Int) {
        for (pt in pattern) {
            val r = (centerRow + pt.first + rows) % rows
            val c = (centerCol + pt.second + cols) % cols
            setCell(r, c, true)
        }
    }
}
