package com.example.lazypizza.lazypizza.data.pizza

import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.domain.pizza.category
import com.example.lazypizza.lazypizza.domain.pizza.id
import com.example.lazypizza.lazypizza.domain.pizza.imagePath
import com.example.lazypizza.lazypizza.domain.pizza.imageUrl
import com.example.lazypizza.lazypizza.domain.pizza.name
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.tasks.await

class LocalDataSource : DataSource {
    private var _state = MutableStateFlow(localDataSource)

    override fun getProducts(search: String?): Flow<List<Product>> {
        return _state.asStateFlow().map { products ->
            products.filter { product ->
                product.category != Category.EXTRATOPPING
                        && (search.isNullOrBlank() || product.name.contains(search, ignoreCase = true))
            }
        }
    }

    override fun getExtraToppings(): Flow<List<Product.OtherProduct>> {
        return _state.asStateFlow().map { products ->
            products.filterIsInstance<Product.OtherProduct>()
                .filter { it.category == Category.EXTRATOPPING }
        }
    }

    override fun getProduct(id: Int): Flow<Product?> {
        return _state.asStateFlow().map { products ->
            products.find { it.id == id }
        }
    }

    override fun updateProductAmount(id: Int, amount: Int) {
        _state.update { products ->
            products.map { product ->
                if (product.id == id && product is Product.OtherProduct) {
                    product.copy(amount = amount)
                } else {
                    product
                }
            }
        }
    }

    override suspend fun loadImagesUrl() {
        val products = _state.value
        val storage = Firebase.storage
        supervisorScope {
            products.forEach { product ->
                if (product.imageUrl != null) return@forEach
                launch {
                    val imageRef = storage.reference.child(product.imagePath)
                    val url = imageRef.downloadUrl.await()
                    _state.update { products ->
                        products.map { p ->
                            if (p.id == product.id) {
                                when(p) {
                                    is Product.Pizza -> p.copy(imageUrl = url.toString())
                                    is Product.OtherProduct -> p.copy(imageUrl = url.toString())
                                }
                            } else {
                                p
                            }
                        }
                    }
                }
            }
        }
    }
}


val localDataSource = listOf(
    Product.Pizza(
        id = 1,
        name = "Margherita",
        shortDescription = "Tomato sauce, mozzarella, fresh basil, olive oil",
        longDescription = "Tomato sauce, mozzarella, fresh basil, olive oil",
        price = 8.99,
        imagePath = "pizza/Margherita.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 2,
        name = "Pepperoni",
        shortDescription = "Tomato sauce, mozzarella, pepperoni",
        longDescription = "Tomato sauce, mozzarella, pepperoni",
        price = 9.99,
        imagePath = "pizza/Pepperoni.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 3,
        name = "Hawaiian",
        shortDescription = "Tomato sauce, mozzarella, ham, pineapple",
        longDescription = "Tomato sauce, mozzarella, ham, pineapple",
        price = 10.49,
        imagePath = "pizza/Hawaiian.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 4,
        name = "BBQ Chicken",
        shortDescription = "BBQ sauce, mozzarella, grilled chicken, onion, corn",
        longDescription = "BBQ sauce, mozzarella, grilled chicken, onion, corn",
        price = 11.49,
        imagePath = "pizza/BBQ Chicken.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 5,
        name = "Four Cheese",
        shortDescription = "Mozzarella, gorgonzola, parmesan, ricotta",
        longDescription = "Mozzarella, gorgonzola, parmesan, ricotta",
        price = 11.99,
        imagePath = "pizza/Four Cheese.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 6,
        name = "Veggie Delight",
        shortDescription = "Tomato sauce, mozzarella, mushrooms, olives, bell pepper, onion, corn",
        longDescription = "Tomato sauce, mozzarella, mushrooms, olives, bell pepper, onion, corn",
        price = 9.79,
        imagePath = "pizza/Veggie Delight.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 7,
        name = "Meat Lovers",
        shortDescription = "Tomato sauce, mozzarella, pepperoni, ham, bacon, sausage",
        longDescription = "Tomato sauce, mozzarella, pepperoni, ham, bacon, sausage",
        price = 12.49,
        imagePath = "pizza/Meat Lovers.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 8,
        name = "Spicy Inferno",
        shortDescription = "Tomato sauce, mozzarella, spicy salami, jalapeños, red chili pepper, garlic",
        longDescription = "Tomato sauce, mozzarella, spicy salami, jalapeños, red chili pepper, garlic",
        price = 11.29,
        imagePath = "pizza/Spicy Inferno.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 9,
        name = "Seafood Special",
        shortDescription = "Tomato sauce, mozzarella, shrimp, mussels, squid, parsley",
        longDescription = "Tomato sauce, mozzarella, shrimp, mussels, squid, parsley",
        price = 13.99,
        imagePath = "pizza/Seafood Special.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.Pizza(
        id = 10,
        name = "Truffle Mushroom",
        shortDescription = "Cream sauce, mozzarella, mushrooms, truffle oil, parmesan",
        longDescription = "Cream sauce, mozzarella, mushrooms, truffle oil, parmesan",
        price = 12.99,
        imagePath = "pizza/Truffle Mushroom.png",
        imageUrl = null,
        category = Category.PIZZA
    ),
    Product.OtherProduct(
        id = 11,
        name = "Mineral Water",
        price = 1.49,
        amount = 0,
        imagePath = "drink/mineral water.png",
        imageUrl = null,
        category = Category.DRINK
    ),
    Product.OtherProduct(
        id = 12,
        name = "7-Up",
        price = 1.99,
        amount = 0,
        imagePath = "drink/7-up.png",
        imageUrl = null,
        category = Category.DRINK
    ),
    Product.OtherProduct(
        id = 13,
        name = "Pepsi",
        price = 1.99,
        amount = 0,
        imagePath = "drink/pepsi.png",
        imageUrl = null,
        category = Category.DRINK
    ),
    Product.OtherProduct(
        id = 14,
        name = "Orange Juice",
        price = 2.49,
        amount = 0,
        imagePath = "drink/orange juice.png",
        imageUrl = null,
        category = Category.DRINK
    ),
    Product.OtherProduct(
        id = 15,
        name = "Apple Juice",
        price = 2.29,
        amount = 0,
        imagePath = "drink/apple juice.png",
        imageUrl = null,
        category = Category.DRINK
    ),
    Product.OtherProduct(
        id = 16,
        name = "Iced Tea (Lemon)",
        price = 2.19,
        amount = 0,
        imagePath = "drink/iced tea.png",
        imageUrl = null,
        category = Category.DRINK
    ),
    Product.OtherProduct(
        id = 17,
        name = "Garlic Sauce",
        price = 0.59,
        amount = 0,
        imagePath = "sauce/Garlic Sauce.png",
        imageUrl = null,
        category = Category.SAUCE
    ),
    Product.OtherProduct(
        id = 18,
        name = "BBQ Sauce",
        price = 0.59,
        amount = 0,
        imagePath = "sauce/BBQ Sauce.png",
        imageUrl = null,
        category = Category.SAUCE
    ),
    Product.OtherProduct(
        id = 19,
        name = "Cheese Sauce",
        price = 0.89,
        amount = 0,
        imagePath = "sauce/Cheese Sauce.png",
        imageUrl = null,
        category = Category.SAUCE
    ),
    Product.OtherProduct(
        id = 20,
        name = "Spicy Chili Sauce",
        price = 0.59,
        amount = 0,
        imagePath = "sauce/Spicy Chili Sauce.png",
        imageUrl = null,
        category = Category.SAUCE
    ),
    Product.OtherProduct(
        id = 21,
        name = "Vanilla Ice Cream",
        price = 2.49,
        amount = 0,
        imagePath = "ice cream/vanilla.png",
        imageUrl = null,
        category = Category.ICECREAM
    ),
    Product.OtherProduct(
        id = 22,
        name = "Chocolate Ice Cream",
        price = 2.49,
        amount = 0,
        imagePath = "ice cream/chocolate.png",
        imageUrl = null,
        category = Category.ICECREAM
    ),
    Product.OtherProduct(
        id = 23,
        name = "Strawberry Ice Cream",
        price = 2.49,
        amount = 0,
        imagePath = "ice cream/strawberry.png",
        imageUrl = null,
        category = Category.ICECREAM
    ),
    Product.OtherProduct(
        id = 24,
        name = "Cookies Ice Cream",
        price = 2.79,
        amount = 0,
        imagePath = "ice cream/cookies.png",
        imageUrl = null,
        category = Category.ICECREAM
    ),
    Product.OtherProduct(
        id = 25,
        name = "Pistachio Ice Cream",
        price = 2.99,
        amount = 0,
        imagePath = "ice cream/pistachio.png",
        imageUrl = null,
        category = Category.ICECREAM
    ),
    Product.OtherProduct(
        id = 26,
        name = "Mango Sorbet",
        price = 2.69,
        amount = 0,
        imagePath = "ice cream/mango sorbet.png",
        imageUrl = null,
        category = Category.ICECREAM
    ),
    Product.OtherProduct(
        id = 27,
        name = "Bacon",
        price = 1.00,
        amount = 0,
        imagePath = "toppings/bacon.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 28,
        name = "Extra Cheese",
        price = 1.00,
        amount = 0,
        imagePath = "toppings/cheese.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 29,
        name = "Corn",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/corn.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 30,
        name = "Tomato",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/tomato.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 31,
        name = "Olives",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/olive.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 32,
        name = "Pepperoni",
        price = 1.00,
        amount = 0,
        imagePath = "toppings/pepperoni.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 33,
        name = "Mushrooms",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/mashroom.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 34,
        name = "Basil",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/basil.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 35,
        name = "Pineapple",
        price = 1.00,
        amount = 0,
        imagePath = "toppings/pineapple.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 36,
        name = "Onion",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/onion.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 37,
        name = "Chili Peppers",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/chilli.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    ),
    Product.OtherProduct(
        id = 38,
        name = "Spinach",
        price = 0.50,
        amount = 0,
        imagePath = "toppings/spinach.png",
        imageUrl = null,
        category = Category.EXTRATOPPING
    )
)
