package app.michaelwoodroof.naughtsandcrosses.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.michaelwoodroof.naughtsandcrosses.data.structure.CellState
import app.michaelwoodroof.naughtsandcrosses.ui.theme.NaughtsAndCrossesTheme

/**
 * This composable handles an individual square within a board
 *
 * @param enabled   If the square in the board is clickable
 * @param modifier  Applies modifications needed to cell
 * @param onClick   What unit of code should be performed when this is clicked
 * @param state     The current state of the cell
 *
 * @see CellState
 */
@Composable
fun Cell(
    enabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
    state: CellState,
) {
    Button(
        border = BorderStroke(
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.4f,
            ),
            width = 1.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
        ),
        enabled = enabled,
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(
            size = 0.dp,
        ),
    ) {
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = if (state == CellState.UNFILLED) "" else state.name.uppercase(),
        )
    }
}

@Preview(
    name = "Cell",
)
@Preview(
    name = "Cell - Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewCell() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Cell(
                    enabled = true,
                    modifier = Modifier
                        .aspectRatio(1f),
                    onClick = {},
                    state = CellState.O,
                )
            }
        }
    )
}
