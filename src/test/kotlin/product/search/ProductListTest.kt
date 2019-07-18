package product.search

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ProductListTest : Spek({

    val firstTestProduct = Product(1234, "Amazing trousers")
    val secondTestProduct = Product(1235, "Shiny saucepan")
    val productList = ProductList()

    describe("Product search") {
        productList.products.add(firstTestProduct)
        productList.products.add(secondTestProduct)

        it("returns product title 'Amazing trousers'") {
            assertThat(productList.search(1234)).isEqualTo("Amazing trousers")
        }

        it("returns 'no product found'") {
            assertThat(productList.search(1236)).isEqualTo("No product found")
        }
    }



})