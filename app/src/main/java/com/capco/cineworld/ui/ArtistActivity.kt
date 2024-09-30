package com.capco.cineworld.ui

//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//
//class ArtistActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                MyComposeScreen()
//            }
//        }
//    }
//}
//
//@Composable
//fun MyComposeScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Image from drawable resource
//        Image(
//            painter = painterResource(id = com.capco.widgets.R.drawable.ic_no_internet_dino), // Replace with your image resource
//            contentDescription = "Image ",
//            modifier = Modifier.padding(bottom = 16.dp),
//            contentScale = ContentScale.Crop
//        )
//
//        // Text views
//        Text(
//            text = "Hello, Jetpack Compose!",
//            style = MaterialTheme.typography.headlineSmall,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//        Text(
//            text = "This is a simple example of Jetpack Compose with an Image and some Text.",
//            style = MaterialTheme.typography.bodyLarge
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MaterialTheme {
//        MyComposeScreen()
//    }
//}
