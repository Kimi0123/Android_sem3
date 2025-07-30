package com.example.ai36.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.example.ai36.R
import com.example.ai36.Utils.ImageUtils
import com.example.ai36.model.ProductModel
import com.example.ai36.repository.ProductRepositoryImpl
import com.example.ai36.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {
    // Make this a normal nullable Uri property
    private var selectedImageUri: Uri? = null

    lateinit var imageUtils: ImageUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge content (replace your enableEdgeToEdge())
        WindowCompat.setDecorFitsSystemWindows(window, false)

        imageUtils = ImageUtils(this, this)
        imageUtils.registerLaunchers { uri ->
            selectedImageUri = uri
            // To notify Compose of the new image, call setContent again or better:
            // Use a Compose state to hold the Uri (see below)
            // But for simplicity, we call setContent again here:
            setContent {
                AddProductBody(
                    selectedImageUri = selectedImageUri,
                    onPickImage = { imageUtils.launchImagePicker() }
                )
            }
        }

        setContent {
            AddProductBody(
                selectedImageUri = selectedImageUri,
                onPickImage = { imageUtils.launchImagePicker() }
            )
        }
    }
}

@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onPickImage()
                        }
                        .padding(10.dp)
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painterResource(R.drawable.imgplchol),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Product Name") },
                    value = productName,
                    onValueChange = { productName = it }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Product Description") },
                    value = productDescription,
                    onValueChange = { productDescription = it }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("Product Price") },
                    value = productPrice,
                    onValueChange = { productPrice = it }
                )

                Button(
                    onClick = {
                        if (selectedImageUri != null) {
                            viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                                if (imageUrl != null) {
                                    val model = ProductModel(
                                        "",
                                        productName,
                                        productPrice.toDoubleOrNull() ?: 0.0,
                                        productDescription,
                                        imageUrl
                                    )
                                    viewModel.addProduct(model) { success, message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        if (success) activity?.finish()
                                    }
                                } else {
                                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please select an image first",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductBodyPreview() {
    AddProductBody(
        selectedImageUri = null, // or pass a mock Uri if needed
        onPickImage = {} // no-op
    )
}
