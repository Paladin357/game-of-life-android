package com.balatro.gameoflife

data class PresetPattern(
    val name: String,
    val category: String,
    val description: String,
    val coordinates: List<Pair<Int, Int>>
)

object Presets {
    val list: List<PresetPattern> = listOf(
        PresetPattern(
            name = "Glider",
            category = "Spaceship",
            description = "Travels diagonally across the grid indefinitely.",
            coordinates = listOf(
                Pair(0, 1),
                Pair(1, 2),
                Pair(2, 0),
                Pair(2, 1),
                Pair(2, 2)
            )
        ),
        PresetPattern(
            name = "Lightweight Spaceship (LWSS)",
            category = "Spaceship",
            description = "Glides horizontally through space.",
            coordinates = listOf(
                Pair(0, 1), Pair(0, 4),
                Pair(1, 0),
                Pair(2, 0), Pair(2, 4),
                Pair(3, 0), Pair(3, 1), Pair(3, 2), Pair(3, 3)
            )
        ),
        PresetPattern(
            name = "Pulsar",
            category = "Oscillator",
            description = "Large, mesmerizing period 3 oscillator.",
            coordinates = listOf(
                // Quadrant 1
                Pair(-6, -4), Pair(-6, -3), Pair(-6, -2), Pair(-6, 2), Pair(-6, 3), Pair(-6, 4),
                Pair(-4, -6), Pair(-3, -6), Pair(-2, -6), Pair(2, -6), Pair(3, -6), Pair(4, -6),
                Pair(-4, -1), Pair(-3, -1), Pair(-2, -1), Pair(2, -1), Pair(3, -1), Pair(4, -1),
                Pair(-4, 1), Pair(-3, 1), Pair(-2, 1), Pair(2, 1), Pair(3, 1), Pair(4, 1),
                Pair(-4, 6), Pair(-3, 6), Pair(-2, 6), Pair(2, 6), Pair(3, 6), Pair(4, 6),
                // Quadrant 2
                Pair(-1, -4), Pair(-1, -3), Pair(-1, -2), Pair(-1, 2), Pair(-1, 3), Pair(-1, 4),
                Pair(1, -4), Pair(1, -3), Pair(1, -2), Pair(1, 2), Pair(1, 3), Pair(1, 4),
                Pair(6, -4), Pair(6, -3), Pair(6, -2), Pair(6, 2), Pair(6, 3), Pair(6, 4)
            )
        ),
        PresetPattern(
            name = "Toad",
            category = "Oscillator",
            description = "Compact period 2 oscillator.",
            coordinates = listOf(
                Pair(0, 1), Pair(0, 2), Pair(0, 3),
                Pair(1, 0), Pair(1, 1), Pair(1, 2)
            )
        ),
        PresetPattern(
            name = "Beacon",
            category = "Oscillator",
            description = "Blinking 4x4 oscillator.",
            coordinates = listOf(
                Pair(0, 0), Pair(0, 1), Pair(1, 0),
                Pair(2, 3), Pair(3, 2), Pair(3, 3)
            )
        ),
        PresetPattern(
            name = "Pentadecathlon",
            category = "Oscillator",
            description = "Period 15 oscillator with high stability.",
            coordinates = listOf(
                Pair(-4, 0), Pair(-3, 0), Pair(-2, -1), Pair(-2, 1),
                Pair(-1, 0), Pair(0, 0), Pair(1, 0), Pair(2, 0),
                Pair(3, -1), Pair(3, 1), Pair(4, 0), Pair(5, 0)
            )
        ),
        PresetPattern(
            name = "Acorn",
            category = "Methuselah",
            description = "Tiny 7-cell seed that takes 5,206 generations to stabilize!",
            coordinates = listOf(
                Pair(0, 1),
                Pair(1, 3),
                Pair(2, 0), Pair(2, 1), Pair(2, 4), Pair(2, 5), Pair(2, 6)
            )
        ),
        PresetPattern(
            name = "Diehard",
            category = "Methuselah",
            description = "Disappears completely after 130 generations.",
            coordinates = listOf(
                Pair(0, 6),
                Pair(1, 0), Pair(1, 1),
                Pair(2, 1), Pair(2, 5), Pair(2, 6), Pair(2, 7)
            )
        ),
        PresetPattern(
            name = "Gosper Glider Gun",
            category = "Gun",
            description = "Continuously creates and fires gliders into space.",
            coordinates = listOf(
                Pair(4, 0), Pair(4, 1), Pair(5, 0), Pair(5, 1),
                Pair(2, 12), Pair(2, 13), Pair(3, 11), Pair(4, 10),
                Pair(5, 10), Pair(6, 10), Pair(7, 11), Pair(8, 12),
                Pair(8, 13), Pair(5, 14), Pair(3, 15), Pair(7, 15),
                Pair(4, 16), Pair(5, 16), Pair(6, 16), Pair(5, 17),
                Pair(2, 20), Pair(2, 21), Pair(3, 20), Pair(3, 21),
                Pair(4, 20), Pair(4, 21), Pair(1, 22), Pair(5, 22)
            )
        )
    )
}
