package product.search

import org.http4k.core.Body
import org.http4k.format.Jackson.auto

data class Product(val id: Int, val name: String) {
    companion object {
        val format = Body.auto<Product>().toLens()
    }
}

val products = listOf(
    Product(1234, "Magic trousers"),
    Product(1235, "Shiny saucepan")
)