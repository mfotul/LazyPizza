package com.example.lazypizza.lazypizza.presentation.preview

import com.example.lazypizza.lazypizza.domain.pizza.Category
import com.example.lazypizza.lazypizza.presentation.models.OtherProductUi
import com.example.lazypizza.lazypizza.presentation.models.PizzaUi

data object PreviewModel {
    val pizza = PizzaUi(
        id = 1,
        name = "Margherita",
        shortDescription = "Tomato sauce, mozzarella, fresh basil, olive oil",
        longDescription = "Tomato sauce, mozzarella, fresh basil, olive oil",
        price = 8.99,
        imagePath = "pizza/Margherita.png",
    )
    val otherProduct = OtherProductUi(
        id = 11,
        name = "Mineral Water",
        price = 1.49,
        amount = 0,
        imagePath = "drink/mineral water.png",
        category = Category.DRINK
    )
}