package com.example.secondmodulefile3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class Product(
    val id: Int,
    val name: String,
    val price: Int
)

data class Cart(
    val products: List<Product>
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ShoppingCartScreen(
                    onFreeDelivery = {
                        Toast.makeText(
                            this,
                            "Доставка бесплатная!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingCartScreen(
    initialProducts: List<Product> = listOf(
        Product(0, "Товар #1", 100),
        Product(1, "Товар #2", 150),
        Product(2, "Товар #3", 56)
    ),
    newProductPrice: () -> Int = {
        Random.nextInt(0, 100)
    },
    onFreeDelivery: () -> Unit = {}
) {

    var cart by remember {
        mutableStateOf(
            Cart(initialProducts)
        )
    }

    val products = cart.products

    val totalSum = products.sumOf {
        it.price
    }

    val productSize = products.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Интернет-магазин",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        products.forEach { product ->

            Text(
                text = "${product.name} - ${product.price} рублей"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Товаров на сумму: $totalSum рублей"
        )

        Text(
            text = "Количество товаров: $productSize"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddProductSection {

            val newProduct = Product(
                id = (products.maxOfOrNull { it.id } ?: -1) + 1,
                name = "Товар #${products.size + 1}",
                price = newProductPrice()
            )

            val updatedProducts = products + newProduct

            val updatedTotal = updatedProducts.sumOf {
                it.price
            }

            cart = cart.copy(
                products = updatedProducts
            )

            if (updatedTotal > 500) {
                onFreeDelivery()
            }
        }

        if (products.isNotEmpty()) {

            RemoveProductSection {

                cart = cart.copy(
                    products = cart.products.dropLast(1)
                )
            }
        }
    }
}

@Composable
fun AddProductSection(
    onAdd: () -> Unit
) {

    Button(
        onClick = onAdd
    ) {
        Text("Добавить товар")
    }
}

@Composable
fun RemoveProductSection(
    onRemove: () -> Unit
) {

    Button(
        onClick = onRemove
    ) {
        Text("Удалить товар")
    }
}
