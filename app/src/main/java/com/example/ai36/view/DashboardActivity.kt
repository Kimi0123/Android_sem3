package com.example.ai36.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.ai36.repository.ProductRepositoryImpl
import com.example.ai36.viewmodel.ProductViewModel

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Proper edge-to-edge enabling
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DashboardBody()
        }
    }
}

@Composable
fun DashboardBody() {
    val context = LocalContext.current
    val activity = context as? Activity

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val products = viewModel.allProducts
        .observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(context, AddProductActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(products.value.size) { index ->
                val eachProduct = products.value[index]
                Card(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text("${eachProduct?.productName}")
                        Text("${eachProduct?.productPrice}")
                        Text("${eachProduct?.productDescription}")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    val intent = Intent(context, UpdateProductActivity::class.java)
                                    intent.putExtra("productId", "${eachProduct?.productId}")
                                    context.startActivity(intent)
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.Gray
                                )
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            }

                            IconButton(
                                onClick = {
                                    viewModel.deleteProduct(eachProduct?.productId.toString()) { success, message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.Red
                                )
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "DeleteProduct")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreDash() {
    DashboardBody()
}
