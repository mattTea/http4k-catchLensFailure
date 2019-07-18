package product.search

class ProductList {

    var products : MutableList<Product> = mutableListOf()

    fun search(productId: Int): String {
        var result = "No product found"

        for (product in products) if (product.id == productId) {
            result = product.name
        }
        return result
    }

}