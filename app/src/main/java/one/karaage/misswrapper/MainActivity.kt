package one.karaage.misswrapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import one.karaage.misswrapper.ui.theme.MisswrapperTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MisswrapperTheme {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MisswrapperTheme {

    }
}