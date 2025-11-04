package com.example.lazypizza.lazypizza.data.remote

import com.example.lazypizza.lazypizza.data.remote.dto.ProductDto
import com.example.lazypizza.lazypizza.domain.pizza.DataSource
import com.example.lazypizza.lazypizza.domain.pizza.Product
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirebaseDataSource : DataSource {
    private val db = Firebase.firestore

    override fun getProducts(search: String?): Flow<List<Product>> {
        return db.collection("products")
            .whereNotEqualTo("category", "EXTRATOPPING")
            .orderBy("id")
            .snapshots()
            .map { documentSnapshots ->
                documentSnapshots.mapNotNull { documentSnapshot ->
                    documentSnapshot.getDocument()
                }
            }
            .map { products ->
                products.filter {
                    search.isNullOrBlank() || it.name.contains(search, ignoreCase = true)
                }
            }
    }

    override fun getExtraToppings(): Flow<List<Product>> {
        return db.collection("products")
            .whereEqualTo("category", "EXTRATOPPING")
            .orderBy("id")
            .snapshots()
            .map { documentSnapshots ->
                documentSnapshots.mapNotNull { documentSnapshot ->
                    documentSnapshot.getDocument()
                }
            }
    }

    override fun getProduct(id: String): Flow<Product?> {
        return db.collection("products")
            .document(id)
            .snapshots()
            .map { doc ->
                doc.getDocument()
            }
    }

    override fun getCartItems(): Flow<List<Product>> {
        return db.collection("cart")
            .orderBy("createdAt")
            .snapshots()
            .map { documentSnapshots ->
                documentSnapshots.mapNotNull { documentSnapshot ->
                    documentSnapshot.getDocument()
                }
            }
    }

    override fun getCartItem(id: String): Flow<Product?> {
        return db.collection("cart")
            .document(id)
            .snapshots()
            .map { snapshot ->
                snapshot.getDocument()
            }
    }


    override suspend fun updateProductAmount(id: String, amount: Int) {
        db.collection("products")
            .document(id)
            .update("amount", amount)
            .await()
    }

    override suspend fun addToCart(id: String): Product? {
        val productSnapshot = db.collection("products")
            .document(id)
            .get()
            .await() ?: return null

        val product = productSnapshot.getDocument()
        product?.let { product ->
            val newProduct = when (product) {
                is Product.Pizza -> product.copy(amount = 1)
                is Product.OtherProduct -> product.copy(amount = 1)
            }
            db.collection("cart")
                .document(product.id)
                .set(newProduct.toDto())
                .await()
        } ?: return null
        return product
    }

    override suspend fun addPizzaToCart(product: Product) {
        val productRef = db.collection("cart")
            .add(product.toDto())
            .await() ?: return
        productRef.update("id", productRef.id).await()
    }

    override suspend fun removeFromCart(id: String) {
        db.collection("cart")
            .document(id)
            .delete()
            .await()
    }

    override suspend fun updateCartProductAmount(id: String, amount: Int) {
        val productSnapshot = db.collection("cart")
            .document(id)
            .get()
            .await()
        if (productSnapshot.exists()) {
            productSnapshot
                .reference
                .update("amount", amount)
                .await()
        } else {
            val product = addToCart(id)
            product?.let { product ->
                db.collection("cart")
                    .document(product.id)
                    .set(product.toDto())
                    .await()
            }
        }
    }

    override suspend fun loadInitialData() {

    }

    private fun DocumentSnapshot.getDocument(): Product? {
        return when (this.getString("productType")) {
            "PIZZA" -> this.toObject(ProductDto.PizzaDto::class.java)?.toDomain()
            "OTHER_PRODUCT" -> this.toObject(ProductDto.OtherProductDto::class.java)?.toDomain()
            else -> null
        }
    }
}