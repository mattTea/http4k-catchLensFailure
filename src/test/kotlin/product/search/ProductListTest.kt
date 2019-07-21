package product.search

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Request
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ProductListTest : Spek({

    val firstTestProduct = Product(1234, "Amazing trousers")
    val secondTestProduct = Product(1235, "Shiny saucepan")
    val productList = ProductList()

//    val fakeServerResponse = """{
//        "name": "${firstTestProduct.name}",
//    }"""
//
//    val fakeServer =
//        routes("/fake/products/${firstTestProduct.id}" bind GET to {
//            Response(OK)
//                .body(fakeServerResponse)
//                .header("Content-Type", "application/json")
//        },
//            "/fake/products/unknown-product-id" bind GET to {
//                Response(NOT_FOUND)
//                    .body("{}")
//                    .header("Content-Type", "application/json")
//            }
//        )
//
//    val testServer = fakeServer.asServer(Jetty(0))
//
//    beforeGroup {
//        testServer.start()
//    }

    describe("Product search") {
        productList.products.add(firstTestProduct)
        productList.products.add(secondTestProduct)


        it("returns product title 'Amazing trousers'") {
            assertThat(productList.search(1234)).isEqualTo("Amazing trousers")
        }

        it("returns 'no product found'") {
            assertThat(productList.search(1236)).isEqualTo("No product found")
        }

//        it("returns OK status") {
//            assertThat(response.status).isEqualTo(OK)
//        }


    }

//    afterGroup {
//        testServer.stop()
//    }

})