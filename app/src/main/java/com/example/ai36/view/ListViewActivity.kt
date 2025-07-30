package com.example.ai36.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.ai36.R

class ListViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Correct edge-to-edge setup:
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ListViewBody()
        }
    }
}

@Composable
fun ListViewBody() {

    val images = listOf(
        R.drawable.img,
        R.drawable.person,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,
        R.drawable.img_6,
    )

    val names = listOf("A","B","C","D","E","F","G","H")

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            item {

                LazyVerticalStaggeredGrid(
                    userScrollEnabled = false,
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 10.dp,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .height(1500.dp)
                        .fillMaxWidth()
                ){
                    items(images.size) { image ->
                        Image(
                            painter = painterResource(images[image]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Use items() or itemsIndexed() instead of multiple item calls for multiple children
                    items(5) { index ->
                        val colors = listOf(Color.Red, Color.Black, Color.Yellow, Color.Blue, Color.Magenta)
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .width(200.dp)
                                .padding(end = 10.dp)
                                .background(color = colors[index])
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = Color.Black)
                )

                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = Color.Green)
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = Color.Yellow)
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = Color.Blue)
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(images) { index, image ->
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(100.dp)
                                    .border(
                                        3.dp,
                                        color = Color.Magenta, shape = CircleShape
                                    )
                                    .width(100.dp)
                                    .clip(shape = CircleShape)
                            )
                            Text(names[index])
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(color = Color.Magenta)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListViewPreview() {
    ListViewBody()
}
