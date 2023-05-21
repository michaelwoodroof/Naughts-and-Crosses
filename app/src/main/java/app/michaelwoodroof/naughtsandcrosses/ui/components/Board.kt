package app.michaelwoodroof.naughtsandcrosses.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.michaelwoodroof.naughtsandcrosses.R
import app.michaelwoodroof.naughtsandcrosses.data.structure.CellState
import app.michaelwoodroof.naughtsandcrosses.ui.theme.NaughtsAndCrossesTheme

/**
 * This composable hosts the naughts and crosses board
 *
 * @param dimension Defines the length and width of the board
 */
@Composable
fun Board(
    boardState: Array<CellState>,
    dimension: Int,
    draw: Boolean,
    isX: Boolean,
    onClick: (Int, Int) -> Unit,
    refreshAction: () -> Unit,
    winner: Boolean,
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .fillMaxSize(1f)
            .padding(
                all = 24.dp,
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(
                    vertical = 8.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                text =
                    if (winner) {
                        stringResource(
                            id = R.string.message_template,
                            if (isX) "X" else "O",
                            stringResource(id = R.string.winner_suffix),
                        )
                    } else if (draw) {
                        stringResource(
                            id = R.string.draw,
                        )
                    } else {
                        stringResource(
                            id = R.string.message_template,
                            if (isX) "X" else "O",
                            stringResource(id = R.string.turn_suffix),
                        )
                    },
            )
            IconButton(
                onClick = refreshAction,
            ) {
                Icon(
                    contentDescription = stringResource(
                        id = R.string.cd_refresh,
                    ),
                    imageVector = Icons.Filled.Refresh,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
        repeat(dimension) { y ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f),
            ) {
                repeat(dimension) { x ->
                    Cell(
                        enabled = boardState[x + dimension * y] == CellState.UNFILLED && !winner,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth(1f)
                            .weight(0.33f),
                        onClick = {
                            onClick(x, y)
                        },
                        state = boardState[x + dimension * y],
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Board",
)
@Preview(
    name = "Board - Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewBoard() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Board(
                    boardState = Array(9) { CellState.UNFILLED },
                    dimension = 3,
                    draw = false,
                    isX = true,
                    onClick = { _, _ -> },
                    refreshAction = {},
                    winner = false,
                )
            }
        }
    )
}
