package com.example.composebasics

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(){
    var shouldShowOnBoarding by rememberSaveable {
        mutableStateOf(false)
    }
    if(shouldShowOnBoarding){
        OnboardingScreen(onContinueClicked = {shouldShowOnBoarding = false})
    }else{
//        Greetings(names = listOf("World", "Compose"))
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List(100){"$it"}){
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names){ name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(modifier = Modifier
        .padding(all = 12.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello,")
            Text(text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                )
            )
            if(isExpanded){
                Text(
                    text = "Lorem ipsum dolor sit amet. Et omnis natus ut quod amet et voluptates consequatur quo consequatur nesciunt. Id recusandae provident ut minus maxime et dicta veniam nam internos At quos animi non dolorum molestias.\n" +
                        "\n".repeat(3)
                )
            }
        }
        val imageVectorId = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore
        val contentDescription = if (isExpanded) R.string.show_less else R.string.show_more
        IconButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
                imageVector = imageVectorId,
                contentDescription = contentDescription.toString()
            )
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: ()-> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeBasicsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320, name = "Test")
@Composable
fun DefaultPreview() {
    ComposeBasicsTheme {
        MyApp()
    }
}