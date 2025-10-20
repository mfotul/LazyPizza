package com.example.lazypizza.lazypizza.data.pizza

import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.example.lazypizza.lazypizza.presentation.models.OtherProductUi
import com.example.lazypizza.lazypizza.presentation.models.PizzaUi

fun Product.Pizza.toPizzaUi(): PizzaUi {
    return PizzaUi(
        id = id,
        name = name,
        shortDescription = shortDescription,
        longDescription = longDescription,
        price = price,
        imagePath = imagePath,
        imageUrl = imageUrl
    )
}

fun Product.OtherProduct.toOtherProductUi(): OtherProductUi {
    return OtherProductUi(
        id = id,
        name = name,
        price = price,
        amount = amount,
        imagePath = imagePath,
        imageUrl = imageUrl,
        category = category,
    )
}
