package com.example.ai36.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ai36.model.ProductModel
import com.example.ai36.repository.ProductRepositoryImpl
import com.example.ai36.view.ui.theme.AI36Theme
import com.example.ai36.viewmodel.ProductViewModel

class UpdateProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpdateProductBody()

        }
    }
}

@Composable
fun UpdateProductBody() {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val repo = remember { ProductRepositoryImpl() }
    val viewmodel= remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    val productId: String? = activity?.intent?.getStringExtra("productId")

    val products= viewmodel.product.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        viewmodel.getProductById(productId.toString())
    }

    name=products.value?.productName?:""
    price=products.value?.productPrice.toString()
    description=products.value?.productDescription?:""




    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Product Price") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Product Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
            }

            item {
                Button(
                    onClick = {

                        var data = mutableMapOf<String,Any?>()

                        data["productDesc"] = description
                        data["productPrice"] = price.toDouble()
                        data["productName"] = name
                        data["productId"] = productId

                        viewmodel.updateProduct(
                            productId.toString(),data
                        ) {
                                success,message->
                            if(success){
                                activity?.finish()
                            }else{
                                Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Product")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUpdateProductBody() {
    UpdateProductBody()
}