package app.michaelwoodroof.naughtsandcrosses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.michaelwoodroof.naughtsandcrosses.ui.theme.NaughtsAndCrossesTheme

/**
 * This is the activity used for the tutorial of making a Naughts and Crosses game
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NaughtsAndCrossesTheme(
                content = {

                },
            )
        }
    }
}
