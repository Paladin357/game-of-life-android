# Conway's Game of Life — Balatro Edition (Android Studio)

An interactive, high-performance Android adaptation of **Conway's Game of Life**, designed with a **Balatro**-inspired roguelike presentation (Chips, Mult, Score, High Score persistence, and retro neon aesthetic).

---

## 🎮 Features

- **Interactive Canvas Grid:**
  - Fast, responsive 60 FPS Canvas rendering (`LifeGridView`).
  - Smooth touch painting: tap or drag across the grid to toggle or draw living cells.
  - Multi-state cell rendering: newly born cells vs mature surviving cells with custom neon palettes.
- **Simulation Controls:**
  - **Play / Pause:** Start or stop real-time cellular evolution.
  - **Step:** Advance exactly one generation when paused for deep pattern observation.
  - **Speed Selector:** Toggle between `1X` (300ms), `2X` (150ms), `5X` (60ms), and `10X` (30ms).
  - **Clear:** Instantly wipe the board and reset counters.
  - **Random:** Seed random entropy configurations across the board.
- **Classic Conway Presets:**
  - Spawns legendary patterns directly into the center:
    - **Spaceships:** Glider, Lightweight Spaceship (LWSS)
    - **Oscillators:** Pulsar (period 3), Toad (period 2), Beacon (period 2), Pentadecathlon (period 15)
    - **Methuselahs:** Acorn (takes 5,206 generations to stabilize!), Diehard
    - **Guns:** Gosper Glider Gun
- **Balatro-Inspired Scoring System:**
  - **Alive Cells Counter:** Real-time count of living cells.
  - **Generation Counter:** Number of evolutionary steps survived.
  - **Chips & Mult Calculation:**
    - `Chips = aliveCells + (births * 2)`
    - `Mult = 1 + (generation / 20)`
    - `Score = Chips * Mult`
  - **High Score:** Automatically persists your personal best score across app launches via `SharedPreferences`.

---

## 🛠️ Opening in Android Studio

1. Open **Android Studio**.
2. Select **File -> Open...** (or click **Open** on the Welcome screen).
3. Navigate to:
   ```
   C:\Users\HomePC\.gemini\antigravity\scratch\GameOfLifeAndroid
   ```
4. Click **OK**.
5. Android Studio will automatically recognize the Gradle project, sync dependencies, and build.
6. Select an Android Emulator or connected physical device and click **Run (Shift + F10)**.

---

## 🃏 Balatro Crossover Roadmap & Design Concept

Balatro is a commercial roguelike poker deckbuilder developed in Love2D (Lua). Commercial game ROMs/binaries cannot be decompiled or redistributed due to copyright and DRM boundaries. 

However, we can adopt **Balatro's core roguelike mechanics** to build a brand new hybrid game: **"Balatro: Cellular Roguelike"**!

### How Balatro + Game of Life Can Work:
1. **Ante & Blinds System:**
   - Each round presents a Target Life Score (Small Blind, Big Blind, Boss Blind).
   - Boss Blinds introduce cellular debuffs (e.g. *The Wall*: Target score is tripled; *The Needle*: You only get 5 generations; *The Flint*: Overpopulation threshold reduced to 2).
2. **Hand / Deck of Life Forms (Cards):**
   - Instead of standard poker cards, your hand consists of **Pattern Cards** (e.g., *Block*, *Glider*, *Blinker*, *Toad*, *LWSS*, *Beehive*).
   - In each round, you have limited "Placements" (Hands) and "Discards" to place pattern cards onto the grid.
3. **Joker Cards for Conway's Life:**
   - **Glider Joker:** `+15 Mult` if any active Glider is traveling on the board.
   - **Still Life Joker:** `+50 Chips` for every stable 2x2 or 3x3 block that survives 10 generations.
   - **Overpopulation Joker:** `x2 Mult` whenever 5+ cells die in a single generation.
   - **Pulsar Joker:** `x3 Mult` if an oscillator completes a full period.
4. **The Shop:**
   - Between rounds, spend cash earned from scoring to buy new Pattern Cards, Joker slots, Planet boosts, and Vouchers (expanding grid size, increasing starting energy).
