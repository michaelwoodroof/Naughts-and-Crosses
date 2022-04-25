package app.michaelwoodroof.naughtsandcrosses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.michaelwoodroof.naughtsandcrosses.data.structure.CellState
import app.michaelwoodroof.naughtsandcrosses.ui.components.Board
import app.michaelwoodroof.naughtsandcrosses.ui.theme.NaughtsAndCrossesTheme
import app.michaelwoodroof.naughtsandcrosses.utils.Utils

/**
 * Defines the activity used for the example of this demo
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Defines the length and width of the board
        val dimension = 3

        setContent {
            var boardState by remember {
                mutableStateOf(
                    Array(9) { CellState.UNFILLED }
                )
            }
            var draw by remember {
                mutableStateOf(
                    false
                )
            }
            var isX by remember {
                mutableStateOf(true)
            }
            var winner by remember {
                mutableStateOf(false)
            }

            NaughtsAndCrossesTheme(
                content = {
                    Board(
                        boardState = boardState,
                        dimension = dimension,
                        draw = draw,
                        isX = isX,
                        onClick = { x, y ->
                            val updatedBoard = boardState.clone()
                            updatedBoard[x + dimension * y] = if (isX) CellState.X else CellState.O
                            boardState = updatedBoard
                            winner = Utils.detectWin(boardState)
                            if (winner) return@Board
                            draw = !boardState.contains(CellState.UNFILLED)
                            isX = !isX
                        },
                        refreshAction = {
                            boardState = Array(9) { CellState.UNFILLED }
                            draw = false
                            isX = true
                            winner = false
                        },
                        winner = winner,
                    )
                },
            )
        }
    }
}
