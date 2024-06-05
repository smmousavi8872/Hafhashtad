package com.github.smmousavi.store

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.github.smmousavi.common.result.Result
import com.github.smmousavi.model.Product
import com.github.smmousavi.model.Rating
import com.github.smmousavi.ui.LoadingWheel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StoreScreen(viewModel: ProductsViewModel = hiltViewModel()) {
    val productsResult by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.product_list)) })
        }
    ) {
        when (productsResult) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingWheel(contentDesc = stringResource(R.string.loading_products))
                }
            }

            is Result.Success -> {
                val products = (productsResult as Result.Success<List<Product>>).data
                ProductList(products = products)
            }

            is Result.Error -> {
                val message = (productsResult as Result.Error).exception.message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = message ?: stringResource(R.string.an_error_occurred))

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    val sampleProducts = listOf(
        Product(
            id = 1,
            title = "Sample Product 1",
            price = 99.99,
            description = "This is a sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 4.5, count = 100)
        ),
        Product(
            id = 2,
            title = "Sample Product 2",
            price = 49.99,
            description = "This is another sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 3.5, count = 50)
        ),
        Product(
            id = 3,
            title = "Sample Product 3",
            price = 29.99,
            description = "This is another sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 3.0, count = 30)
        ),
        Product(
            id = 4,
            title = "Sample Product 4",
            price = 19.99,
            description = "This is another sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 2.5, count = 20)
        )
    )
    ProductList(products = sampleProducts)
}

@Composable
fun ProductList(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)
    ) {
        items(products.size) { index ->
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                ProductItem(products[index])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val sampleProducts = listOf(
        Product(
            id = 1,
            title = "Sample Product 1",
            price = 99.99,
            description = "This is a sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 4.5, count = 100)
        ),
        Product(
            id = 2,
            title = "Sample Product 2",
            price = 49.99,
            description = "This is another sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 3.5, count = 50)
        ),
        Product(
            id = 3,
            title = "Sample Product 3",
            price = 29.99,
            description = "This is another sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 3.0, count = 30)
        ),
        Product(
            id = 4,
            title = "Sample Product 4",
            price = 19.99,
            description = "This is another sample product description.",
            category = "Sample Category",
            image = "https://via.placeholder.com/150",
            rating = Rating(rate = 2.5, count = 20)
        )
    )
    ProductList(products = sampleProducts)
}

@Composable
fun ProductItem(product: Product) {
    Column(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberImagePainter(data = product.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row {
            Text(
                text = "\$${product.price}",
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color(0xFFE9B006)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${product.rating.rate} (${product.rating.count})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    val sampleProduct = Product(
        id = 1,
        title = "Sample Product",
        price = 99.99,
        description = "This is a sample product description.",
        category = "Sample Category",
        image = "https://via.placeholder.com/150",
        rating = Rating(rate = 4.5, count = 100)
    )
    ProductItem(product = sampleProduct)
}