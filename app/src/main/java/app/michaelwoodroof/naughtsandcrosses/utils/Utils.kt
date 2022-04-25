package app.michaelwoodroof.naughtsandcrosses.utils

import app.michaelwoodroof.naughtsandcrosses.data.structure.CellState

/**
 * Provides a set of useful utility functions
 */
object Utils {

    /**
     * Detects if there is a three in a row based on a pre-defined set of winning states
     */
    fun detectWin(input: Array<CellState>): Boolean {
        val winningStates = arrayOf(
            arrayOf(0, 1, 2),
            arrayOf(3, 4, 5),
            arrayOf(6, 7, 8),
            arrayOf(0, 3, 6),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(0, 4, 8),
            arrayOf(2, 4, 6),
        )

        for (state in winningStates) {
            val (a, b, c) = state
            if (input[a] == input[b] && input[b] == input[c] && input[a] == input[c]) {
                return !(
                    input[a] == CellState.UNFILLED
                    || input[b] == CellState.UNFILLED
                    || input[c] == CellState.UNFILLED
                )
            }
        }
        return false
    }
}
