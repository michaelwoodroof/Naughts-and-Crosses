# Naughts and Crosses Compose tutorial
## Introduction

This tutorial will walk through the steps needed to create a simple Naughts and Crosses game in Compose using a state-driven design.

## Requirements

- Basic understanding of Android Studio
- Basic understanding of Kotlin

If you wish to skip this tutorial and see the final code switch to the branch `end-stage`.

### 1. Setting-up the project

Simply clone the project and ensure you are on the branch `main`. Next ensure your gradle is synced and all dependencies are downloaded.

### 2. Setting-up the UI components

Create this package `app.michaelwoodroof.naughtsandcrosses.ui.components` then navigate to it. You will need to create two UI components in order for the game to function. Let's first create a new file under this package, right click the package then click new file then 'Kotlin Class/File' then just File name this file 'Cell' this should create a new file called Cell.kt in the package. Repeat this process and create another file called 'Board' this show leave you with two files Cell and Board. These two components will be the building blocks for the naught and crosses game.

### 3. Creating the Cell component

Open Cell.kt and copy this code snippet:

```
@Composable
fun Cell() {

}

@Preview(
    name = "Cell",
)
@Composable
fun PreviewCell() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Cell()
            }
        }
    )
}
```

This provides us with a simple Composable with a preview. However, this is just a bare bones composable you will need to add a Button to this composable view the [api specification of a filled button](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#button). You will create a Button with a Text element as it's content. See below for the snippet you will use for the contents of the cell. As in order for the game to function you will need something to click to fill the cell with either a 'X' or an 'O'.

```
fun Cell() {
    Button(
        onClick = {},
    ) {
        Text(
            text = "Example",
        )
    }
}
```

#### 3.1 Styling the Cell

Currently, the Cell doesn't really look how you'd expect for a cell in a Naughts and Crosses game. In order to make the button look how'd you'd expect a Cell to look you need to give the button properties. Let's start by adjusting the colours of the Button, update the property `border` in the button to the following:

```
border = BorderStroke(
    color = MaterialTheme.colorScheme.onBackground.copy(
        alpha = 0.4F,
    ),
    width = 1.dp,
),
```

and update the property `colors` to the following:

```
colors = ButtonDefaults.buttonColors(
    containerColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface,
    disabledContainerColor = MaterialTheme.colorScheme.surface,
),
```

and add `style` to the text element:

```
Button(
    ...
) {
    Text(
        style = MaterialTheme.typography.bodyLarge,
        text = ...,
    )
}
```

and lastly the property `shape` to the following:

```
shape = RoundedCornerShape(
    size = 0.dp,
),
```

This should result in this:

```
Button(
    border = BorderStroke(
        color = MaterialTheme.colorScheme.onBackground.copy(
            alpha = 0.4F,
        ),
        width = 1.dp,
    ),
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.surface,
    ),
    shape = RoundedCornerShape(
        size = 0.dp,
    ),
) {
    Text(
        style = MaterialTheme.typography.bodyLarge,
        text = "Example",
    )
}
```

This has now provided us with a cell with the needed visual properties. However, the shape of the cell will still not be correct so you will need to apply a modifier to the cell. Apply the following modifier to the cell:

```
Button(
    modifier = Modifier.aspectRatio(1f)
)
```

This will successfully create a Cell that resembles what you'd except a Cell to look like in a Naughts and Crosses game. 

#### 3.2 Hoisting the state outside the button

You may have noticed that a Button needs to have a `onClick` property this has intentionally been omitted for the time being. As you will now hoist any logic that pertains to the Cell's state. Additionally, there needs to be a way to track the state in which the cell is in there is an enum class provided `CellState` this records the three possible states in which a cell can be in: X, O or UNFILLED. By adding the following properties we will now have completed the cell component:

```
fun Cell(
    enabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
    state: CellState,
)
```

And now update the Button to use these paramters so it looks as the following:

```
Button(
    border = ...,
    colors = ...,
    enabled = enabled,
    modifier = modifier,
    onClick = onClick,
    shape = ...,
) {
    Text(
        text = if (state == CellState.UNFILLED) "" else state.name.uppercase(),
    )
}
```

Additionally, you will need to also the preview with these parameters with the following:

```
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
```

If you now refresh or build the project you will see a cell preview alongside the code this is useful in Compose to allow you to see components in isolate and preview the component in multiple different states. Try updating the CellState in the property `state` with `CellState.X` and `CellState.UNFILLED` and see that the component will update it's text accordingly.

### 4. Creating the Board component

Open Board.kt and copy this code snippet:

```
@Composable
fun Board() {

}

@Preview(
    name = "Board",
)
@Composable
fun PreviewBoard() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Board()
            }
        }
    )
}
```

#### 4.1 Creating the board

Currently, our board is empty and does not make use of our `Cell` component. To begin we will use the `Column` layout and instead this we will have a `Row` this will be used later on to show the current state of the game copy the following into the Board composable:

```
fun Board() {
    Column() {
        Row() {
        }
    }
}
```

Below the row we need to create the 3 x 3 Grid this can be created by using the following Kotlin built-in function `repeat` copy the following (below the row) to create the board:

```
repeat(3) {
    Row() {
        repeat(3) {
            Cell(
                enabled = true,
                modifier = Modifier,
                onClick = {},
                state = CellState.UNFILLED,
            )
        }
    }
}
```

You will now have the board with the grid of cells for the naughts and crosses board. Which after a refresh or build will now show a preview of the board in the preview.

Your board component should look like this:

```
fun Board() {
    Column() {
        Row() {
        }
        repeat(3) {
            Row() {
                repeat(3) {
                    Cell(
                        enabled = true,
                        modifier = Modifier,
                        onClick = {},
                        state = CellState.UNFILLED,
                    )
                }
            }
        }
}
```

#### 4.2 Styling the Board

The board however, will not look quite right as it hasn't been styled yet to rectify this please adjust the board to the following:

```
fun Board() {
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
        
        }
        repeat(3) { y ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f),
            ) {
                repeat(3) { x ->
                    Cell(
                        enabled = true,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth(1f)
                            .weight(0.33F),
                        onClick = {},
                        state = CellState.UNFILLED,
                    )
                }
            }
        }
    }
}
```

You now have a board that is correctly styled.

#### 4.3 Adding logic to the Board

In order for the game to function the following will need to be tracked: `boardState`, `isX`, & `winner`. This will be achieved through the use of `mutableStateOf` and `by remember`. 

`mutableStateOf` this is needed in Compose as it allows the value to be observed which is crucial as when this value is changed the layout will be recomposed to reflect this new value. `by remember` is needed so that the values of this variables are remembered in-between [new recompositions](https://developer.android.com/jetpack/compose/mental-model#recomposition).

##### 4.3.1 boardState

Board state will track the state of each cell within the board. In order to track this state in Compose this needs to be declared at the top level of the composable Board.

```
var boardState by remember {
    mutableStateOf(
        Array(9) { CellState.UNFILLED }
    )
}
```

##### 4.3.2 isX

isX tracks if player X is the current player or not. To track this within Compose this needs to be declared at the top level of the composable Board.

```
var isX by remember {
    mutableStateOf(false)
}
```

##### 4.3.3 winner

Winner will track when a player has won the game. Add this to the top level of the composable Board.

```
var winner by remember {
    mutableStateOf(false)
}
```

##### How the board composable should be:

```
fun Board() {
    var boardState by remember {
        mutableStateOf(
            Array(9) { CellState.UNFILLED }
        )
    }
    var isX by remember {
        mutableStateOf(true)
    }
    var winner by remember {
        mutableStateOf(false)
    }
    ...
}
```

#### 4.4 Integrating these varaibles into the board composable

You will now integrate the logic into the board to make the game functional. To begin add the below string templates to `strings.xml`. These will be used to display who's turn it is and which player has won.

```
<string name="message_template">Player %1$s%2$s</string>
<string name="turn_suffix">\'s turn</string>
<string name="winner_suffix">\u0020has won</string>
```

Now that the strings have been added to the project you can now implement these strings by adding a Text component into the Row added earlier in the Board composable. This will make use of the variables `winner` and `isX` to be able to correctly display who's turn it is and if not who won the current game.

```
Board() {
    Column(
        ...
    ) {
        Row(
            ...
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f),
                style = MaterialTheme.typography.bodyMedium,    
                text =
                    if (winner) {
                        stringResource(
                            id = R.string.message_template,
                            if (isX) "X" else "O",
                            stringResource(id = R.string.winner_suffix),
                        )
                    } else {
                        stringResource(
                            id = R.string.message_template,
                            if (isX) "X" else "O",
                            stringResource(id = R.string.turn_suffix),
                        )
                    },
            )
        }
        ...
    }
}
```

Now the logic will be added to the Cell composable and this will tie the whole game together. Next the enabled property needs to be tied to the Game state. First the Cell should only be able to be clicked if it is in the state of `CellState.UNFILLEDD` and that there isn't a winner. This is why the enabled property is set to `boardState[x + 3 * y] == CellState.UNFILLED && !winner`.


```
Cell(
    enabled = boardState[x + 3 * y] == CellState.UNFILLED && !winner,
    modifier = ...,
    onClick = {

    },
    state = boardState[x + 3 * y], 
)
```

#### 4.5 Adding the click event to the cell

The click event will handle updating all the previously added variables, this click event will be added to the Cell within the Board composable.

```
Cell(
    enabled = ...,
    modifier = ...,
    onClick = {
        val updatedBoard = boardState.clone()
        updatedBoard[x + 3 * y] = if (isX) CellState.X else CellState.O
        boardState = updatedBoard
        
        winner = Utils.detectWin(boardState)
        if (winner) return@Cell
        isX = !isX
    },
    state = ..., 
)
```

This code snippet will also need to be added to the function in Utils `detectWin` this defines using a list of all possible winning board states and determines if there is a winner. Then using some built-in Kotlin functions and boolean logic we can determine if all three cells in a possible winning state are in fact X or O.

```
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
```

Now try playing a game and you will see that it will be able to detect if a player has won.

#### 4.6 Hoisting the state out of the board

Currently, all the logic for the board is bound to the board composable itself this is generally not advised, due to that the composable board will manage it's own state making it harder to test and to simulate different states for the board in previews. This is why the state will be hoisted outside of the board. First let's add all the state variables as parameters for the board composable itself this should be done as the following:

```
fun Board(
    boardState: Array<CellState>,
    dimension: Int,
    isX: Boolean,
    onClick: (Int, Int) -> Unit,
    winner: Boolean,
)
```

You'll notice that there is an additional parameter this being dimension this is to make it easier to track the length and width of the naughts and crosses make it easier to expand this in the future. In order to faciliate this new parameter any place where `3` is used instead replace this with dimension.

Now you need to remove all state variables from the board composable and update the cell onClick method to the following:

```
Cell(
    ...
    onClick = {
        onClick(x, y)
    },
    ...
)
```

and all that is left is to update the preview of the board composable and the adjustments for the MainActivity to begin update the board preview to the following:

```
fun PreviewBoard() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Board(
                    boardState = Array(9) { CellState.UNFILLED },
                    dimension = 3,
                    isX = true,
                    onClick = { _, _ -> },
                    winner = false,
                )
            }
        }
    )
}
```

and the MainActivity should now be updated to the following:

```
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
                isX = isX,
                onClick = { x, y ->
                    val updatedBoard = boardState.clone()
                    updatedBoard[x + dimension * y] = if (isX) CellState.X else CellState.O
                    boardState = updatedBoard
                    winner = Utils.detectWin(boardState)
                    if (!winner) {
                        isX = !isX
                    }
                },
                winner = winner,
            )
        },
    )
}
```

The state has now been hoisted to out of the board composable.

### 5. Adding a reset button and draw detection

#### 5.1 Draw detection

There is a slight problem in that there is currently no way to detect if there was a draw to fix this a new variable will be introduced this being `draw`. Start by adding this to `MainActivity`:

```
var draw by remember {
    mutableStateOf(
        false
    )
}
```

Then add this property to board:

```
fun Board(
    ...
    draw: Boolean,
    ...
)
```

and update the preview to:

```
fun PreviewBoard() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Board(
                    ...
                    draw = false,
                    ...
                )
            }
        }
    )
}
```

Now add that draw variable to the Board composable that if the content in the `MainActivity`. Lastly, in order to correctly detect a draw all that needs to be added is this to the `onClick` and adjustment to the text element in the Board that shows the player's turn and who has won:

```
onClick = { x, y ->
    ...
    winner = Utils.detectWin(boardState)
    if (winner) return@Board
    draw = !boardState.contains(CellState.UNFILLED)
    ...
},
```

and adjust the Text component in the board to this:

```
fun Board(
    ...
) {
    Column(
        ...
    ) {
        Row(
            ...
        ) {
            Text(
                ...
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
```

and lastly, add this string to `strings.xml`:

```
<string name="draw">Draw</string>
```

The game will now be able to detect a draw state.

#### 5.2 Reset mechanic

Additionally, there is another problem there is no way to restart the game to fix this a button will be added that when clicked will reset the board. First start by adding this to the existing Row in the board composable:

```
fun Board(
    ...
) {
    Column(
        ...
    ) {
        Row(
            ...
        ) {
            Text(
                ...
            ) {
                ...
            }
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
```

This will add a simple [IconButton](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#IconButton(kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Boolean,androidx.compose.foundation.interaction.MutableInteractionSource,kotlin.Function0)), in order for this button to work a refreshAction is needed to pass to the board composable and a string template add the following:

```
fun Board(
    ...
    refreshAction: () -> Unit,
    ...
)
```

and update the preview to:

```
fun PreviewBoard() {
    NaughtsAndCrossesTheme(
        content = {
            Surface {
                Board(
                    ...
                    refreshAction = {},
                    ...,
                )
            }
        }
    )
}
```

and

```
<string name="cd_refresh">restarts the game</string>
```

All that is left is the actual contents of the refresh action that needs to be added to the Board composable in the MainActivity add the following for the refresh action to correctly perform:

```
content = {
    Board(
        ...
        refreshAction = {
            boardState = Array(9) { CellState.UNFILLED }
            draw = false
            isX = true
            winner = false
        },
        ...
    )
},
```
Now the game can be restarted.

### 6. Extensions

This is the end of this tutorial. Extensions you may wish to make to the game could include:

- Allowing either O or X to be the first player
- Extract state into a MVVM pattern by using a ViewModel
- Offer option to play again after the game has finished
- Displaying a history of moves that was made
- Extending the board to a 4 x 4 board
- A bot that makes moves for the other player
